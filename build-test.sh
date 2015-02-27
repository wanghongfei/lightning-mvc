#!/bin/bash

echo "***************** rebuilding framework ********************"
mvn clean install


echo "***************** rebuilding test project ********************"
cd ../ligntning-sample
mvn clean install