package com.sqli.composablehub.streamscore.streams;

import com.sqli.composablehub.streamscore.transformation.StreamTransformer;
import com.sqli.composablehub.streamscore.config.KafkaStreamConfigData;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
@Slf4j
public class KafkaStreamProcessor {
    private final KafkaStreamConfigData kafkaStreamConfigData;
    @Autowired
    private Serde sourceSerde;
    @Autowired
    private Serde targetSerde;

    @Autowired
    private StreamTransformer streamTransformer;

    public KafkaStreamProcessor(KafkaStreamConfigData kafkaStreamConfigData) {
        this.kafkaStreamConfigData = kafkaStreamConfigData;
    }

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        sourceSerde.configure(Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaStreamConfigData.getSchemaRegistryUrl()), false);
        targetSerde.configure(Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaStreamConfigData.getSchemaRegistryUrl()), false);

        KStream<String, SpecificRecordBase> input = streamsBuilder.stream(kafkaStreamConfigData.getInputTopic(), Consumed.with(Serdes.String(), sourceSerde));

        KStream<String, SpecificRecordBase> output = input
                .map((key, value) -> KeyValue.pair(key, convert(value)))
                .filter((key, value) -> value != null);
        //Error handling to do
        output.to(kafkaStreamConfigData.getOutputTopic(), Produced.with(Serdes.String(), targetSerde));
    }

    private SpecificRecordBase convert(SpecificRecordBase source) {
        return streamTransformer.transform(source);
    }

}
