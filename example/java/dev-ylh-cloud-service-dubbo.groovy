#!/usr/bin/env groovy

timestamps {

  node() {

    stage('dev2===ylh-cloud-service-dubbo - Checkout') {
      checkout([$class: 'GitSCM', branches: [[name: '${branch_name}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '', url: 'http://12.168.3.17:8060/changelog/ylh-cloud-service-dubbo']]])
    }
    stage('dev2===ylh-cloud-service-dubbo - Build') {

// Unable to convert a build step referring to "hudson.plugins.timestamper.TimestamperBuildWrapper". Please verify and convert manually if required.
// Unable to convert a build step referring to "EnvInjectBuildWrapper". Please verify and convert manually if required.
// Unable to convert a build step referring to "org.jenkinsci.plugins.builduser.BuildUser". Please verify and convert manually if required.
// Unable to convert a build step referring to "org.jenkinsci.plugins.buildnameupdater.BuildNameUpdater". Please verify and convert manually if required.		// Maven build step
      withMaven(maven: 'maven', mavenOpts: '') {
        if (isUnix()) {
          sh "mvn -Dmaven.test.skip=true -Penable-config-server clean install -U "
        } else {
          bat "mvn -Dmaven.test.skip=true -Penable-config-server clean install -U "
        }
      }    // Shell build step
      sh """ 
groovy ${JENKINS_HOME}/groovy/ReleaseVersionChecker.groovy false 
 """
// Unable to convert a build step referring to "EnvInjectBuilder". Please verify and convert manually if required.		// Shell build step
      sh """ 
# 将dev环境的配置文件复制到安装目录下.
mv -f ${WORKSPACE}/target/${POM_ARTIFACT_NAME} ${WORKSPACE}/target/${FINAL_ARTIFACT_NAME}
cp -rf /data/upgrade_scripts ${WORKSPACE}/target/ 
 """
// Unable to convert a build step referring to "jenkins.plugins.publish__over__ssh.BapSshBuilderPlugin". Please verify and convert manually if required.
    }
  }
}