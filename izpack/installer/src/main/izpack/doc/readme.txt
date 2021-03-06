1/ Description
______________

Identification                       : Regards
Date                                 : 2020-07-01
Version                              : 1.2.0
Owner                                : CNES
Developer                            : CS SI
Type                                 : Complete
Project page                         : https://regardsoss.github.io/
Classification                       : Not Confidential - Opensource GPL V3
Characteristics                      : Microservice, NetflixOSS, Spring, Java, Javascript, React
Role/Function                        : Data valorization product
Reference tag                        : (V1.2.0)

2/ Changes
______________

- 1.2.0 (2020-07-01) :
       - New Features :
             1 : (Frontend) New search module

- 1.0.0 (2020-03-12) :
       - New Features :
             1 : (Storage & ingest) New system for OAIS data archival. New SIP Collection format.
             2 : (Storage) Deletion of "dispatch stategy" plugins. Storage location is now directly provided to storage microservice.
             3 : (Storage : Local storage plugin) Allow to store "little" files in a common zip archive.
             4 : (Storage) A new copy system is available to copy stored files between two configured storage location.
             5 : (Datamangement) New default user role : Operator.
             6 : (Frontend) New administration pages to monitor dataprovider, OAIS archival and storage.
             7 : (Frontend) New user module to display entties description.

- 0.4.1 (2019-07-31) :
        - Bug fixes :
              1 : (Frontend) Display of HTML files in entities description 
              2 : (Frontend) Add visible loading information on access-rights page when loading datasets
              3 : (Frontend) Fix access-rights configuration when using custom access by plugin
              4 : (Frontend) Fix bugs on quicklooks display
              5 : (Frontend) Fix render of entities properties on map view
              6 : (Frontend) Fix too long request when loading attributes associated to a dataset in search-from module configuration
              7 : (Framework) Handle disabled plugins in a global way
              8 : (Framework) Fix notification issues
              9 : (Storage) Fix API session filters on date
              10 : (Dataprovider) fix multiple bugs on acquisition process
              11 : (Ingest) : Fix SIP deletion when a same provider identified SIP is in DELETED state
              12 ! (Administration) : Fix user account creation issue
              13 : (Documentation) : Update API documentation for new version
              14 : (Documentation) : Fix license version in some files

- 0.4.0 (2019-06-17) :
        - New features :
              1 : COTS upgrade : OpenJDK 8, Spring Boot 2.1, ElasticSearch 6+, etc.
              2 : Full text search
              3 : Access right definition at the group level with gliding criterion
              4 : Data flow
              5 : Scalability improvements
              6 : Geospatial search & vizualisation with Mizar
              7 : New datasource plugin to crawl datas from external web services (opensearch / geojson features)

- 0.3.0 (2018-11-07) :
        - New features :
              1 : OpenSearch implementation with Geo, Time and Parameter extensions for interoperability
              2 : Geospatial data feature (point, line string, polygon, multi polygon) on Earth, Mars and Celestial vault (aka Astro)
              3 : Geospatial search with convex polygon, circle and bounding box on Earth, Mars and Astro including poles
              4 : RegardsDownloader
 
- 0.2.0 (2018-07-01) : 
        - New features :
               1 : REGARDS framework
               2 : Partial implementation of the OAIS recommandation with administration, data management and access microservices
               3 : Backend and frontend plugins
               4 : Granular access rights on API endpoints and data ingested

- 0.1.1 (2017-10-24) : Initial version of Regards

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

- 2.0 (2018-05-18) : 

	- Bugs fixed : 

	- Known bugs : 

3/ System requirements
______________________

Developement : Linux
Runtime      : Linux


Build tools :
-------------

.................................................................................................
Name                    |  Version        |   Provider                                          |
.................................................................................................
OpenJDK                 |  8              |   Oracle                                            |
Maven                   |  3.3+           |   Apache Software Foundation                        |
Groovy                  |  2.4+           |   Apache Software Foundation                        |
Node.js                 |  8.10+          |   Linux Foundation                                  |
npm                     |  5.7.1+         |   Linux Foundation                                  |
IzPack                  |                 |                                                     |
.................................................................................................


Tools to install :
------------------

.................................................................................................
Name                    |  Version        |   Provider                                          |
.................................................................................................
OpenJDK                 |  8              |   Oracle                                            |
Maven                   |  3.3+           |   Apache Software Foundation                        |
Groovy                  |  2.4+           |   Apache Software Foundation                        |
Node.js                 |  8.10           |   Linux Foundation                                  |
.................................................................................................

Apache Software are distributed under Apache License version 2.0.

Prerequisite tools :
--------------------

.................................................................................................
Name                    |  Version        |   Provider                                          |
.................................................................................................
Elasticsearch           |  6.5            |   Elastic                                           |
PostgreSQL              |  9.6+           |   PostgreSQL                                        |
RabbitMQ                |  3.6.5          |   Pivotal                                           |
.................................................................................................

In order to install the frontend, you need to be sure that your user has write access to the global module folder. If you have an EACCESS issue while executing `touch $(npm config get prefix)/lib/node_modules/test`, npm has [released a guide to fix it](https://docs.npmjs.com/getting-started/fixing-npm-permissions)

4/ Content
__________

1) Backend
	Access : two microservices Access, one for the instance frontend, one for the project frontend
			Access : the microservices Access-Project and Access-Instance
			Administration : the microservices Administration and Administration-Instance
			Catalog : the microservice Catalog
			Cloud : the components Config, Registry, Gateway and the microservice Authentication
			Data Management : the microservice Data management
			Data Provider : the microservice Data management
			Ingest : the microsrvice Ingest
			Microservice : the core of Regards
			Order : the microsrvice Order
			regards-bom : the Maven BOM (Bill of Materials)
			Storage : the microservice Storage

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

6/ Build & install
________

<<<<<<< HEAD
You can find out the tutorial on the README.md file at the root of this repository.
=======
:warning: If you want to run regards-frontend compilation using root user you have to add the hereunder line in your .npmrc file.
If your root user does not have any .npmrc file yet, you can create it in your root user home directory. You can read [npm documentation about npmrc here](https://docs.npmjs.com/files/npmrc). 
```sh
unsafe-perm=true
```


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
>>>>>>> 5e5ba22980d20cfc186ee06d1c3f76bacbc1a843

7/ Start
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
{install_dir}/REGARDS/bin/start_microservice.sh -t ingest             && \
{install_dir}/REGARDS/bin/start_microservice.sh -t dataprovider       && \
{install_dir}/REGARDS/bin/start_microservice.sh -t order              && \
{install_dir}/REGARDS/bin/start_microservice.sh -t frontend

The following command allows to get the status of a microservice :
{install_dir}/REGARDS/bin/status_microservice.sh -t <microserviceName>

The following command allows to stop a microservice :
{install_dir}/REGARDS/bin/stop_microservice.sh -t <microserviceName>

If you need to install Regards with an enforced security level, see the Regards Manual Installation.

8 / Copyright
______________

This software distribution is covered by this copyright notice.

All third-party libraries redistributed with this software remain the property
of their respective copyright owners and are subject to separate license
agreements.


9 / License
____________

You can obtain a copy of the GPL 3.0 license at
http://www.opensource.org/licenses/GPL-3.0
