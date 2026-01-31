pipeline {
  agent any

  parameters {
    string(name: 'ENV', defaultValue: 'qa', description: 'Environment to run tests against')
    choice(name: 'SUITE', choices: ['smoke','regression','full'], description: 'Which child suite to run')
    string(name: 'GROUPS', defaultValue: 'smoke', description: 'TestNG groups to run (smoke/regression)')
  }

  environment {
    MVN_CMD = 'mvn -B'
    PARENT_SUITE = 'src/test/resources/testng-parent.xml'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Test') {
      steps {
        script {
          def child = "src/test/resources/testng-${params.SUITE}.xml"
          echo "Running parent suite: ${env.PARENT_SUITE} with child: ${child} on env=${params.ENV} groups=${params.GROUPS}"
          sh "${env.MVN_CMD} clean test -Denv=${params.ENV} -DsuiteXmlFile=${env.PARENT_SUITE} -Dchild.suite.file=${child} -Dgroups=${params.GROUPS}"
        }
      }
    }

    stage('Allure Report') {
      steps {
        sh "${env.MVN_CMD} site"
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'target/allure-report/**', allowEmptyArchive: true
      junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
      sh 'ls -la target || true'
    }
    success {
      echo 'Build and tests succeeded.'
    }
    failure {
      echo 'Build or tests failed.'
    }
  }
}
