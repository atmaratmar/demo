pipeline {
    agent any

    environment {
        IMAGE_NAME = "my-springboot-apptest"

        // Name of the second Nexus container
        NEXUS_HOST = "nexus2"

        // Internal Docker registry port configured in Nexus
        NEXUS_PORT = "8085"

        // Docker hosted repository name in Nexus
        NEXUS_REPO = "docker-hosted"

        // Jenkins reaches Nexus through the Docker network
        NEXUS_URL = "${NEXUS_HOST}:${NEXUS_PORT}"

        // Timestamp for unique image tags
        TIMESTAMP = "${new Date().format('yyyyMMddHHmmss')}"

        // Full Docker image tag
        IMAGE_TAG = "${NEXUS_URL}/${NEXUS_REPO}/${IMAGE_NAME}:${TIMESTAMP}.SNAPSHOT"

        // File used to save the image as a tar archive
        IMAGE_SNAPSHOT = "target/${IMAGE_NAME}-${TIMESTAMP}.SNAPSHOT.tar"
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
                    echo "Building Docker image: ${IMAGE_TAG}"
                    docker.build(IMAGE_TAG)
                }
            }
        }

        stage('Push Docker Image to Nexus') {
            steps {
                script {
                    // 'nexus-credentials' is the Jenkins credential ID
                    docker.withRegistry("http://${NEXUS_URL}", 'nexus-credentials') {
                        docker.image(IMAGE_TAG).push()
                    }
                }
            }
        }

        stage('Save Docker Image as TAR') {
            steps {
                script {
                    sh "docker save -o ${IMAGE_SNAPSHOT} ${IMAGE_TAG}"
                }
            }
        }

        stage('Run Batch Job') {
            steps {
                script {
                    def jarFile = sh(
                        script: "ls target/*.jar | head -1",
                        returnStdout: true
                    ).trim()

                    sh "java -jar ${jarFile} --spring.main.web-application-type=none"
                }
            }
        }
    }

    post {
        success {
            echo "Docker image '${IMAGE_TAG}' built, pushed to Nexus, and saved as TAR."

            archiveArtifacts artifacts: 'target/*.jar, target/*.tar',
                             fingerprint: true
        }
    }
}




// pipeline {
//     agent any
//
//     environment {
//         IMAGE_NAME = "my-springboot-apptest"
//         NEXUS_PORT = "8085"
//         NEXUS_REPO = "docker-hosted"
//         NEXUS_URL = "localhost:${NEXUS_PORT}"
//         TIMESTAMP = "${new Date().format('yyyyMMddHHmmss')}"
//         IMAGE_TAG = "${NEXUS_URL}/${NEXUS_REPO}/${IMAGE_NAME}:${TIMESTAMP}.SNAPSHOT"
//         IMAGE_SNAPSHOT = "target/${IMAGE_NAME}-${TIMESTAMP}.SNAPSHOT"
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
//                     echo "Building image: ${IMAGE_TAG}"
//                     docker.build("${IMAGE_TAG}")
//                 }
//             }
//         }
//
//         stage('Tag and Push to Nexus') {
//             steps {
//                 script {
//                     docker.withRegistry("http://${NEXUS_URL}", 'admin') {
//                         docker.image("${IMAGE_TAG}").push()
//                     }
//                 }
//             }
//         }
//
//         stage('Save Docker Image as SNAPSHOT') {
//             steps {
//                 script {
//                     sh "docker save -o ${IMAGE_SNAPSHOT} ${IMAGE_TAG}"
//                 }
//             }
//         }
//
//         stage('Run Batch Job') {
//             steps {
//                 script {
//                     def jarFile = sh(script: "ls target/*.jar", returnStdout: true).trim()
//                     sh "java -jar ${jarFile} --spring.main.web-application-type=none"
//                 }
//             }
//         }
//     }
//
//     post {
//         success {
//             echo "Docker image '${IMAGE_TAG}' built, pushed to Nexus, and saved as tar."
//             archiveArtifacts artifacts: 'target/*.jar, target/*.SNAPSHOT', fingerprint: true
//         }
//     }
// }





// pipeline {
//     agent any
//
//     environment {
//         IMAGE_NAME = "my-springboot-app"
//         NEXUS_PORT = "8085"
//         NEXUS_REPO = "docker-hosted"
//         NEXUS_URL = "localhost:${NEXUS_PORT}"
//         TIMESTAMP = "${new Date().format('yyyyMMddHHmmss')}"
//         IMAGE_TAG = "${NEXUS_URL}/${NEXUS_REPO}/${IMAGE_NAME}:${TIMESTAMP}"
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
//                     echo "Building image: ${IMAGE_TAG}"
//                     docker.build("${IMAGE_TAG}")
//                 }
//             }
//         }
//
//         stage('Tag and Push to Nexus') {
//             steps {
//                 script {
//                     docker.withRegistry("http://${NEXUS_URL}", 'admin') {
//                         docker.image("${IMAGE_TAG}").push()
//                     }
//                 }
//             }
//         }
//         stage('Run Batch Job') {
//             steps {
//                 script {
//                     def jarFile = sh(script: "ls target/*.jar", returnStdout: true).trim()
//                     sh "java -jar ${jarFile} --spring.main.web-application-type=none"
//                 }
//             }
//         }
//
//     }
//
//
//     post {
//         success {
//             echo "Docker image '${IMAGE_TAG}' built and pushed to Nexus."
//              archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
//         }
//
//     }
// }









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
