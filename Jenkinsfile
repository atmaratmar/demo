pipeline {
    agent any
    environment {
        DOCKER_REGISTRY = "localhost:5000"
        IMAGE_NAME = "${DOCKER_REGISTRY}/my-springboot-app"
    }
    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("my-springboot-app:latest")
                }
            }
        }
        stage('Tag and Push') {
            steps {
                script {
                    docker.withRegistry("http://${env.DOCKER_REGISTRY}", 'nexus-creds') {
                        def appImage = docker.image("my-springboot-app:latest")
                        appImage.tag("latest")
                        appImage.push("latest")
                    }
                }
            }
        }
    }
}







// pipeline {
//     agent any
//
//     environment {
//         IMAGE_NAME = "my-springboot-app"
//     }
//
//     stages {
//         stage('Checkout') {
//             steps {
//                 git 'https://github.com/atmaratmar/demo.git'
//             }
//         }
//
//         stage('Build with Maven (in Docker)') {
//             steps {
//                 script {
//                     docker.image('maven:3.8.5-openjdk-17').inside {
//                         sh 'mvn clean package -DskipTests'
//                     }
//                 }
//             }
//         }
//
//         stage('Build Docker Image') {
//             steps {
//                 script {
//                     docker.build("${IMAGE_NAME}")
//                 }
//             }
//         }
//     }
//
//     post {
//         success {
//             echo "Docker image '${IMAGE_NAME}' built and ready."
//         }
//     }
// }
