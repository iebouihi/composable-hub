package com.sqli.composablehub.kafka.client;

import com.sqli.composablehub.kafka.config.KafkaConfigData;
import com.sqli.composablehub.kafka.config.RetryConfigData;
import com.sqli.composablehub.kafka.exception.KafkaClientException;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.apache.kafka.clients.admin.CreateTopicsResult;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class KafkaClient {
    private final KafkaConfigData kafkaConfigData;

    private final RetryConfigData retryConfigData;

    private final KafkaAdminClient adminClient;

    private final RetryTemplate retryTemplate;

    public KafkaClient(KafkaConfigData kafkaConfigData, RetryConfigData retryConfigData, KafkaAdminClient adminClient, RetryTemplate retryTemplate) {
        this.kafkaConfigData = kafkaConfigData;
        this.retryConfigData = retryConfigData;
        this.adminClient = adminClient;
        this.retryTemplate = retryTemplate;
    }

    public void createTopic() {
        CreateTopicsResult createTopicsResult;
        try {
            if (!topicExists()) {
                createTopicsResult = retryTemplate.execute(this::doCreateTopic);
                log.info("Create topic result {}", createTopicsResult.values().values());
            } else {
                log.info("Topic {} already exist, skipping creation ..", kafkaConfigData.getTopicName());
            }
        } catch (Throwable t) {
            throw new KafkaClientException("Reached max number of retry for creating kafka topic!", t);
        }
    }

    private boolean topicExists() {
        boolean exists = false;
        try {
            ListTopicsResult listTopics = adminClient.listTopics();
            Set<String> names = listTopics.names().get();
            exists = names.contains(kafkaConfigData.getTopicName());
        } catch (Throwable t) {
            throw new KafkaClientException("Reached max number of retry for creating kafka topic!", t);
        }
        return exists;
    }

    private CreateTopicsResult doCreateTopic(RetryContext retryContext) {
        String topicName = kafkaConfigData.getTopicName();
        log.info("Creating topic {}, attempt {}", topicName, retryContext.getRetryCount());
        NewTopic kafkaTopic = new NewTopic(
                topicName.trim(),
                kafkaConfigData.getNumOfPartitions(),
                kafkaConfigData.getReplicationFactor()
        );
        return adminClient.createTopics(List.of(kafkaTopic));
    }
}
