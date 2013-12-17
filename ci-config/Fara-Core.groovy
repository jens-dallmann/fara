job {
    name 'Fara-Core'
    scm {
        git('git://github.com/Dace/fara.git', 'master')
    }
    triggers {
        githubPush()
    }
    steps {
        maven("clean install -f Fara-Core/pom.xml", "Fara-Core/pom.xml")
    }
    publishers {
        archiveJunit('**/target/surefire-reports/*.xml')
    }
}

job {
    name 'Fara-Core cobertura'
    scm {
        git('git://github.com/Dace/fara.git', 'master')
    }
    triggers {
        githubPush()
    }
    steps {
        maven("site -Pqa", "Fara-Core/pom.xml")
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
        chucknorris()
        findbugs('**/findbugsXml.xml', false)
    }
}