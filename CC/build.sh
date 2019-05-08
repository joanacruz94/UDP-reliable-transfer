#!/bin/bash
rm -r ./src/ServerBuild/*
rm -r ./src/ClientBuild/*
cd ./src
javac -d ServerBuild/ Server/*.java Common/*.java
javac -d ClientBuild/ Client/*.java Common/*.java
cd ServerBuild
jar cfv Server.jar *
cd ..
cd ClientBuild
jar cfv Client.jar *

