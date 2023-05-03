### Prerequisites

If you don't have docker installed, follow the steps [here](https://docs.docker.com/get-docker/) to install it.

Install docker-compose from this [link](https://docs.docker.com/compose/install/other/) if you don't have it already

### Overview

This setup will replicate a real deployment of a Kafka cluster which includes:

* Zookeeper: Used to manage the cluster membership and configuration
* Kafka: The Kafka broker, this is the core component of the Kafka platform
* Schema-registry: Used to manage schemas for Kafka messages
* Kafka-connect: Used as a container to build and run connectors to move data in and out of Kafka

We use the confluent distribution (Community Edition) of Kafka, which is free, open source and provides a complete Kafka cluster with additional tools and features i.e  Schema Registry and Kafka Connect, which are required for this project.

### Getting Started

From the project root, go to the Docker directory

```
cd Doker
```

Copy the .env.sample file to a .env file and make sure all the variables are correctly set

```
cp .env.sample .env
```

Make sure the port 8080 is free and no containers with names, zookeeper, kafka, schema-registry, kafka-connect and conduktor exist, then run the Kafka cluster.

```
docker-compose up -d
```

To check your setup go to the conduktor UI [http://localhost:8080/](http://localhost:8080/)

Shut down the cluster

```
docker-compose down
```