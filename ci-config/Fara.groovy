job {
    name 'Fara'
    scm {
        git('git://github.com/Dace/fara.git', 'master')
    }
    triggers {
        githubPush()
    }
    steps {
        maven {
            goals "clean install"
            rootPOM "pom.xml"
            mavenInstallation "maven-3.2.3"
        }
    }
    publishers {
        archiveJunit('**/target/surefire-reports/*.xml')
        downstream('Fara_QA')
    }
}

job {
    name 'Fara_QA'
    scm {
        git('git://github.com/Dace/fara.git', 'master')
    }
    triggers {
        githubPush()
    }
    steps {
        maven {
            goals "site -Pqa"
            rootPOM "pom.xml"
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
    name "Fara-Complete-Workspace"
    filterBuildQueue()
    filterExecutors()
    displayedBuilds(5)
    // common options
    description "Fara-Complete-Workspace"
    // BuildPipelineView options
    selectedJob "Fara"
    title "Fara-Complete-Workspace"
    alwaysAllowManualTrigger()
    refreshFrequency(60)
    showPipelineParameters()
}