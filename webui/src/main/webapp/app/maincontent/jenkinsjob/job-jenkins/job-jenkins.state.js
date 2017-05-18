(function() {
    'use strict';

    angular
        .module('webuiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('job-jenkins', {
            parent: 'jenkins',
            url: '/job-jenkins?page&sort&search',
            data: {
                authorities: [],
                pageTitle: 'webuiApp.jobJenkins.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/maincontent/jenkinsjob/job-jenkins/job-jenkins.html',
                    controller: 'JobJenkinsController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('jobJenkins');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('job-jenkins-detail', {
            parent: 'jenkins',
            url: '/job-jenkins/{id}',
            data: {
                authorities: [],
                pageTitle: 'webuiApp.jobJenkins.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/maincontent/jenkinsjob/job-jenkins/job-jenkins-detail.html',
                    controller: 'JobJenkinsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('jobJenkins');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'JobJenkins', function($stateParams, JobJenkins) {
                    return JobJenkins.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'job-jenkins',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('job-jenkins-detail.edit', {
            parent: 'job-jenkins-detail',
            url: '/detail/edit',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/maincontent/jenkinsjob/job-jenkins/job-jenkins-dialog.html',
                    controller: 'JobJenkinsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JobJenkins', function(JobJenkins) {
                            return JobJenkins.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job-jenkins.new', {
            parent: 'job-jenkins',
            url: '/new',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/maincontent/jenkinsjob/job-jenkins/job-jenkins-dialog.html',
                    controller: 'JobJenkinsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                urlJenkins: null,
                                serverIdJenkins: null,
                                folderJenkins: null,
                                etc: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('job-jenkins', null, { reload: 'job-jenkins' });
                }, function() {
                    $state.go('job-jenkins');
                });
            }]
        })
        .state('job-jenkins.edit', {
            parent: 'job-jenkins',
            url: '/{id}/edit',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/maincontent/jenkinsjob/job-jenkins/job-jenkins-dialog.html',
                    controller: 'JobJenkinsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JobJenkins', function(JobJenkins) {
                            return JobJenkins.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('job-jenkins', null, { reload: 'job-jenkins' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job-jenkins.delete', {
            parent: 'job-jenkins',
            url: '/{id}/delete',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/maincontent/jenkinsjob/job-jenkins/job-jenkins-delete-dialog.html',
                    controller: 'JobJenkinsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['JobJenkins', function(JobJenkins) {
                            return JobJenkins.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('job-jenkins', null, { reload: 'job-jenkins' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
