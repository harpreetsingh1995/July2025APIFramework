pipeline {
    agent any
    
    tools {
        // Using the tool name Jenkins recognized in your previous log
        maven 'maven' 
    }

    stages {
        stage('Build Base Project') {
            steps {
                dir('base-app') {
                    git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                    sh "mvn -Dmaven.test.failure.ignore=true clean package"
                }
            }
            post {
                success {
                    junit 'base-app/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'base-app/target/*.jar'
                }
            }
        }
        
        stage("Deploy to Dev") {
            steps {
                echo "Deploying to Dev Environment..."
            }
        }
        
        stage('Run Sanity API Automation Test on DEV') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    dir('api-framework') {
                        git 'https://github.com/harpreetsingh1995/July2025APIFramework.git'
                        sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=dev"
                    }
                }
            }
        }
        
        stage("Deploy to QA") {
            steps {
                echo "Deploying to QA Environment..."
            }
        }
          
        stage('Run Regression API Automation Tests on QA') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    dir('api-framework') {
                        // Repository is already cloned, but we pull latest or ensure we are in the right place
                        sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml -Denv=qa"
                    }
                }
            }
        }
                        
        stage('Publish Allure Reports') {
           steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'api-framework/allure-results']]
                    ])
                }
            }
        }
        
        stage('Publish ChainTest HTML Report') {
            steps {
                 publishHTML([allowMissing: false,
                              alwaysLinkToLastBuild: false, 
                              keepAll: true, 
                              reportDir: 'api-framework/target/chaintest', 
                              reportFiles: 'Index.html', 
                              reportName: 'HTML API Regression ChainTest Report on QA', 
                              reportTitles: 'API Test HTML Report'])
            }
        }
        
        stage("Deploy to Stage") {
            steps {
                echo "Deploying to Stage Environment..."
            }
        }
        
        stage('Sanity API Automation Test on Stage') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    dir('api-framework') {
                        sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=stage"
                    }
                }
            }
        }
        
        stage('Publish sanity ChainTest Report') {
            steps {
                 publishHTML([allowMissing: false,
                              alwaysLinkToLastBuild: false, 
                              keepAll: true, 
                              reportDir: 'api-framework/target/chaintest', 
                              reportFiles: 'Index.html', 
                              reportName: 'HTML API Sanity ChainTest Report On Stage', 
                              reportTitles: ''])
            }
        }
        
        stage("Deploy to PROD") {
            steps {
                echo "Deploying to PROD Environment..."
            }
        }
        
        stage('Sanity API Automation Test on PROD') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    dir('api-framework') {
                        // Corrected the URL here as well to your 2025 repo
                        sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=prod"
                    }
                }
            }
        }
    }
}