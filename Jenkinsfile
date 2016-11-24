#!groovy

node {
    def BRANCH_NAME = env.BRANCH_NAME
    echo "Using branch: ${BRANCH_NAME}"

    stage('Unittests') {
        build job: 'DnD, all - Unittests', parameters: [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]
    }

    stage('Builds') {
        build job: 'DnD, all - Builds', parameters: [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]
    }

    if (BRANCH_NAME == 'develop') {
        stage('Upload HockeyApp') {
            build job: 'DnD, all - Upload HockeyApp', parameters: [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]
        }
    }
}
