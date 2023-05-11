package com.sqli.composablehub.streamscore.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBootConfiguration
@EnableAutoConfiguration
public class KafkaStreamConfigTest extends KafkaStreamConfigData {
    @Test
    void readBootstrapServers() {
        assertEquals("localhost:9092", this.getBootstrapServers());
    }

    @Test
    void readSchemaRegistryUrl() {
        assertEquals("http://localhost:8081", this.getSchemaRegistryUrl());
    }

    @Test
    void readApplicationId() {
        assertEquals("stream-core", this.getApplicationId());
    }

    @Test
    void readInputTopic() {
        assertEquals("input-topic", this.getInputTopic());
    }

    @Test
    void readOutputTopic() {
        assertEquals("output-topic", this.getOutputTopic());
    }


}
