package com.sqli.composablehub.kafka.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SpringBootConfiguration
@EnableAutoConfiguration
public class KafkaConfigTest extends KafkaConfigData {

    @Test
    void readBootstrapServers() {
        assertEquals("localhost:9092", this.getBootstrapServers());
    }

    @Test
    void readTopicName() {
        assertEquals("sap-raw", this.getTopicName());
    }

    @Test
    void readSchemaRegistryUrl() {
        assertEquals("http://localhost:8081", this.getSchemaRegistryUrl());
    }

    @Test
    void readSchemaRegistryUrlKey() {
        assertEquals("schema.registry.url", this.getSchemaRegistryUrlKey());
    }

    @Test
    void readReplicationFactor() {
        assertEquals(Short.valueOf("2"), this.getReplicationFactor());
    }

    @Test
    void readNumOfPartitions() {
        assertEquals(3, this.getNumOfPartitions());
    }
}
