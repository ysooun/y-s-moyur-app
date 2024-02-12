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
        stage('Copy JAR') {
		    steps {
		        script {
		            // 빌드된 JAR 파일을 작업 디렉토리로 복사합니다.
		            sh "cp target/moyur-0.0.1.jar ."
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
            echo 'Image build and push successfully..'
        }
    }
}

