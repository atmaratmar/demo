pipeline {
    agent any
    environment {
        DOCKER_REGISTRY = "localhost:8082" // Change port to your Nexus Docker registry port
        IMAGE_NAME = "${DOCKER_REGISTRY}/my-springboot-app"
    }
    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    // Build image with full registry path so tag is consistent
                    docker.build("${IMAGE_NAME}:latest")
                }
            }
        }
        stage('Tag and Push') {
            steps {
                script {
                    docker.withRegistry("http://${env.DOCKER_REGISTRY}", '2e0fbd30-24f3-404d-8ed4-987d26a68bd4') {
                        def appImage = docker.image("${IMAGE_NAME}:latest")
                        // Push the image to the Nexus registry
                        appImage.push()
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
