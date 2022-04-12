pipelineJob('DSL_Demo') {

    def repo = 'https://github.com/gusLopezC/Jenkins_CI_DLS_Demo.git'
   
    triggers {
        scm('*/15 * * * *')
    }
    
    definition {
        cpsScm {
          scm {
            git(repo, 'main', { node -> node / 'extensions' << '' } )
            }
        }
    }
}
