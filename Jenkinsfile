#!groovy

node {
    def BRANCH_NAME = env.BRANCH_NAME
    echo "Using branch: ${BRANCH_NAME}"

    stage('Unittests') {
        build job: 'DnD, Unittests', parameters: [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]
    }

    // Master branch naar HockeyApp voor alpha-testers
    if (BRANCH_NAME == 'master') {
        echo "This app version can be safely released!"
    }
    // Release branch uploaden naar HockeyApp voor alpha-testers
    else if (BRANCH_NAME.contains('release/')) {
        stage('Upload HockeyApp voor alpha-test') {
            build job: 'DnD, HockeyApp, alpha', parameters: [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]
        }
    }
    // Alleen develop branch uploaden naar HockeyApp?
    else if (BRANCH_NAME == 'develop') {
        stage('Upload HockeyApp') {
            build job: 'DnD, HockeyApp, dev', parameters: [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]
        }
    }
}
