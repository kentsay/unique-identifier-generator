# README #

Hibernate prefixed ID generator proof of concept.

### Usage ###

1. `mvn package` to generate `jar`
2. `java -jar target/poc-id-generator-<version>.jar baseline|custPrefixed|seqPrefixed [# of threads] [# of iterations]`
