package com.example.DynamicPropertySource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
//@DirtiesContext // To enforce to have a call of @DynamicPropertySource for each child class
class ParentTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(ParentTest.class);

    @Container
    static ElasticsearchContainer elasticsearchContainer =
            new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch-oss:7.9.2");

    @DynamicPropertySource
    static void elasticProperties(DynamicPropertyRegistry registry) {
        LOGGER.info("SPRING-25850: Configuring SPRING BOOT to use ElasticSearch on: {}", elasticsearchContainer.getHttpHostAddress());
        registry.add("spring.elasticsearch.rest.uris", elasticsearchContainer::getHttpHostAddress);
    }

    @Test
    void contextLoads() {
        LOGGER.info("SPRING-25850: TestContainers deployed ElasticSearch on: {}", elasticsearchContainer.getHttpHostAddress());
    }

}
