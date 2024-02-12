pipeline {
    agent {
        kubernetes {
            defaultContainer 'kaniko'
            yaml '''
kind: Pod
spec:
  containers:
  - name: kaniko
    image: gcr.io/kaniko-project/executor:debug
    imagePullPolicy: Always
    command:
    - sleep
    args:
    - 99d
    volumeMounts:
      - name: kaniko-secret
        mountPath: /kaniko/.docker
  volumes:
    - name: kaniko-secret
      secret:
        secretName: regcred
        items:
          - key: .dockerconfigjson
            path: config.json
'''
        }
    }
    stages {
        stage('Checkout') {
            steps {
                script {
                    git branch: 'master',
                        credentialsId: 'github-credentials',
                        url: 'https://github.com/ysooun/y-s-moyur-app/tree/master/moyur'
                }
            }
        }
        stage('Build JAR') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Build and Push Image') {
            steps {
                container('kaniko') {
                    sh '''
                    /kaniko/executor --context . --dockerfile=Dockerfile --destination=renum/test:v1.0.0
                    '''
                }
            }
        }
    }
    post {
        success {
            echo 'Image build and push successfully.'
        }
    }
}
