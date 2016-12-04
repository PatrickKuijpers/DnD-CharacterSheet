#!groovy

node {
    def BRANCH_NAME = env.BRANCH_NAME
    echo "Using branch: ${BRANCH_NAME}"

    stage('Unittests') {
        build job: 'DnD, Unittests', parameters: [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]
    }

    if (BRANCH_NAME == 'master') {
        stage('Auto-release stage (future)') {
            echo "This app version can be safely released!"
        }
    } else if (BRANCH_NAME.contains('release/')) {
        stage('Upload HockeyApp voor alpha-test') {
            build job: 'DnD, HockeyApp, alpha', parameters: [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]
        }
    } else if (BRANCH_NAME == 'develop') { // Alleen develop branch uploaden naar HockeyApp?
        stage('Upload HockeyApp') {
            build job: 'DnD, HockeyApp, dev', parameters: [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]
        }
    }
}
