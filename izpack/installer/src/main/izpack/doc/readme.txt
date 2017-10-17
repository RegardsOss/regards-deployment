1/ Description
______________

Identification                       : Regards
Date                                 : 2017-10-XX
Version                              : 1.1
Owner                                : CNES
Developer                            : CS SI
Type                                 : Complete
Project page                         : https://github.com/RegardsOss
Classification                       : Not Confidential - Opensource GPL V3
Characteristics                      : Microservice, NetflixOSS, Spring, Java, Javascript, React
Role/Function                        : Data valorization product
Reference tag                        : (1.1)

2/ Changes
______________

- 1.1 (2017-10-XX) : Initial version of Regards

	- Bugs fixed : to be completed

	- Known bugs : to be completed


3/ System requirements
______________________

Developement : Linux
Runtime      : Linux


Build tools :
-------------

.................................................................................................
Name                    |  Version        |   Provider                                          |
.................................................................................................
Java Development Kit    |  1.8            |   Oracle                                            |
Maven                   |  3.3+           |   Apache Software Foundation                        |
Groovy                  |  2.4+           |   Apache Software Foundation                        |
Node.js                 |  6.11.0 LTS     |   Linux Foundation                                  |
npm                     |  3+ and 4.x max |   Linux Foundation                                  |
IzPack                  |                 |                                                     |
.................................................................................................


Tools to install :
------------------

.................................................................................................
Name                    |  Version        |   Provider                                          |
.................................................................................................
Java Development Kit    |  1.8            |   Oracle                                            |
Maven                   |  3.3+           |   Apache Software Foundation                        |
Groovy                  |  2.4+           |   Apache Software Foundation                        |
Node.js                 |  6.11.0 LTS     |   Linux Foundation                                  |
.................................................................................................

Apache Software are distributed under Apache License version 2.0.


4/ Content
__________

1) Backend
	Access : two microservices Access, one for the instance frontend, one for the project frontend
			Administration : the microservice Administration
			Catalog : the microservice Catalog
			Cloud : the components Config, Registry, Gateway and the microservice Authentication
			Data Management : the microservice Data management
			Microservice : the core of Regards
			regards : the Maven BOM (Bill of Materials)

2) Client
	Frontend  : the web interface

3) Build modules
	Deployement : the IzPack installer of Regards

4) Documentation : the documentation of Regards
	Tests end-to-end : the test end-to-end of Regards for Selenium

5/ File extensions
__________________

.class          binary         Java class
.css            ascii          Web style sheet
.ejs            ascii
.gif            binary         Image
.html           ascii          HTML javadoc file
.iml            ascii
.java           ascii          Java source
.jar            binary         Java archive
.jpg            binary         Image
.js             ascii          Javascript source
.jsx            ascii          ExtenScript Script source
.m4             ascii          Python file
.properties     ascii          Java property file
.png            binary         Image
.txt            ascii          Text file
.xml_fra        ascii          XML file for french language
.xml_eng        ascii          XML file for english language
.xml            ascii          XML file
.xsd            ascii          XML schema
.yaml           ascii          Yaml file

6/ Build
________

git clone https://thor.si.c-s.fr/git/regards
cd regards
mvn install -DskipTests

cd ..
git clone https://thor.si.c-s.fr/git/rs-microservice
cd rs-microservice
mvn install -DskipTests

cd ..
git clone https://thor.si.c-s.fr/git/rs-admin
cd rs-admin
mvn install -DskipTests -P delivery

cd ..
git clone https://thor.si.c-s.fr/git/rs-cloud
cd rs-cloud
mvn install -DskipTests -P delivery

cd ..
git clone https://thor.si.c-s.fr/git/rs-dam
cd rs-dam
mvn install -DskipTests -P delivery

cd ..
git clone https://thor.si.c-s.fr/git/rs-catalog
cd rs-catalog
mvn install -DskipTests -P delivery

cd ..
git clone https://thor.si.c-s.fr/git/rs-access
cd rs-access
mvn install -DskipTests

cd ..
git clone https://thor.si.c-s.fr/git/rs-frontend
cd rs-frontend
mvn install -DskipTests -P delivery

7/ Build installer
__________________

git clone https://thor.si.c-s.fr/git/rs-deployment
cd rs-deployment
mvn install -DskipTests -P delivery

8/ Install
__________

cd izpack/installer/target
java -jar REGARDS-OSS-Installer.jar

9/ Start
________

In a standard installation, with all the microservices and a standard security level, the microservices can be started with the following commands :
{install_dir}/REGARDS/bin/start_microservice.sh -t config             && \
{install_dir}/REGARDS/bin/start_microservice.sh -t registry           && \
{install_dir}/REGARDS/bin/start_microservice.sh -t admin              && \
{install_dir}/REGARDS/bin/start_microservice.sh -t gateway            && \
{install_dir}/REGARDS/bin/start_microservice.sh -t dam                && \
{install_dir}/REGARDS/bin/start_microservice.sh -t catalog            && \
{install_dir}/REGARDS/bin/start_microservice.sh -t access-instance    && \
{install_dir}/REGARDS/bin/start_microservice.sh -t access-project     && \
{install_dir}/REGARDS/bin/start_microservice.sh -t authentication     && \
{install_dir}/REGARDS/bin/start_microservice.sh -t frontend

The following command allows to get the status of a microservice :
{install_dir}/REGARDS/bin/status_microservice.sh -t <microserviceName>

The following command allows to stop a microservice :
{install_dir}/REGARDS/bin/stop_microservice.sh -t <microserviceName>

If you need to install Regards with an enforced security level, see the Regards Manual Installation.

10 / Copyright
______________

This software distribution is covered by this copyright notice.

All third-party libraries redistributed with this software remain the property
of their respective copyright owners and are subject to separate license
agreements.


11 / License
____________

You can obtain a copy of the GPL 3.0 license at
http://www.opensource.org/licenses/GPL-3.0
