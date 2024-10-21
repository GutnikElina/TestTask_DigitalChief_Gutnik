package com.task.testtask.service;

import com.task.testtask.config.CreateBuilder;
import jakarta.annotation.PostConstruct;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@Slf4j
public class ElasticsearchIndexInitializer {

    private static final String INDEX_NAME = "products";
    private final RestHighLevelClient client;
    private static final int MAX_RETRIES = 5;
    private static final long RETRY_DELAY_MS = 5000L;

    public ElasticsearchIndexInitializer(RestHighLevelClient client) {
        this.client = client;
    }

    @PostConstruct
    public void createIndex() {
        GetIndexRequest getIndexRequest = new GetIndexRequest(INDEX_NAME);

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                if (!client.indices().exists(getIndexRequest, RequestOptions.DEFAULT)) {
                    CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);
                    createIndexRequest.mapping(CreateBuilder.getInstance().buildMapping());

                    CreateIndexResponse response = client.indices()
                            .create(createIndexRequest, RequestOptions.DEFAULT);

                    if (response.isAcknowledged()) {
                        log.info("-> Index '{}' successfully created.", INDEX_NAME);
                    } else {
                        log.error("! Failed to create index '{}'.", INDEX_NAME);
                    }
                } else {
                    log.info("! Index '{}' already exists.", INDEX_NAME);
                }
                break;
            } catch (IOException | ElasticsearchStatusException e) {
                log.error("! Error creating index '{}', attempt {}/{}", INDEX_NAME, attempt, MAX_RETRIES, e);
                if (attempt < MAX_RETRIES) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    log.error("! Max retries reached. Failed to create index '{}'.", INDEX_NAME);
                }
            }
        }
    }
}
