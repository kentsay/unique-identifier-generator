

## Hibernate prefixed ID generator proof of concept ###
---

### Usage ###

You can either run our shell scripts:

```
sh run.sh
```

or you can do it running the following command:

```
mvn package
java -jar target/poc-id-generator-<version>.jar baseline|custPrefixed|seqPrefixed [# of threads] [# of iterations]
```
