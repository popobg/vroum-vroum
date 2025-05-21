pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'Java21'
    }

    environment {
        SONARQUBE_ENV = 'SonarQube 1'
        SONAR_PROJECT_KEY = 'JAVA-fil-rouge'
        SRV_DEPLOY = '192.168.1.100'
    }

    stages {
        stage('Clonage du dépôt') {
            steps {
                git 'https://github.com/octocat/Hello-World.git'
            }
        }

        stage('Lister les fichiers') {
            steps {
                sh 'ls -la && cat README'
            }
        }

        stage('Tests unitaires') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/truc/*.xml'
                }
            }
        }

        stage('Analyse SonarQube') {
            steps {
                withSonarQubeEnv("${env.SONARQUBE_ENV}") {
                    sh "mvn sonar:sonar -Dsonar.projectKey=${env.SONAR_PROJECT_KEY}"
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
                scp target/*.war user@${env.SRV_DEPLOY}:/opt/tomcat/webapps/
                '''
            }
        }
    }

    post {
        success {
            echo "La pipeline terminée avec succès"
        }
        failure {
            echo "La pipeline a échoué"
        }
    }
}
