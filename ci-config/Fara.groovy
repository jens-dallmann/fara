job {
    name 'Fara'
    label 'Fara'
    scm {
        git('git://github.com/Dace/fara.git', 'master')
    }
    triggers {
        githubPush()
    }
    steps {
        maven("clean install", "pom.xml")
    }
    publishers {
        archiveJunit('**/target/surefire-reports/*.xml')
    }
}