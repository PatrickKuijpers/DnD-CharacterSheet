#!groovy

node {
    def BRANCH_NAME = env.BRANCH_NAME
    echo "Using branch: ${BRANCH_NAME}"

    stage('Unittests') {
        build job: 'DnD, all - Unittests', parameters: [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]
    }

    // Is in principe onnodig als unittests al slagen!
//    stage('Builds') {
//        build job: 'DnD, all - Builds', parameters: [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]
//    }

    // Alleen develop branch uploaden naar HockeyApp?
    if (BRANCH_NAME == 'develop') {
        stage('Upload HockeyApp') {
            build job: 'DnD, all - Upload HockeyApp', parameters: [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]
        }
    }
    // Bovenstaande constructie is vooral handig als we alleen Release naar HockeyApp willen builden!
    // http://stackoverflow.com/questions/27069701/groovy-how-to-check-if-a-string-contains-any-element-of-an-array
}
