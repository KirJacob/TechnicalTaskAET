#!groovy​

node {
    stage 'Checkout'
    checkout scm

    withMaven {
        wrap([$class: 'Xvfb', autoDisplayName: true, screen: '1440x900x24']) {
            def prop = "-DAPPLICATION_NAMESPACE=${env.applicationNamespace} -Dselenide.reports=target/reports -Dselenide.baseUrl=${selenideBaseUrl} -Dselenide.browser=${env.selenideBrowser} -Dvideo.folder=target/reports -Dsurefire.rerunFailingTestsCount=${env.rerunFailingTestsCount}"
            sh("mvn ${prop} -P ${env.mavenProfile}  -am -pl -Dmaven.test.failure.ignore=true clean test")
            writeFile file: '.archive-jenkins-maven-event-spy-logs', text: ''
            archive(includes: '**/target/reports/*')
        }
    }

}