pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'Java21'
    }

    environment {
        SONARQUBE_ENV = 'SonarQube 1'
        SONAR_PROJECT_KEY = 'vroum_vroum'
        DEPLOY_PATH = "deploy_dir/"
    }

    stages {
        stage('Tests unitaires') {
            steps {
                echo "======================> mvn test"
                sh 'mvn -B -f back/vroum_vroum/pom.xml -Dmaven.repo.local=.m2 test'
            }
            post {
                always {
                    junit 'back/vroum_vroum/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Analyse SonarQube') {
            steps {
                withSonarQubeEnv("${env.SONARQUBE_ENV}") {
                    sh "mvn -f back/vroum_vroum/pom.xml sonar:sonar -Dmaven.repo.local=.m2 -Duser.home=~ -Dsonar.projectKey=${env.SONAR_PROJECT_KEY}"
                }
            }
        }

        stage('deploiement') {
            when {
                branch 'main'
            }
            steps {
                // packaging
                sh "mvn -f back/vroum_vroum/pom.xml -Dmaven.repo.local=.m2 package"
                // déploiement
                sh "rm -rf ${env.DEPLOY_PATH}"
                sh "mkdir ${env.DEPLOY_PATH}"
                sh "cp back/vroum_vroum/target/*.jar ${env.DEPLOY_PATH}"
                sh "echo Déploiement réussi vers ${env.DEPLOY_PATH}"
            }
        }
    }

    post {
        success {
            echo "Pipeline terminée avec succès"
        }
        failure {
            echo "La pipeline a échoué"
        }
    }
}
