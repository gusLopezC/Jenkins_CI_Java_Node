pipelineJob('DSL_Demo') {

    def repo = 'https://github.com/Jouda-Hidri/demo.git'
   
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
