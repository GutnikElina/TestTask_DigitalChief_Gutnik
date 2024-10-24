package com.task.testtask.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUri;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restClient() {
        RestClientBuilder builder = RestClient.builder(HttpHost.create(elasticsearchUri));
        return new RestHighLevelClient(builder);
    }
}
