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
        maven("clean install -pl :fitCommandDocumentor -am -DskipTests", "pom.xml")
        maven("package jd-tools:fitCommandDocumentor:install", "FaraTestapp/faraPersistence/pom.xml")
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
        maven("clean install -pl :fitCommandDocumentor -am -DskipTests", "pom.xml")
        maven("package jd-tools:fitCommandDocumentor:install", "FaraTestapp/festFaraAdapter/pom.xml")
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