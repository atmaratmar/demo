pipeline {
    agent any

    environment {
        IMAGE_NAME = "my-springboot-app"
        NEXUS_PORT = "8085"
        NEXUS_REPO = "docker-hosted"
        NEXUS_URL = "localhost:${NEXUS_PORT}"
        TIMESTAMP = "${new Date().format('yyyyMMddHHmmss')}"
        IMAGE_TAG = "${NEXUS_URL}/${NEXUS_REPO}/${IMAGE_NAME}:${TIMESTAMP}"
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
                    echo "Building image: ${IMAGE_TAG}"
                    docker.build("${IMAGE_TAG}")
                }
            }
        }

        stage('Tag and Push to Nexus') {
            steps {
                script {
                    docker.withRegistry("http://${NEXUS_URL}", 'admin') {
                        docker.image("${IMAGE_TAG}").push()
                    }
                }
            }
        }
        stage('Run Batch Job') {
            steps {
                script {
                    def jarFile = sh(script: "ls target/*.jar", returnStdout: true).trim()
                    sh "java -jar ${jarFile} --spring.main.web-application-type=none"
                }
            }
        }

    }


    post {
        success {
            echo "Docker image '${IMAGE_TAG}' built and pushed to Nexus."
             archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }

    }
}









// pipeline {
//     agent any
//
//     environment {
//         IMAGE_NAME = "my-springboot-app"
//         NEXUS_PORT = "8085"
//         NEXUS_REPO = "docker-hosted" // the repo name shown in Nexus
//         NEXUS_URL = "localhost:${NEXUS_PORT}"
//         IMAGE_TAG = "${NEXUS_URL}/${NEXUS_REPO}/${IMAGE_NAME}:latest"
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
//                     // Build the Docker image locally tagged as "my-springboot-app:latest"
//                     //docker.build("${IMAGE_NAME}:latest")
//                      docker.build("${NEXUS_URL}/${NEXUS_REPO}/${IMAGE_NAME}:latest")
//                 }
//             }
//         }
//
//         stage('Tag and Push to Nexus') {
//             steps {
//                 script {
//                     docker.withRegistry("http://${NEXUS_URL}", 'admin') {
//                         docker.image("${NEXUS_URL}/${NEXUS_REPO}/${IMAGE_NAME}:latest").push()
//                     //docker.withRegistry("http://${NEXUS_URL}", 'admin') {
//                         //def localImage = docker.image("${IMAGE_NAME}:latest")
//
//                         // Tag image properly
//                         //localImage.tag("${NEXUS_REPO}/${IMAGE_NAME}", 'latest', true)
//
//                         // Don't re-declare nexusImage if already declared
//                         //nexusImage = docker.image("${NEXUS_URL}/${NEXUS_REPO}/${IMAGE_NAME}:latest")
//                         //nexusImage.push()
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
