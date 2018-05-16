# REGARDS deployment

This repository is used to build IZPACK jar for system deployment.

REGARDS is still under heavy development. Operational version V1.0.0 is planed for 2018.

## Build requirements

Build relies on :
* Maven 3.3+
* OpenJDK 8
* Groovy 2.4+
* Node.js 8.10+
* Npm 5.7.1+

Prerequisite tools :
* Elasticsearch 5.1.1
* PostgreSQL 9.4+
* RabbitMQ 3.6.5

Dependencies : 
* All artifacts must have been built.

```bash
git clone https://github.com/RegardsOss/regards-bom.git
cd regards-bom
mvn install -DskipTests -P install

cd ..
git clone https://github.com/RegardsOss/regards-microservice.git
cd regards-microservice
mvn install -DskipTests -P install,delivery

cd ..
git clone https://github.com/RegardsOss/regards-admin.git
cd regards-admin
mvn install -DskipTests -P install,delivery

cd ..
git clone https://github.com/RegardsOss/regards-cloud.git
cd regards-cloud
mvn install -DskipTests -P install,delivery 

cd ..
git clone https://github.com/RegardsOss/regards-storage.git
cd regards-storage
mvn install -DskipTests -P install,delivery

cd ..
git clone https://github.com/RegardsOss/regards-dam.git
cd regards-dam
mvn clean install -Dmaven.test.skip=true -P install,delivery

cd ..
git clone https://github.com/RegardsOss/regards-catalog.git
cd regards-catalog
mvn install -DskipTests -P install,delivery

cd ..
git clone https://github.com/RegardsOss/regards-access.git
cd regards-access
mvn install -DskipTests -P install,delivery

cd ..
git clone https://github.com/RegardsOss/regards-ingest.git
cd regards-ingest
mvn install -DskipTests -P install,delivery

cd ..
git clone https://github.com/RegardsOss/regards-dataprovider.git
cd regards-dataprovider
mvn install -DskipTests -P install,delivery

cd ..
git clone https://github.com/RegardsOss/regards-order.git
cd regards-order
mvn install -DskipTests -P install,delivery

cd ..
git clone https://github.com/RegardsOss/regards-frontend.git
cd regards-frontend/webapp
npm install
npm run build:production
npm run build:plugins
cd ../frontend-boot/
mvn clean install -DskipTests -Dwebapp.dir=../webapp -P install
```

## Build

```bash
cd ../..
git clone https://github.com/RegardsOss/regards-deployment.git
cd regards-deployment
mvn install -DskipTests -P install,delivery
```

## Install

To install with the user interface use the command below:

```bash
cd izpack/installer/target
java -jar REGARDS-OSS-Installer.jar 
```

If you have a XML configuration file for automatic installation use the command below:

```bash
cd izpack/installer/target
java -jar REGARDS-OSS-Installer.jar auto-install-config.xml
```