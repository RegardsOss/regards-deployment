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
                echo 'Building..'
                sh 'mvn -U -P delivery clean package org.jacoco:jacoco-maven-plugin:0.7.7.201606060606:prepare-agent sonar:sonar -fae -Dsonar.jacoco.reportPath=${WORKSPACE}/jacoco-ut.exec -Dsonar.jacoco.itReportPath=${WORKSPACE}/jacoco-it.exec' 
            }
            post {
                success {
                    junit '**/target/surefire-reports/*.xml, **/target/failsafe-reports/*.xml' 
                }
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Install') {
            steps {
                echo 'Install..'
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