package com.sqli.composablehub.streamscore.config;

import com.sqli.composablehub.algolia.models.ArticleTarget;
import com.sqli.composablehub.sap.models.SAPProductRaw;
import com.sqli.composablehub.streamscore.transformation.StreamTransformer;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {
    private final KafkaStreamConfigData kafkaStreamConfigData;
    public KafkaStreamConfig(KafkaStreamConfigData kafkaStreamConfigData) {
        this.kafkaStreamConfigData = kafkaStreamConfigData;
    }
    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();

        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);

        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaStreamConfigData.getBootstrapServers());
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaStreamConfigData.getApplicationId());
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaStreamConfigData.getSchemaRegistryUrl());
        return new KafkaStreamsConfiguration(props);
    }
}
