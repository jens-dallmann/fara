job {
    name 'Fara-Core'
    scm {
        git('git://github.com/Dace/fara.git')
    }
    triggers {
        githubPush()
    }
    steps {
        maven("clean install -f Fara-Core/pom.xml", "Fara-Core/pom.xml")
    }
    publishers {
        chucknorris()
    }
}