pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'Java21'
    }

    environment {
        SONARQUBE_ENV = 'SonarQube 1'
        SONAR_PROJECT_KEY = 'vroum_vroum'
        SRV_DEPLOY = '172.29.86.140'
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

        stage('Validation manuelle pour déploiement') {
            steps {
                input message: 'Souhaitez-vous déployer en staging ?'
            }
        }

        stage('deploiement') {
            steps {
                sh '''
                echo "Déploiement réussi vers $env.SRV_DEPLOY"
                scp back/vroum_vroum/target/*.jar user@${env.SRV_DEPLOY}:/opt/tomcat/webapps/
                '''
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
