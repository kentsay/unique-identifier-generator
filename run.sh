#!/bin/bash

mvn clean
mvn package

for strategy in baseline custPrefixed seqPrefixed 
do
  for numThread in 1 10 50 100
  do
    for numIteration in 200 400 600 800 1000
    do 
      java -jar target/poc-id-generator-1.0-SNAPSHOT.jar $strategy $numThread $numIteration
    done
  done
done
