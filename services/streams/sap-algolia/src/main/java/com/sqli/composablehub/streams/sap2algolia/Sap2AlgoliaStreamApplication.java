package com.sqli.composablehub.streams.sap2algolia;

import com.sqli.composablehub.algolia.models.ArticleTarget;
import com.sqli.composablehub.sap.models.SAPProductRaw;
import com.sqli.composablehub.streams.sap2algolia.config.SapToAlgoliaStreamConfigData;
import com.sqli.composablehub.streams.sap2algolia.impl.Sap2AlgoliaTransformer;
import com.sqli.composablehub.streams.sap2algolia.streams.SapAlgoliaStreamProcessor;
import com.sqli.composablehub.streamscore.config.KafkaStreamConfigData;
import com.sqli.composablehub.streamscore.streams.KafkaStreamProcessor;
import com.sqli.composablehub.streamscore.transformation.StreamTransformer;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.util.Collections;

@SpringBootApplication
@EnableConfigurationProperties(SapToAlgoliaStreamConfigData.class)
@Configuration
@EnableKafka
@EnableKafkaStreams
public class Sap2AlgoliaStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sap2AlgoliaStreamApplication.class, args);
	}
	@Bean
	StreamTransformer streamTransformer() {
		return new Sap2AlgoliaTransformer();
	}


	@Autowired
	private SapToAlgoliaStreamConfigData sapToAlgoliaStreamConfigData;



	@Bean
	Serde sourceSerde() {

		Serde sourceSerde = new SpecificAvroSerde<SAPProductRaw>();
		sourceSerde.configure(Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, sapToAlgoliaStreamConfigData.getSchemaRegistryUrl()), false);
		return new SpecificAvroSerde<SAPProductRaw>();
	}

	@Bean
	Serde targetSerde() {
		Serde targetSerde = new SpecificAvroSerde<ArticleTarget>();
		targetSerde.configure(Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, sapToAlgoliaStreamConfigData.getSchemaRegistryUrl()), false);
		return new  SpecificAvroSerde<ArticleTarget>();
	}

	@Bean
	SapAlgoliaStreamProcessor sapAlgoliaStreamProcessor()
	{
		return new SapAlgoliaStreamProcessor(sapToAlgoliaStreamConfigData);
	}

}
