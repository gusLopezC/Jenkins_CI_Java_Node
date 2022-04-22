job("git-pull"){
  description("This job will pull our files from github")
  scm{
    github('gusLopezC/Jenkins_kube-web', 'main')
  }
  triggers{
    githubPush()
  }
  steps{
   
  }
}
