package com.sqli.composablehub.streams.sap2algolia.config;

import com.sqli.composablehub.streamscore.config.KafkaStreamConfigData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "sap-to-algolia-stream-config")
public class SapToAlgoliaStreamConfigData extends KafkaStreamConfigData {
    //here put additional properties
}
