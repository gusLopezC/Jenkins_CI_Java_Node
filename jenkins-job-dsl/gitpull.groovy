job('Portafolio_backend_test'){
    description('This job will pull our files from github')
    scm {
        git('https://github.com/gusLopezC/Jenkins_kube-web.git','*/main')
    }
    wrappers {
        nodejs('nodeJS_10.15.3')
    }
    steps {
        shell('npm install')
        shell('npm run test')
       shell('''
        ssh ec2 - user @172 .31 .74 .187 '
        path = "/home/ec2-user/jenkins_vol/workspace/git-pull/"
        files = $(ls $path | grep - e php - e html | wc - l) if [
          [$files > 0]
        ] then cp $path * /home/ec2 - user / master
        else
          echo "No php or html files received"
        exit 1 fi '
        ''')
    }
}
