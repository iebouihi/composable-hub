package com.kafka.streams.samples;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.sqli.composablehub.algolia.models.ArticleTarget;
import com.sqli.composablehub.sap.models.Category;
import com.sqli.composablehub.sap.models.SAPProductRaw;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class SapToAlgoliaStream {
    public static void main(String[] args) {
        System.out.println("Hello Stream");

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "sap-algolia-stream");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10000);
// Set the maximum number of records to fetch in a single poll to 100
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 2);

// Set the maximum number of records to fetch in a single poll to 100

// Create Serde instances for User and UserOutput
        Serde<SAPProductRaw> sapProductSerde = new SpecificAvroSerde<>();
        sapProductSerde.configure(Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081"), false);

        Serde<ArticleTarget> algoliaArticleSerde = new SpecificAvroSerde<>();
        algoliaArticleSerde.configure(Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081"), false);

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, SAPProductRaw> source = builder.stream("sap-raw", Consumed.with(Serdes.String(), sapProductSerde));

        KStream<String, ArticleTarget> target = source.mapValues(sapProductSource -> {
            ArticleTarget algoliaArticleTarget = new ArticleTarget();
            //Here create logique of conversion
            algoliaArticleTarget.setCode(sapProductSource.getCode());
            algoliaArticleTarget.setObjectID(sapProductSource.getCode().toString() + "_" + sapProductSource.getCatalog() + "_" + sapProductSource.getCatalogVersion());
            algoliaArticleTarget.setCatalogVersion(sapProductSource.getCatalog() + "_" + sapProductSource.getCatalogVersion());
            algoliaArticleTarget.setIntegrationKey(sapProductSource.getCode().toString() + "_" + sapProductSource.getCatalog() + "_" + sapProductSource.getCatalogVersion());

            List<CharSequence> charSeqList = new ArrayList<>();
            for (Category cat : sapProductSource.getSupercategories()) {
                charSeqList.add(cat.getCode().toString());
            }
            algoliaArticleTarget.setSupercategories(charSeqList);
            return algoliaArticleTarget;
        });

        target.to("algolia-target", Produced.with(Serdes.String(), algoliaArticleSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();


    }
}
