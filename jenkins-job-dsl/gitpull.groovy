job('Portafolio_backend_test') {
    description('This job will pull our files from github')
    scm {
      git('https://github.com/gusLopezC/Jenkins_kube-web.git', '*/main')
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

    job("launch-web") {
      description("This job will deploy our website on top of K8s")
      triggers {
        upstream('git-pull', 'SUCCESS')
      }
      steps {
        shell('''
          ssh ec2 - user @172 .31 .74 .187 '
          html_files = $(ls / home / ec2 - user / master | grep html | wc - l) php_files = $(ls / home / ec2 - user / master | grep php | wc - l)

          if kubectl get deploy | grep myweb then kubectl delete deploy myweb
          else
            echo "No myweb deployment found, you can proceed to next step"
          fi

          if [
            [$html_files > 0 && $php_files > 0]
          ] then sudo docker build--build - arg IMAGE = php: 7.2 - apache--build - arg FILES = * --build - arg DIR = /var/www / html - t dheeth / kube - web / home / ec2 - user / master elif[[$html_files > 0]] then sudo docker build--build - arg IMAGE = httpd--build - arg FILES = * .html--build - arg DIR = /usr/local / apache2 / htdocs - t dheeth / kube - web / home / ec2 - user / master elif[[$php_files > 0]] then sudo docker build--build - arg IMAGE = php: 7.2 - apache--build - arg FILES = * .php--build - arg DIR = /var/www / html - t dheeth / kube - web / home / ec2 - user / master fi

          sudo docker push dheeth / kube - web

          kubectl create - f / home / ec2 - user / master / pvc.yml kubectl create - f / home / ec2 - user / master / deploy.yml sleep 10 kubectl expose deploy myweb--type = LoadBalancer--port = 80 sleep 10 kubectl get svc '
          ''')
        }
      }
    }
