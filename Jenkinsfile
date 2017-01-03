#!groovy

node {
    def AUTODEPLOY_TURNED_ON = false

    def BRANCH_NAME = env.BRANCH_NAME
    echo "Using branch: ${BRANCH_NAME}"

    def PARAMS = [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]

    stage('Unittests') {
        build job: 'DnD, Unittests', parameters: PARAMS
    }

    if (BRANCH_NAME == 'master') {
        stage('Auto-deploy (future)') {
            if (AUTODEPLOY_TURNED_ON) {
                build job: 'DnD, Google Play Store, release', parameters: PARAMS
            } else {
                echo "The app can be released now!"
                echo "However, auto-deploy is not turned on..."
            }
        }
    } else if (BRANCH_NAME.contains('release/') || BRANCH_NAME.contains('hotfix/')) {
        stage('Upload HockeyApp (alpha-test)') {
            build job: 'DnD, HockeyApp, alpha', parameters: PARAMS
        }
    } else { // Develop & alle feature branches
        stage('Upload HockeyApp') {
            build job: 'DnD, HockeyApp, dev', parameters: PARAMS
        }
    }
}
