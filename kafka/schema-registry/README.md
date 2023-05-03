### This project is used to generate Avro Objects from schemas defined in src/resources/**.avsc
### Prerequisites 
Install JDK (1.8 Recommended)
Install Maven (3.6 or later)
check installation 
java -version
mvn --version

### How to generate Avro classes
Go to /schema-registry and execute

mvn generate-sources
Java classes will be generated in src/main/java
