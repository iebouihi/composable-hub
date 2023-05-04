### Overview

This module provides utility functions to manage and send commands to the Kafka broker.
This should be added as a dependency to your microservice then all the calls to the Kafka broker provided by the client will be driven from your microservice configuration.

### Usage

Add this dependency to your microservice

````
<dependency>
    <groupId>com.sqli.composablehub</groupId>
    <artifactId>kafka-core</artifactId>
</dependency>
````

Adjust this config based on your cluster settings application.yml

````
kafka-config:
  bootstrap-servers: localhost:9092
  schema-registry-url-key: schema.registry.url
  schema-registry-url: http://localhost:8081
  topic-name: sap-raw
  num-of-partitions: 1
  replication-factor: 1
  
retry-config:
  initial-interval-ms: 1000
  max-interval-ms: 10000
  multiplier: 2.0
  max-attempts: 3
````

To create a new topic you just need to inject the KafkaClient object and call the createTopic() method

````
private final KafkaClient kafkaClient;
public void init() {
    kafkaClient.createTopic();
}
````

#### Kafka Config

* bootstrap-servers: A comma separated list of urls to indicate the available kafka broker nodes
* schema-registry-url-key: The key used in your producer or consumer object
* schema-registry-url: The schema registry URL
* topic-name: The topic your microservice is producing data to or consuming from
* num-of-partitions: The number of partitions to create in the topic
* replication-factor: The data replication factor

#### Retry Config

We are using spring boot RetryTemplate to retry failed operations. Using the ExponentialBackOffPolicy which exponentially increases the back off period after each retry attempt.

* initial-interval-ms: The initial period to sleep on the first backoff.
* max-interval-ms: The maximum interval to sleep for.
* multiplier: The multiplier to use to generate the next backoff interval from the last.
* max-attempts: Max number of attempts before failing the operation.