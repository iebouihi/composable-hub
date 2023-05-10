## Overview

The SAP source connector was created from the kafka-connect-quickstart [maven artifact](https://mvnrepository.com/artifact/io.confluent.maven/kafka-connect-quickstart/0.10.0.0)

Source code scaffold was generated using this maven command.

````
mvn archetype:generate -DarchetypeGroupId=io.confluent.maven -DarchetypeArtifactId=kafka-connect-quickstart -DarchetypeVersion=0.10.0.0 \
    -Dpackage=com.sqli.kafka.connect.sap \
    -DgroupId=com.sqli.kafka.connect.sap \
    -DartifactId=sap-source-connector \
    -Dversion=1.0-SNAPSHOT
````

## Prerequisites

* Java 11
* Maven 3.6+
* Kafka broker version 3.4+
* Kafka connect version 3.4+

## Running in development

Build the jar using

````
mvn clean install package
````

Find the fat jar in the target repository sap-source-connector-<version>-fat.jar and copy it to the Docker/connectors folder.

In the Kafka connect container run this curl command to create the source connector

````
curl -XPOST "http://127.0.0.1:8083/connectors" -H "Content-Type: application/json" -d'{
  "name": "sap-source-connector",
  "config": {
    "connector.class": "com.sqli.kafka.connect.sap.SapSourceConnector",
    "tasks.max": "1",
    "sap.base.url": "https://64352fbc537112453fcfa8b2.mockapi.io/commerce/api",
    "sap.username": "admin",
    "sap.password": "secret",
    "sap.api.version": "v1",
    "sap.topic": "sap-raw-data"
  }
}'
````

### Configuration

| Property | Required | Description |   
| --- | --- | --- |  
| connector.class | True | The main source connector class, should be set to com.sqli.kafka.connect.sap.SapSourceConnector | 
| tasks.max  | True | The maximum number of tasks allowed, should be set to 1 | 
| sap.base.url  | True | The SAP api base url | 
| sap.username  | True | The SAP api username used for basic authentication | 
| sap.password  | True | The SAP api password used for basic authentication | 
| sap.api.version  | False | The SAP api version, set to v1 by default | 
| sap.topic  | False | The target topic to send data to, set to sap-raw by default | 
| sap.batch.size  | False | Window of kafka records to pull from SAP API set to 100 by default | 
| sap.poll.interval  | False | Poll interval in milliseconds, set to 5000 by default (5 seconds) | 
---

## Run the tests

````
mvn test
````
