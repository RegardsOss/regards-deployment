1/ Description
______________

Identification                       : Regards
Date                                 : 2017-10-24
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

- 1.1 (2017-10-24) : Initial version of Regards

	- Bugs fixed : 
		1  : Inscription d'un nouvel utilisateur via l'IhmUser : il ne reçoit pas le mail
		7  : Création d'un module "search-form"
		8 : POP-UP d'erreur qui s'ouvre en bas de l'écran des Ihms
		10 : Le Menu de navigation est vide
		11 : Snapshot maven
		12 : Métadonnées dans les pom.xml
		16 : Problème pour la génération de izPack
		17 : Les fichiers de logs ne sont pas limités en taille
		18 : Filtres à facettes dans l'Ihm utilisateur
		19 : Installation par Izpack
		23 : Navigation par graphe
		24 : Reprise de REGARDS aprés un arrêt-relance de la Base de données POstgres
		25 : Creation de la collection JASON2
		26 : Initialisation d'un nouveau projet
		27 : Plugins temporal et two-temporal
		28 : Ré-initialisation du mot de passe non conforme
		32 : Le bouton "details" a disparu dans la Navigation par graphe
		35 : Module home-page
		37 : Association d'un formulaire de recherche à tous les jeux d'un modèle de jeux de données
		38 : Ajout d'un plugin IHM
		39 : Création d'une collection ou d'un jeu de données avec description locale
		40 : Espace en début et en fin de chaîne de caractéres
		42 : Remarques sur le look de l'Ihm utilisateur
		43 : Remise à zéro d'une valeur dans l'Ihm utilisateur
		47 : Rendu des fichiers Markdown dans Regards
		49 : Download d'un fichier de données
		50 : Recherche des entités portant un tag
		51 : Thumbnail vide dans l'Ihm utilisateur

	- Known bugs : 
		2 : Changement de rôle
		3 : Remarques sur les IHMs
		5 : Informations dans la vue des microservices de l'IhmAdmin d'un projet
		6 : Placer un nouveau module dans un nouveau conteneur
		13 : IMPORT du modele SSALTO
		20 : Mots de passe en clair
		21 : Description openSeach
		22 : Problème de désérialisation json (subtype)
		29 : Réponse openSearch n'est pas conforme
		30 : Utilisation de la pagination de openSearch
		36 : Utilisation de la BD postgresql pendant l'import depuis une BD oracle
		41 : Modification d'un modèle de données
		44 : Composant FontFamily du THEME
		45 : Fichier Markdown ou PDF contenant la description d'une collection ou d'un jeu
		46 : Enlever la description markdown d'une collection
		48 : Remarques Ihm utilisateur


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

Prerequisite tools :
--------------------

.................................................................................................
Name                    |  Version        |   Provider                                          |
.................................................................................................
Elasticsearch           |  5.1.1          |   Elastic                                           |
PostgreSQL              |  9.4+           |   PostgreSQL                                        |
RabbitMQ                |  3.6.5          |   Pivotal                                           |
.................................................................................................

In order to install the frontend, you need to be sure that your user has write access to the global module folder. If you have an EACCESS issue while executing `touch $(npm config get prefix)/lib/node_modules/test`, npm has [released a guide to fix it](https://docs.npmjs.com/getting-started/fixing-npm-permissions)

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
git clone https://thor.si.c-s.fr/git/rs-frontend
git clone https://thor.si.c-s.fr/git/rs-cloud
cd rs-frontend/frontend-webapp/src/main/webapp/
npm run bootstrap
npm install
npm run build:production

cd ../../../../../
mkdir -p ./rs-cloud/rs-frontend/frontend-webapp/src/main/webapp/dist
cp rs-frontend/frontend-webapp/src/main/webapp/dist ./rs-cloud/rs-frontend/frontend-webapp/src/main/webapp/dist
cd rs-cloud
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

7/ Build installer
__________________

cd ..
git clone https://github.com/RegardsOss/regards-deployment.git
cd regards-deployment
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
{install_dir}/REGARDS/bin/start_microservice.sh -t admin-instance     && \
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
