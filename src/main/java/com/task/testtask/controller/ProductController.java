package com.task.testtask.controller;

import com.task.testtask.entity.Product;
import com.task.testtask.service.DataLoadService;
import com.task.testtask.service.ElasticSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    private final ElasticSearchService elasticSearchService;
    private final DataLoadService dataLoadService;

    private static final String DATA_LOADED_SUCCESS = "-> Data loaded to Elasticsearch successfully";
    private static final String ERROR_LOADING_DATA = "! Error loading data: ";

    @PostMapping("/load")
    public ResponseEntity<String> loadToElasticsearch() {
        dataLoadService.loadData();
        return ResponseEntity.ok(DATA_LOADED_SUCCESS);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage = elasticSearchService.getAllProductsFromElasticsearch(pageable);

        if (productsPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productsPage.getContent());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error(ERROR_LOADING_DATA + e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_LOADING_DATA + e.getMessage());
    }
}
