#!groovy

node {
    echo "Using branch: ${env.BRANCH_NAME}"

    build job: 'DnD, all - Unittests', parameters: [string(name: 'BRANCH_NAME', value: env.BRANCH_NAME)]

    build job: 'DnD, all - Builds HockeyApp', parameters: [string(name: 'BRANCH_NAME', value: BRANCH_NAME)]
}
