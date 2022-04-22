job("git-pull"){
  description("This job will pull our files from github")
  git {
			remote {
				github("gusLopezC/Jenkins_kube-we", "https")
				credentials("29a0e27f-3839-46d4-a1a4-6e7fcead986d")
			}
			branch("main")
		}
  triggers{
    githubPush()
  }
  steps{
   
  }
}
