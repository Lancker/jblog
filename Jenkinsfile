pipeline {
  agent any
  environment {
    //目标服务器IP以及登陆名
    TAG_SERVER = 'guohai@guohai.org'
    //目标服务器程序部署路径
    TAG_PATH = '/data/jblog.guohai.org'
    //目标服务器启动停止springboot脚本路径
    TAG_SCRIPT = '/data/spring-boot.sh'
  }

  stages {
    //构建块
    stage ('build') {
      steps {
         script{
            //获得maven程序路径
            def mvnHome = tool 'maven 3.6.0'
            //打包
            sh "${mvnHome}/bin/mvn clean package"
            echo "build over"
         }

      }
    }
    //联署块
    stage ('deploy') {
        steps {
            //计算本地文件MD5
            sh "md5sum ${WORKSPACE}/target/*.jar"
            //因为我们要使用私钥来操作远程服务器内容，下面的代码块需要使用withCredentials括起来，其中credentialsId为在Jenkins里配置的证书。keyFileVariable为代码块中可以使用的变量名
            // withCredentials([sshUserPrivateKey(credentialsId: 'guohai.org', keyFileVariable: 'guohai_org_key', passphraseVariable: '', usernameVariable: '')]) {
                //拷贝本地JAR文件到服务器上
               // sh "scp -i ${guohai_org_key} ${WORKSPACE}/target/*.jar ${TAG_SERVER}:${TAG_PATH}/${JOB_BASE_NAME}.jar"
                //计算拷贝到服务器上的文件 MD5，确保与本地一致。避免因传输产生的错误。
              //  sh "ssh -i ${guohai_org_key} ${TAG_SERVER} md5sum ${TAG_PATH}/${JOB_BASE_NAME}.jar"
                //使用脚本重启spring boot
              //  sh "ssh -i ${guohai_org_key} ${TAG_SERVER} ${TAG_SCRIPT} restart ${TAG_PATH}/${JOB_BASE_NAME}.jar"
             //  }
              sh "${TAG_SCRIPT} stop ${TAG_PATH}/${JOB_BASE_NAME}.jar"
              sh "cp ${WORKSPACE}/target/*.jar ${TAG_PATH}/${JOB_BASE_NAME}.jar"
              sh "md5sum ${TAG_PATH}/${JOB_BASE_NAME}.jar"
              sh "${TAG_SCRIPT} restart ${TAG_PATH}/${JOB_BASE_NAME}.jar --spring.config.location=/data/config/application.yml" 

        }
    }
  }
}
