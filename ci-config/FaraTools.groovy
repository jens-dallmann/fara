job {
    name 'Tools'
    scm {
        git('git://github.com/Dace/fara.git', 'master')
    }
    triggers {
        githubPush()
    }
    steps {
        maven {
            goals "clean install"
            rootPOM "Tools/pom.xml"
            mavenInstallation "maven-3.2.3"
        }
    }
    publishers {
        archiveJunit('**/target/surefire-reports/*.xml')
        downstream('Tools_QA')
        downstream('Fara Test Editor')
    }
}

job {
    name 'Tools_QA'
    scm {
        git('git://github.com/Dace/fara.git', 'master')
    }
    triggers {
        githubPush()
    }
    steps {
        maven {
            goals "site -Pqa"
            rootPOM "Tools/pom.xml"
            mavenInstallation "maven-3.2.3"
        }
    }
    publishers {
        cobertura("**/target/site/cobertura/coverage.xml") {
            onlyStable(false)    // Include only stable builds, i.e. exclude unstable and failed ones.
            failUnhealthy(false) // Unhealthy projects will be failed.
            failUnstable(false)  // Unstable projects will be failed.
            autoUpdateHealth(false)    // Auto update threshold for health on successful build.
            autoUpdateStability(false) // Auto update threshold for stability on successful build.
            zoomCoverageChart(false)   // Zoom the coverage chart and crop area below the minimum and above the maximum coverage of the past reports.
            failNoReports(true) // Fail builds if no coverage reports have been found.
            sourceEncoding('ASCII') // Character encoding of source files
            // The following targets are added by default to check the method, line and conditional level coverage:
            methodTarget(0, 0, 0)
            lineTarget(0, 0, 0)
            conditionalTarget(0, 0, 0)
        }
        findbugs('**/findbugsXml.xml', false)
    }
}

view(type: BuildPipelineView) { // since 1.21
    name "Fara Tools"
    filterBuildQueue()
    filterExecutors()
    displayedBuilds(5)
    // common options
    description "Fara Tools"
    // BuildPipelineView options
    selectedJob "Tools"
    title "Fara Tools"
    alwaysAllowManualTrigger()
    refreshFrequency(60)
    showPipelineParameters()
}