
job {
    name 'faraPersistence Docs'
    scm {
        git('git://github.com/Dace/fara.git', 'master')
    }
    triggers {
        githubPush()
    }
    steps {
        maven("clean install -pl :fitCommandDocumentor -am -DskipTests", "pom.xml")
        maven("package jd-tools:fitCommandDocumentor:install", "FaraTestapp/faraPersistence/pom.xml")
    }
    publishers {
        archiveJunit('**/target/surefire-reports/*.xml')
        archiveArtifacts('**/FitCommands/*.html')
    }
}