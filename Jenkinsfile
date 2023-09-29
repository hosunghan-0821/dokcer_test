def getCommitType(commit) {
    script {
        return sh(script : "git show -s --format=%B $commit", returnStdout: true)
    }
}


node {
    def vars = checkout scm
    def prj_type = getCommitType(vars.GIT_COMMIT).substring(1,3)

    def branch = env.BRANCH_NAME
    def jenkins_home= env.JENKINS_HOME
    echo "Branch: ${branch}"


    if(prj_type=="BE" && branch=="dev"){

        stage('Clean'){
             step([$class: 'WsCleanup'])
        }
       stage('clone') {
            git branch: branch, url:'https://github.com/hosunghan-0821/dokcer_test.git'
        }
        stage('settings'){
                MY_CREDENTIALS_FILE = credentials('properties')
                sh "cp ${env.MY_CREDENTIALS_FILE} ${env.WORKSPACE}/src/main/resources"
        }

        dir("${env.WORKSPACE}") {
            stage ('Gradle Build') {



                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }

            stage ('Docker Build') {
                sh 'docker build -t test -f ./DockerFile .'
            }

            stage ('Deploy') {
                try{
                     sh 'docker stop test_cicd'
                     sh 'docker rm test_cicd'
                }
                catch(Exception){
                    echo "Docker Container 실행 중이지 않았음"
                }

                sh 'docker run -d --name test_cicd -p 8084:8084 test '
            }
            stage ('Finish'){
               sh 'docker rmi $(docker images -f "dangling=true" -q)'
            }
        }
    }

}