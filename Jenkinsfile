#!/usr/bin/env groovy

/*
 * Copyright 2017 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
 *
 * This file is part of REGARDS.
 *
 * REGARDS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * REGARDS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with REGARDS. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Declarative Jenkinsfile. The language is Groovy.
 * Contains the definition of a Jenkins Pipeline, is checked into source control
 * and is expected to be the single source of truth.
 *
 * @author Xavier-Alexandre Brochard
 * @author Christophe Mertz
 *
 * @see https://jenkins.io/doc/book/pipeline/jenkinsfile/
 */
pipeline {
  agent { label 'unix-integration' }
  tools {
    maven 'Maven 3.3.9'
    jdk 'jdk8'
  }
  stages {
    stage ('Initialize') {
      steps {
        sh '''
          echo "PATH = ${PATH}"
          echo "M2_HOME = ${M2_HOME}"
        '''
      }
    }
    stage('Package') {
      steps {
        // Apache Maven related side notes:
        // --batch-mode : recommended in CI to inform maven to not run in interactive mode (less logs)
        // -V : strongly recommended in CI, will display the JDK and Maven versions in use.
        //      Very useful to be quickly sure the selected versions were the ones you think.
        // -U : force maven to update snapshots each time (default : once an hour, makes no sense in CI).
        sh 'export MAVEN_M2=~/.m2 && mvn -V -U -P delivery,CI clean package org.jacoco:jacoco-maven-plugin:0.7.7.201606060606:prepare-agent sonar:sonar -Dsonar.jacoco.reportPath=${WORKSPACE}/jacoco-ut.exec -Dsonar.jacoco.itReportPath=${WORKSPACE}/jacoco-it.exec -Dsonar.branch=develop -DcmdLineTarget=target'
      }
      post {
        success {
          junit '**/target/surefire-reports/*.xml, **/target/failsafe-reports/*.xml'
        }
      }
    }
    stage('Stop') {
      when {
			anyOf {
				branch 'develop'
		    }
	  }
      steps {
        sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh stop" || true'
      }
    }
    stage('Clean') {
        when {
			anyOf {
				branch 'develop'
		    }
	    }
    	steps {
        // Retour au SNAPSHOT
        // Les VM-Cli ne marchent pas sur centos 7 (pb perl ou ?). Tant que ce n'est pas résolu,
        // utilisation de la VM-IC SAG comme passerelle
        //sh 'ssh jenkins@172.26.46.49 "/opt/vmshell/bin/vmoperation --vmname regard-ic --operation revert"'
        //sh 'ssh jenkins@172.26.46.49 "/opt/vmshell/bin/vmoperation --vmname regard-ic --ipaddress 172.26.47.95 --operation poweron"'
        // Delete a previous LIVRAISON folder
        sh 'ssh -t rsins@172.26.47.95 "rm -rf LIVRAISON"'
        // Delete the previous installation
        sh 'ssh -t rsins@172.26.47.95 "rm -rf /opt/regards/regards-ic/*"'
      }
    }
    stage('Deploy') {
	    when {
			anyOf {
				branch 'develop'
			}
		}
	    steps {
	        // Deploy installer to a LIVRAISON folder
	        sh 'ssh -t rsins@172.26.47.95 "mkdir -p LIVRAISON"'
	        sh 'ssh -t rsins@172.26.47.95 "rm -f LIVRAISON/REGARDS-OSS-Installer.jar"'
	        sh 'scp izpack/installer/target/REGARDS-OSS-Installer.jar rsins@172.26.47.95:LIVRAISON'
	        sh 'scp izpack/installer/src/test/resources/auto-install-ic.xml rsins@172.26.47.95:LIVRAISON'
	    }
    }
    stage('Install') {
      // Installation continue sur la VM regard-ic (172.26.47.95)
      when {
			anyOf {
				branch 'develop'
			}
		}
        steps {
        	// The installation
        	sh 'ssh -t rsins@172.26.47.95 "cd LIVRAISON && java -jar REGARDS-OSS-Installer.jar auto-install-ic.xml"'
        }
    }
    stage('Start') {
      when {
			  anyOf {
				  branch 'develop'
		    }
	    }
      steps {
        sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh start"'
      }
    }
    stage('Check - 1') {
      when {
			anyOf {
				branch 'develop'
			}
		}
      steps {
        parallel(
          config: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t config status"'
          },
          registry: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t registry status"'
          },
          admin: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t admin status"'
          },
          admininstance: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t admin-instance status"'
          },          
          gateway: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t gateway status"'
          },
          acessinstance: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t access-instance status"'
          },
          accessproject: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t access-project status"'
          },
          authentication: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t authentication status"'
          }
        )
      }
    }  
    stage('Check - 2') {
      when {
			  anyOf {
				  branch 'develop'
		      }
	    }
      steps {
        parallel(
          storage: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t storage status"'
          },
          dam: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t dam status"'
          },
          catalog: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t catalog status"'
          },
          dataprovider: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t dataprovider status"'
          },
          order: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t order status"'
          },
          ingest: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t ingest status"'
          },
          frontend: {
            sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh -t frontend status"'
          }
        )
      }
    }  
  }
}
