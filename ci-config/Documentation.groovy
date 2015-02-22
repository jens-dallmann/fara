job {
    name 'documentation_build_trigger'
    publishers {
        downstream('fara_persistence_docs')
        downstream('fara_fest_docs')
    }
}
job {
    name 'fara_persistence_docs'
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
            goals "process-classes"
            rootPOM "FaraTestapp/faraPersistence/pom.xml"
            mavenInstallation "maven-3.2.3"
        }
    }
    publishers {
        archiveArtifacts('**/FitCommands/*.html')
    }
}

job {
    name 'fara_fest_docs'
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
            goals "process-classes"
            rootPOM "FaraTestapp/festFaraAdapter/pom.xml"
            mavenInstallation "maven-3.2.3"
        }
    }
    publishers {
        archiveArtifacts('**/FitCommands/*.html')
    }
}

view(type: BuildPipelineView) { // since 1.21
    name "Fara-Documentation"
    filterBuildQueue()
    filterExecutors()
    displayedBuilds(5)
    // common options
    description "Generate Documentation"
    /*filterBuildQueue(boolean filterBuildQueue)
    filterExecutors(boolean filterExecutors)
*/
    // BuildPipelineView options
    selectedJob "documentation_build_trigger"
    title "Fara-Documentation"
    alwaysAllowManualTrigger()
    refreshFrequency(60)
    showPipelineParameters()
}