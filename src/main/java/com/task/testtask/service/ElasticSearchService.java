package com.task.testtask.service;

import com.task.testtask.entity.Product;
import com.task.testtask.entity.SKU;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticSearchService {

    private final RestHighLevelClient restHighLevelClient;
    private static final String INDEX_NAME = "products";

    public Page<Product> getAllProductsFromElasticsearch(Pageable pageable) {
        List<Product> allProducts = searchProducts(QueryBuilders.matchAllQuery(), null).getContent();
        long totalHits = allProducts.size();
        return new PageImpl<>(allProducts, pageable, totalHits);
    }

    public Page<Product> searchProducts(String query, Pageable pageable, Boolean activeFilter, String startDateFilter) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (query != null && !query.isEmpty()) {
            boolQuery.should(QueryBuilders.matchQuery("name", query))
                    .should(QueryBuilders.matchPhraseQuery("description", query))
                    .minimumShouldMatch(1);
        }

        if (activeFilter != null) {
            boolQuery.filter(QueryBuilders.termQuery("active", activeFilter));
        }

        if (startDateFilter != null && !startDateFilter.isEmpty()) {
            try {
                LocalDate startDate = LocalDate.parse(startDateFilter);
                Instant startInstant = startDate.atStartOfDay(ZoneId.of("UTC")).toInstant();
                Instant endInstant = startDate.plusDays(1).atStartOfDay(ZoneId.of("UTC")).toInstant();

                boolQuery.filter(QueryBuilders.rangeQuery("startDate")
                        .gte(startInstant)
                        .lte(endInstant));
            } catch (DateTimeParseException e) {
                log.error("! Invalid startDate format: {}", e.getMessage());
            }
        }

        if (query == null && activeFilter == null && startDateFilter == null) {
            return getAllProductsFromElasticsearch(pageable);
        }
        return searchProducts(boolQuery, pageable);
    }

    private Page<Product> searchProducts(QueryBuilder query, Pageable pageable) {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(query)
                .sort("startDate", SortOrder.DESC);

        if (pageable != null) {
            sourceBuilder.from((int) pageable.getOffset()).size(pageable.getPageSize());
        } else {
            sourceBuilder.size(100);
        }

        searchRequest.source(sourceBuilder);
        return executeSearch(searchRequest, pageable);
    }

    private Page<Product> executeSearch(SearchRequest searchRequest, Pageable pageable) {
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            List<Product> products = Arrays.stream(response.getHits().getHits())
                    .map(SearchHit::getSourceAsMap)
                    .map(this::mapToProduct)
                    .collect(Collectors.toList());
            long totalHits = Objects.requireNonNull(response.getHits().getTotalHits()).value;

            return new PageImpl<>(products, pageable != null ? pageable : Pageable.unpaged(), totalHits);
        } catch (IOException e) {
            log.error("! Error during search: {}", e.getMessage());
            throw new RuntimeException("! Error executing search: " + e.getMessage());
        }
    }

    private Product mapToProduct(Map<String, Object> source) {
        Product product = new Product();
        product.setName((String) source.get("name"));
        product.setDescription((String) source.get("description"));
        product.setActive((Boolean) source.get("active"));

        String startDateString = (String) source.get("startDate");
        if (startDateString != null) {
            try {
                LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.ISO_LOCAL_DATE);
                product.setStartDate(startDate);
            } catch (DateTimeParseException e) {
                log.error("! Error parsing date: {}", e.getMessage());
            }
        }

        product.setSkuList((List<SKU>) source.get("skuList"));
        return product;
    }


}