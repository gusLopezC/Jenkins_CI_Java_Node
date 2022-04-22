pipelineJob('job-dsl-plugin') {
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('https://github.com/gusLopezC/Jenkins_kube-web.git')
          }
          branch('*/main')
        }
      }
      triggers{
        githubPush()
      }
      lightweight()
    }
  }
}
