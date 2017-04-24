#!/usr/bin/env groovy

pipeline {
    agent any
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
        stage('Build') {
            steps {
                sh 'mvn -U -P delivery clean package org.jacoco:jacoco-maven-plugin:0.7.7.201606060606:prepare-agent sonar:sonar -fae -Dsonar.jacoco.reportPath=${WORKSPACE}/jacoco-ut.exec -Dsonar.jacoco.itReportPath=${WORKSPACE}/jacoco-it.exec' 
            }
            post {
                success {
                    junit '**/target/surefire-reports/*.xml, **/target/failsafe-reports/*.xml' 
                }
            }
        }
        stage('Install') {
            steps {
                echo 'Restoring the VM to SNPASHOT'
				sh 'ssh jenkins@172.26.46.49 "/opt/vmshell/bin/vmoperation --vmname regard-ic --operation revert"'
				sh 'ssh jenkins@172.26.46.49 "/opt/vmshell/bin/vmoperation --vmname regard-ic --ipaddress 172.26.47.95 --operation poweron"'
				sh 'ssh -t rsins@172.26.47.95 "set -xe && \
					mkdir -p LIVRAISON && \
					cd LIVRAISON && \
					scp jenkins@172.26.46.158:workspace/rs-deployment/izpack/installer/target/REGARDS-OSS-Installer.jar . && \
					scp jenkins@172.26.46.158:workspace/rs-deployment/izpack/installer/src/test/resources/auto-install-ic.xml . && \
					java -jar REGARDS-OSS-Installer.jar auto-install-ic.xml"'
            }
        }
        stage('Start') {
            steps {
                echo 'Starting..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}