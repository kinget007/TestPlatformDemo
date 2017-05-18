(function() {
    'use strict';

    angular
        .module('webuiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('server-jenkins', {
            parent: 'jenkins',
            url: '/server-jenkins?page&sort&search',
            data: {
                authorities: [],
                pageTitle: 'webuiApp.serverJenkins.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/maincontent/jenkinsjob/server-jenkins/server-jenkins.html',
                    controller: 'ServerJenkinsController',
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
                    $translatePartialLoader.addPart('serverJenkins');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('server-jenkins-detail', {
            parent: 'jenkins',
            url: '/server-jenkins/{id}',
            data: {
                authorities: [],
                pageTitle: 'webuiApp.serverJenkins.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/maincontent/jenkinsjob/server-jenkins/server-jenkins-detail.html',
                    controller: 'ServerJenkinsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('serverJenkins');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ServerJenkins', function($stateParams, ServerJenkins) {
                    return ServerJenkins.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'server-jenkins',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('server-jenkins-detail.edit', {
            parent: 'server-jenkins-detail',
            url: '/detail/edit',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/maincontent/jenkinsjob/server-jenkins/server-jenkins-dialog.html',
                    controller: 'ServerJenkinsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ServerJenkins', function(ServerJenkins) {
                            return ServerJenkins.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('server-jenkins.new', {
            parent: 'server-jenkins',
            url: '/new',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/maincontent/jenkinsjob/server-jenkins/server-jenkins-dialog.html',
                    controller: 'ServerJenkinsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                urlJenkins: null,
                                loginJenkins: null,
                                pwdJenkins: null,
                                publicFlag: null,
                                userId: null,
                                userName: null,
                                status:null,
                                etc: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('server-jenkins', null, { reload: 'server-jenkins' });
                }, function() {
                    $state.go('server-jenkins');
                });
            }]
        })
        .state('server-jenkins.edit', {
            parent: 'server-jenkins',
            url: '/{id}/edit',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/maincontent/jenkinsjob/server-jenkins/server-jenkins-dialog.html',
                    controller: 'ServerJenkinsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ServerJenkins', function(ServerJenkins) {
                            return ServerJenkins.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('server-jenkins', null, { reload: 'server-jenkins' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('server-jenkins.delete', {
            parent: 'server-jenkins',
            url: '/{id}/delete',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/maincontent/jenkinsjob/server-jenkins/server-jenkins-delete-dialog.html',
                    controller: 'ServerJenkinsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ServerJenkins', function(ServerJenkins) {
                            return ServerJenkins.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('server-jenkins', null, { reload: 'server-jenkins' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
