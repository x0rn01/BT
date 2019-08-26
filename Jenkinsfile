#!/usr/bin/env groovy
// Jenkinsfile

node {

    stage('Initialize')
    {
        def dockerHome = tool 'MyDocker'
        def gradleHome  = tool 'MyGradle'
        env.PATH = "${dockerHome}/bin:${gradleHome}/bin:${env.PATH}"
    }

    stage('Checkout')
    {
        checkout scm
    }

      stage('Build')
           {
            sh 'uname -a'
            //sh 'mvn -B -DskipTests clean package'
          }

        stage('Test')
        {
            //sh 'mvn test'
            sh 'ifconfig'
        }

        stage('Deliver')
          {
                sh 'gradle version'
          }
}

/*
pipeline {
  agent {
          docker {
              image 'maven:3-alpine'
              args '-v /root/.m2:/root/.m2'
           }
 }
  stages {
    stage('build') {
      steps {
        timeout(time: 5, unit: 'MINUTES') {
          script {
            sh './gradlew clean assemble check --console=plain --warning-mode all'
          }
        }
      }
    }
    stage('test') {
        steps {
            script {
                sh 'apt-get install -y unzip git'
                sh 'git rev-parse --abbrev-ref HEAD'
            }
        }
    }
    stage('upload') {
          steps {
            script {
              sh './gradlew publishRpm'
            }
          }
    }
  }
}
*/

// Helpers
@NonCPS
def getChangeString() {
  MAX_MSG_LEN = 100
  def changeString = ""

  echo "Gathering SCM changes"
  def changeLogSets = currentBuild.changeSets
  for (int i = 0; i < changeLogSets.size(); i++) {
    def entries = changeLogSets[i].items
    for (int j = 0; j < entries.length; j++) {
      def entry = entries[j]
      truncated_msg = entry.msg.take(MAX_MSG_LEN)
      changeString += "\t[${entry.author}@${entry.commitId}]\t${truncated_msg}\n"
      def paths = entry.affectedPaths.toArray()
      for (int p = 0; p < paths.length; p++) {
        changeString += "\t\t-${paths[p]}\n"
      }
    }
  }

  if (!changeString) {
    changeString = " - No new changes"
  }
  return changeString
}
