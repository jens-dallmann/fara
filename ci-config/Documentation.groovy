job {
    name 'documentation build trigger'
    publishers {
        downstream('fara persistence docs')
        downstream('fara fest docs')
    }
}
job {
    name 'fara persistence docs'
    scm {
        git('git://github.com/Dace/fara.git', 'master')
    }
    triggers {
        githubPush()
    }
    steps {
        maven {
            goals "clean install -pl :fitCommandDocumentor -am -DskipTests"
            rootPOM "pom.xml"
            mavenInstallation "maven-3.2.3"
        }
        maven {
            goals "package jd-tools:fitCommandDocumentor:install"
            rootPOM "FaraTestapp/faraPersistence/pom.xml"
            mavenInstallation "maven-3.2.3"
        }
    }
    publishers {
        archiveArtifacts('**/FitCommands/*.html')
    }
}

job {
    name 'fara fest docs'
    scm {
        git('git://github.com/Dace/fara.git', 'master')
    }
    triggers {
        githubPush()
    }
    steps {
        maven {
            goals "clean install -pl :fitCommandDocumentor -am -DskipTests"
            rootPOM "pom.xml"
            mavenInstallation "maven-3.2.3"
        }
        maven {
            goals "package jd-tools:fitCommandDocumentor:install"
            rootPOM "FaraTestapp/festFaraAdapter/pom.xml"
            mavenInstallation "maven-3.2.3"
        }
    }
    publishers {
        archiveArtifacts('**/FitCommands/*.html')
    }
}

view(type: BuildPipelineView) { // since 1.21
    name "fara documentation"
    filterBuildQueue()
    filterExecutors()
    displayedBuilds(5)
    // common options
    description "Generate Documentation"
    /*filterBuildQueue(boolean filterBuildQueue)
    filterExecutors(boolean filterExecutors)
*/
    // BuildPipelineView options
    selectedJob "documentation build trigger"
    title "fara documentation"
    alwaysAllowManualTrigger()
    refreshFrequency(60)
    showPipelineParameters()
}