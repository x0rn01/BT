#!/usr/bin/env groovy
pipeline {
  agent { node { label 'lab' } }
  stages {
    stage('build') {
      steps {
        timeout(time: 5, unit: 'MINUTES') {
          script {
            sh './gradlew clean assemble check detekt --console=plain --warning-mode all'
          }
        }
      }
    }
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
