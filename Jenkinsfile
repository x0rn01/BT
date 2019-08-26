#!/usr/bin/env groovy
// Jenkinsfile

node {

    stage('Initialize')
    {
        def dockerHome = tool 'MyDocker'
        def gradleHome  = tool 'MyGradle'
        env.PATH = "${dockerHome}/bin:${gradleHome}/bin:${env.PATH}"
    }

    stage('Checkout') {
            checkout([
                //$class: 'GitSCM',
                branches: [[name: '9.4']],
                //extensions: scm.extensions + [[$class: 'LocalBranch'], [$class: 'WipeWorkspace']],
                userRemoteConfigs: [[credentialsId: '36deca3b-4b18-494f-812a-a46cb631eccd', url: 'git@10.221.29.101:gecko']],
                doGenerateSubmoduleConfigurations: false
            ])
        }

    stage('test')
              {
                    sh 'git name-rev --name-only HEAD'
                    echo env.BRANCH_NAME
              }

     /* stage('Build')
           {
            sh './gradlew clean assemble check --console=plain --warning-mode all'
          }
*/



    stage('Upload') {

        def branch = env.BRANCH_NAME

        sh '''


            LAST_TAG_VERSION=$(git describe --tags --match "*pre" --abbrev=0 HEAD)

            # Trim the .pre suffix to deduce the next version
            NEXT_VERSION=${LAST_TAG_VERSION%.pre}

            BUILD_NUMBER=$(git rev-list ${LAST_TAG_VERSION}...HEAD --count)

            echo "${NEXT_VERSION}.${BUILD_NUMBER}"
        '''

        sh "./gradlew publishRpm -PbranchParam=$branch"
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
