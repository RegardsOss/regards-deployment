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
    stage('Package') {
      steps {
        // Apache Maven related side notes:
        // --batch-mode : recommended in CI to inform maven to not run in interactive mode (less logs)
        // -V : strongly recommended in CI, will display the JDK and Maven versions in use.
        //      Very useful to be quickly sure the selected versions were the ones you think.
        // -U : force maven to update snapshots each time (default : once an hour, makes no sense in CI).
        sh 'mvn -V -U -P delivery clean package -DskipTests org.jacoco:jacoco-maven-plugin:0.7.7.201606060606:prepare-agent sonar:sonar -fae -Dsonar.jacoco.reportPath=${WORKSPACE}/jacoco-ut.exec -Dsonar.jacoco.itReportPath=${WORKSPACE}/jacoco-it.exec'
      }
    }
    stage('Deploy') {
      steps {
        // Retour au SNAPSHOT
        // Les VM-Cli ne marchent pas sur centos 7 (pb perl ou ?). Tant que ce n'est pas résolu,
        // utilisation de la VM-IC SAG comme passerelle
        sh 'ssh jenkins@172.26.46.49 "/opt/vmshell/bin/vmoperation --vmname regard-ic --operation revert"'
        sh 'ssh jenkins@172.26.46.49 "/opt/vmshell/bin/vmoperation --vmname regard-ic --ipaddress 172.26.47.95 --operation poweron"'
        // Deploy installer to a LIVRAISON folder
        sh 'ssh -t rsins@172.26.47.95 "set -xe && mkdir -p LIVRAISON'
        sh 'scp izpack/installer/src/target/REGARDS-OSS-Installer.jar rsins@172.26.47.95:LIVRAISON'
        sh 'scp izpack/installer/src/test/resources/auto-install-ic.xml rsins@172.26.47.95:LIVRAISON'
      }
    }
    stage('Install') {
      // Installation continue sur la VM regard-ic (172.26.47.95)
      steps {
        sh 'ssh -t rsins@172.26.47.95 "cd LIVRAISON && java -jar REGARDS-OSS-Installer.jar auto-install-ic.xml"'
      }
    }
    stage('Start') {
      steps {
        sh 'ssh -tty rsadmin@172.26.47.95 "sudo /opt/regards/regards-ic/REGARDS/sbin/microservice_regards.sh start"'
      }
    }
  }
}
