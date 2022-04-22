job('Portafolio_backend_test'){
    description('This job run a test in Postman with Newman')
    scm {
        git('https://github.com/manfer993/test-postman-newman.git','*/master')
    }
    wrappers {
        nodejs('nodeJS_10.15.3')
    }
    steps {
        shell('npm install')
        shell('npm run test')
    }
}
