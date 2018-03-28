# REGARDS deployment

This repository is used to build IZPACK jar for system deployment.

REGARDS is still under heavy development. Operational version V1.0.0 is planed for 2018.

:warning: If you want to run regards-frontend compilation using root user you have to add the hereunder line in your .npmrc file.
If your root user does not have any .npmrc file yet, you can create it in your root user home directory. You can read [npm documentation about npmrc here](https://docs.npmjs.com/files/npmrc). 
```sh
unsafe-perm=true
```

## Build requirements

Build relies on :
* Maven 3.3+
* OpenJDK 8
* Groovy 2.4+
* Node.js 6.11.0 LTS

Prerequisite tools :
* Elasticsearch 5.4
* PostgreSQL 9.6
* RabbitMQ 3.6.5

Dependencies : 
* All artifacts must have been built.

```shell
export REGARDS_HOME=`pwd`

git clone https://github.com/RegardsOss/regards-bom.git
cd regards-bom
mvn install -DskipTests

cd ..
git clone https://github.com/RegardsOss/regards-microservice.git
cd regards-microservice
mvn install -DskipTests

cd ..
git clone https://github.com/RegardsOss/regards-admin.git
cd regards-admin
mvn install -DskipTests -P delivery

cd ..
git clone https://github.com/RegardsOss/regards-cloud.git
cd regards-cloud
mvn install -DskipTests -P delivery

cd ..
git clone https://github.com/RegardsOss/regards-dam.git
cd regards-dam
mvn clean install -Dmaven.test.skip=true -P delivery

cd ..
git clone https://github.com/RegardsOss/regards-catalog.git
cd regards-catalog
mvn install -DskipTests -P delivery

cd ..
git clone https://github.com/RegardsOss/regards-access.git
cd regards-access
mvn install -DskipTests

cd ..
git clone https://github.com/RegardsOss/regards-frontend.git
cd regards-frontend
mvn install -DskipTests
```

## Build

```shell
cd ..
git clone https://github.com/RegardsOss/regards-deployment.git
cd regards-deployment
mvn install -DskipTests -P delivery
```

## Install

```shell
cd izpack/installer/target
java -jar REGARDS-OSS-Installer.jar
```
