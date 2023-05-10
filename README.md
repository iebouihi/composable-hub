# composable-hub
## Overview
Composable hub is a framwework build on Top of Kafka to easliy integrates solutions in a composable architecture contexts.
Kafka's is designed to be scalable, fault-tolerant, and high-performance. Kafka ( and so Compoable Hub) can handle large volumes of data with high throughput and low latency.
### Concepts
* Connectors :  Kafka Connect or Producers based apps to pull or push data from systems such as ERP, CRM, Commerce ...etc.
* Transformers : Kafka Streams Apps that are responsible of converting data from source system to target system. Examle adpating Odata payload from Commere to indexing API paylaod of Alolia.
* Schema Registry :  Avor based scheam definitions to control structure over systems. 

# Project structure
## Docker
Define container used in this framework such as Kafka broker, Connect, Schema registry et services. With docker compose it is easy de run the cluster for developpement purpose.
## Connectors 
Contains different connectors.
## Kafka/Kafka-core
Module that contains shared feature with all Kafka components. It's make easy to manage topics, abstract retry processes...etc.
## Kafka/Schema registry
Mododules that contains all data structure in Avro format with calsses generation to make easy mapping data structures beteween different systems.

# Run the Compsable hub 
##Compilation
Go the root folder
```
mvn clean install
```
This will compile all components and cores.
In next paragraph, each component has ReadME file.


[Running with docker](./Docker/DOC.md)
## Kafka Modules
* [Kafka Core](./kafka/kafka-core/DOC.md)
