job {
    name 'Fara-Core'
    scm {
        git('git://github.com/Dace/fara.git')
    }
    triggers {
        githubPush()
    }
    steps {
        maven("clean install", "Fara-Core/backend/pom.xml")
    }
    publishers {
        chucknorris()
    }
}