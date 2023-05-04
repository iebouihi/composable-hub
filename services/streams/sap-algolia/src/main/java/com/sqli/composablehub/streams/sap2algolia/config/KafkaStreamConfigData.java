package com.sqli.composablehub.streams.sap2algolia.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-stream-config")
public class KafkaStreamConfigData {
    private  String bootstrapServers;
    private  String schemaRegistryUrl;
    private  String applicationId;
    private  String inputTopic;
    private  String outputTopic;

}

