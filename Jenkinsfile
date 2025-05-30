pipeline {
    agent any

    environment {
        IMAGE_NAME = "my-springboot-app"
        NEXUS_URL = "localhost:6555/docker-hosted"
        IMAGE_TAG = "${NEXUS_URL}/${IMAGE_NAME}:latest"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/atmaratmar/demo.git'
            }
        }

        stage('Build with Maven (in Docker)') {
            steps {
                script {
                    docker.image('maven:3.8.5-openjdk-17').inside {
                        sh 'mvn clean package -DskipTests'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image locally tagged as "my-springboot-app:latest"
                    docker.build("${IMAGE_NAME}:latest")
                }
            }
        }

        stage('Tag and Push to Nexus') {
            steps {
                script {
                    docker.withRegistry("http://${NEXUS_URL}", 'nexus-credentials-id') {
                        def localImage = docker.image("${IMAGE_NAME}:latest")
                        def nexusImage = docker.image("${IMAGE_TAG}")

                        // Tag the local image with the Nexus repository URL
                        localImage.tag("${IMAGE_TAG}")

                        // Push the image to Nexus Docker registry
                        nexusImage.push()
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Docker image '${IMAGE_TAG}' built and pushed to Nexus."
        }
    }
}











// pipeline {
//     agent any
//
//     environment {
//         IMAGE_NAME = "my-springboot-app"
//         NEXUS_URL = "localhost:6555/docker-hosted"
//         IMAGE_TAG = "${NEXUS_URL}/${IMAGE_NAME}:latest"
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
//
//         stage('Tag and Push to Nexus') {
//             steps {
//                 script {
//                     docker.withRegistry("http://${NEXUS_URL}", 'nexus-credentials-id') {
//                         def app = docker.image("${IMAGE_NAME}")
//                         app.tag("latest")
//                         app.push("latest")
//                     }
//                 }
//             }
//         }
//     }
//
//     post {
//         success {
//             echo "Docker image '${IMAGE_TAG}' built and pushed to Nexus."
//         }
//     }
// }











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
