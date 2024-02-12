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
  - name: maven
    image: maven:latest
    command:
    - sleep
    args:
    - 99d
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
		stage('Build') {
            steps {
                container('maven') {
                    sh 'mvn clean package'
                }
            }
        }
        stage('Build and Push Image') {
            steps {
                script {
                    def newVersion = "v1.0.0"  // 새 이미지 버전
                    container('kaniko') {
                        sh """
                        /kaniko/executor --context=${WORKSPACE} --dockerfile=${WORKSPACE}/Dockerfile --destination=docker.io/renum/moyur:${newVersion}
                        """
                    }
                }
            }
        }
    }
    post {
        success {
            echo 'Image build and push successfully....'
        }
    }
}
