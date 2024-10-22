package com.task.testtask.service;

import com.task.testtask.entity.Product;
import com.task.testtask.entity.SKU;
import com.task.testtask.repository.ProductRepository;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class DataLoadService {

    private static final String INDEX_NAME = "products";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @PostConstruct
    public void loadData() {
        try {
            createIndexIfNotExists();
            loadProductsToElasticsearch();
        } catch (Exception e) {
            log.error("! Error loading data to Elasticsearch", e);
        }
    }

    private void createIndexIfNotExists() throws IOException {
        if (!restHighLevelClient.indices().exists(new GetIndexRequest(INDEX_NAME), RequestOptions.DEFAULT)) {
            AcknowledgedResponse response = restHighLevelClient.indices()
                    .create(new CreateIndexRequest(INDEX_NAME), RequestOptions.DEFAULT);

            if (response.isAcknowledged()) {
                log.info("-> Index '{}' created successfully.", INDEX_NAME);
            } else {
                log.error("! Failed to create index '{}'.", INDEX_NAME);
            }
        } else {
            log.info("! Index '{}' already exists.", INDEX_NAME);
        }
    }

    private void loadProductsToElasticsearch() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            log.warn("! No products found to load.");
            return;
        }

        BulkRequest bulkRequest = new BulkRequest();
        products.forEach(product -> addProductToBulkRequest(product, bulkRequest));

        executeBulkRequest(bulkRequest);
    }

    private void addProductToBulkRequest(Product product, BulkRequest bulkRequest) {
        try {
            IndexRequest indexRequest = new IndexRequest(INDEX_NAME)
                    .id(String.valueOf(product.getId()))
                    .source(buildProductJson(product));

            bulkRequest.add(indexRequest);
            log.info("-> Preparing to load product: {}", product.getName());
        } catch (IOException e) {
            log.error(String.format("! Error building JSON for product %d", product.getId()), e);
        }
    }

    private void executeBulkRequest(BulkRequest bulkRequest) {
        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                log.error("! Errors occurred while loading data: {}", bulkResponse.buildFailureMessage());
            } else {
                log.info("-> Successfully loaded products to Elasticsearch.");
            }
        } catch (IOException e) {
            log.error("! Error while executing bulk request", e);
        }
    }

    private String formatDate(LocalDate date) {
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return date.format(formatter);
        }
        return null;
    }

    private XContentBuilder buildProductJson(Product product) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .field("id", product.getId())
                .field("name", product.getName())
                .field("description", product.getDescription())
                .field("active", product.isActive());

        String formattedDate = product.getStartDate() != null ? product.getStartDate().format(DateTimeFormatter.ISO_DATE) : null;
        log.info("-> Formatted startDate for product {}: {}", product.getId(), formattedDate);
        builder.field("startDate", formattedDate);

        builder.startArray("skuList");
        if (product.getSkuList() != null) {
            for (SKU sku : product.getSkuList()) {
                builder.startObject()
                        .field("id", sku.getId())
                        .field("skuCode", sku.getSkuCode())
                        .field("price", sku.getPrice() != null ? sku.getPrice().doubleValue() : null)
                        .endObject();
            }
        }
        builder.endArray();

        builder.endObject();
        return builder;
    }


}

