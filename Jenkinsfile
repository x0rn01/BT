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
                $class: 'GitSCM',
                branches: scm.branches,
                extensions: scm.extensions + [[$class: 'LocalBranch'], [$class: 'WipeWorkspace']],
                userRemoteConfigs: [[credentialsId: '4a9169c0-6263-428d-9a82-7a1a43c1dc91', url: 'https://github.com/x0rn01/Kotlin.git']],
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
        sh '''
            LAST_TAG_VERSION=$(git describe --tags --match "*pre" --abbrev=0 HEAD)

            # Trim the .pre suffix to deduce the next version
            NEXT_VERSION=${LAST_TAG_VERSION%.pre}

            BUILD_NUMBER=$(git rev-list ${LAST_TAG_VERSION}...HEAD --count)

            VER=${NEXT_VERSION}.${BUILD_NUMBER}
            BRANCH=$(git rev-parse --abbrev-ref HEAD)

            ./gradlew publishRpm -PbranchParam=$BRANCH -PversionParam=$VER
        '''

        version=readFile('result').trim()
        branch = env.BRANCH_NAME
        sh "./gradlew publishRpm -PbranchParam=$branch -PversionParam=$version"
    }
}



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
