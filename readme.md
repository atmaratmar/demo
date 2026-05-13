🚀 FULL CI/CD SETUP GUIDE (Jenkins + Nexus + Docker)
1. Create Docker Network

This allows Jenkins and Nexus to communicate internally (optional but recommended)

docker network create cicd2-network
2. Create Persistent Volumes
docker volume create jenkins2-home
docker volume create nexus2-data
3. Run Nexus (Second Instance)
Nexus UI → http://localhost:8989
Docker Registry → http://localhost:8985
docker run -d \
  --name nexus2 \
  --restart unless-stopped \
  -p 8989:8081 \
  -p 8985:8085 \
  -v nexus2-data:/nexus-data \
  sonatype/nexus3
Get Nexus Password
docker exec nexus2 cat /nexus-data/admin.password

Login:

http://localhost:8989

User:

admin
Create Docker Repository

Inside Nexus:

Repository type: Docker (hosted)
Name:
docker-hosted
HTTP Port:
8085
4. Run Jenkins (Second Instance)
Jenkins UI → http://localhost:8988
docker run -d \
  --name jenkins2 \
  --restart unless-stopped \
  -p 8988:8080 \
  -p 50001:50000 \
  -u root \
  -v jenkins2-home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  jenkins/jenkins:lts-jdk17
Get Jenkins Password
docker exec jenkins2 cat /var/jenkins_home/secrets/initialAdminPassword
5. Install Required Jenkins Plugins

Go to:

Manage Jenkins → Plugins

Install:

Docker Pipeline
Git
Pipeline
Credentials Binding
6. Add Nexus Credentials in Jenkins

Go to:

Manage Jenkins → Credentials → Global → Add Credentials

Fill:

Field	Value
Kind	Username with password
Username	admin
Password	Nexus password
ID	nexus-credentials
7. IMPORTANT NETWORK RULE (VERY IMPORTANT)

Because Jenkins uses host Docker daemon:

✔ Use localhost, NOT nexus2

8. FINAL WORKING JENKINSFILE
pipeline {
    agent any

    environment {
        IMAGE_NAME = "my-springboot-apptest"

        NEXUS_HOST = "localhost"
        NEXUS_PORT = "8985"
        NEXUS_REPO = "docker-hosted"

        NEXUS_URL = "${NEXUS_HOST}:${NEXUS_PORT}"

        TIMESTAMP = "${new Date().format('yyyyMMddHHmmss')}"

        IMAGE_TAG = "${NEXUS_URL}/${NEXUS_REPO}/${IMAGE_NAME}:${TIMESTAMP}.SNAPSHOT"

        IMAGE_SNAPSHOT = "target/${IMAGE_NAME}-${TIMESTAMP}.tar"
    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/atmaratmar/demo.git'
            }
        }

        stage('Build Maven') {
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
                    docker.build(IMAGE_TAG)
                }
            }
        }

        stage('Push to Nexus') {
            steps {
                script {
                    docker.withRegistry("http://${NEXUS_URL}", 'nexus-credentials') {
                        docker.image(IMAGE_TAG).push()
                    }
                }
            }
        }

        stage('Save Image') {
            steps {
                script {
                    sh "docker save -o ${IMAGE_SNAPSHOT} ${IMAGE_TAG}"
                }
            }
        }
    }

    post {
        success {
            echo "SUCCESS: Image built and pushed to Nexus"
            archiveArtifacts artifacts: 'target/*.jar, target/*.tar'
        }
    }
}
9. Access URLs
Service	URL
Jenkins	http://localhost:8988

Nexus UI	http://localhost:8989

Docker Registry	http://localhost:8985
10. Test Docker Registry Manually
docker login localhost:8985

Push test:

docker tag hello-world localhost:8985/docker-hosted/test:1
docker push localhost:8985/docker-hosted/test:1
11. Architecture Overview
GitHub → Jenkins → Maven Build → Docker Build → Nexus Registry → Stored Image
12. Persistent Storage
Service	Volume
Jenkins	jenkins2-home
Nexus	nexus2-data
🎯 DONE

You now have:

✔ Jenkins CI/CD
✔ Nexus Docker Registry
✔ Docker image build + push
✔ Persistent storage
✔ Working pipeline