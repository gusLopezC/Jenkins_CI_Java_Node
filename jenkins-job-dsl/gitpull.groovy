job('job-dsl-plugin') {
  scm {
    git {
      remote {
        url('https://github.com/gusLopezC/Jenkins_kube-web.git')
      }
      branch('*/main')
    }
  }
  steps {
    shell('npm install')
    shell('npm run test')
  }
}
