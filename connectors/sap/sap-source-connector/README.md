Welcome to your new Kafka Connect connector!

# Running in development

```
mvn clean package
export CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')"
$CONFLUENT_HOME/bin/connect-standalone $CONFLUENT_HOME/etc/schema-registry/connect-avro-standalone.properties config/MySourceConnector.properties
```

````
mvn archetype:generate -DarchetypeGroupId=io.confluent.maven -DarchetypeArtifactId=kafka-connect-quickstart -DarchetypeVersion=0.10.0.0 \
-Dpackage=com.sqli.kafka.connect.sap \
-DgroupId=com.sqli.kafka.connect.sap \
-DartifactId=sap-source-connector \
-Dversion=1.0-SNAPSHOT
````

````
curl -XPOST "http://127.0.0.1:8083/connectors" -H "Content-Type: application/json" -d'{ "name": "sap-source-connector", "config": { "connector.class": "com.sqli.kafka.connect.sap.SapSourceConnector", "tasks.max": "1", "sap.base.url": "https://64352fbc537112453fcfa8b2.mockapi.io/commerce/api", "sap.username": "admin", "sap.password": "secret", "sap.api.version": "v1", "sap.topic": "sap-raw-data"} }'
````
