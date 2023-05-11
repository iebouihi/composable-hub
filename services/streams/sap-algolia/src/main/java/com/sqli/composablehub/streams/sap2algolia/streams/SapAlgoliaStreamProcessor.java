package com.sqli.composablehub.streams.sap2algolia.streams;

import com.sqli.composablehub.streamscore.config.KafkaStreamConfigData;
import com.sqli.composablehub.streamscore.streams.KafkaStreamProcessor;
import org.springframework.stereotype.Component;

public class SapAlgoliaStreamProcessor extends KafkaStreamProcessor {
    public SapAlgoliaStreamProcessor(KafkaStreamConfigData kafkaStreamConfigData) {
        super(kafkaStreamConfigData);
    }
}
