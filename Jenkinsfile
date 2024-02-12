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
        stage('Debug') {
            steps {
                script {
                    echo 'Searching for moyur-0.0.1.jar...'
                    def jarFilePath = sh(script: 'find ${WORKSPACE} -name moyur-0.0.1.jar', returnStdout: true).trim()
                    if (jarFilePath) {
                        echo "Found moyur-0.0.1.jar at: ${jarFilePath}"
                        // 이제 jarFilePath를 사용하여 파일을 복사하거나 다른 작업을 수행할 수 있습니다.
                    } else {
                        error 'moyur-0.0.1.jar not found!'
                    }
                }
            }
        }
        stage('Copy JAR') {
            steps {
                script {
                    sh "cp ${jarFilePath} ."
                }
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
            echo 'Image build and push successfully...'
        }
    }
}