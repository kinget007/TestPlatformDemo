(function() {
    'use strict';

    angular
        .module('webuiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('scenario-list', {
                parent: 'apiauto',
                url: '/scenario-list',
                data: {
                    authorities: [],
                    pageTitle: 'webuiApp.apiauto.apidoc.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/maincontent/apiauto/scenario/scenario-list.html',
                        controller: 'ScenarioListController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('leftbar');
                        return $translate.refresh();
                    }]
                }
            })
            .state('scenario-detail', {
                parent: 'apiauto',
                url: '/scenario-list/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'webuiApp.apiauto.scenario.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/maincontent/apiauto/scenario/scenario-detail.html',
                        controller: 'ScenarioDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        return $translate.refresh();
                    }]
                    // ,
                    // entity: ['$stateParams', 'ScenarioDetail', function($stateParams, IssueInProject) {
                    //     return IssueInProject.get({id : $stateParams.id}).$promise;
                    // }],
                    // previousState: ["$state", function ($state) {
                    //     var currentStateData = {
                    //         name: $state.current.name || 'scenario-list',
                    //         params: $state.params,
                    //         url: $state.href($state.current.name, $state.params)
                    //     };
                    //     return currentStateData;
                    // }]
                }
            })
            .state('scenario-detail.edit', {
                parent: 'scenario-detail',
                url: '/edit',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/maincontent/apiauto/scenario/scenario-detail-dialog.html',
                        controller: 'ScenarioDetailEditController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg'
                        // ,
                        // resolve: {
                        //     entity: function () {
                        //         return {
                        //             tagName: null,
                        //             scenarioName: null,
                        //             title: null,
                        //             version: null,
                        //             _id: null
                        //         };
                        //     }
                        // }
                    }).result.then(function() {
                        $state.go('scenario-detail', null, { reload: 'scenario-detail' });
                    }, function() {
                        $state.go('scenario-detail');
                    });
                }]
            })
            .state('scenario-detail.run', {
                parent: 'scenario-detail',
                url: '/run',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/maincontent/apiauto/scenario/scenario-detail-dialog-run.html',
                        controller: 'ScenarioDetailRunController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg'
                        // ,
                        // resolve: {
                        //     entity: function () {
                        //         return {
                        //             tagName: null,
                        //             scenarioName: null,
                        //             title: null,
                        //             version: null,
                        //             _id: null
                        //         };
                        //     }
                        // }
                    }).result.then(function() {
                        $state.go('scenario-detail', null, { reload: 'scenario-detail' });
                    }, function() {
                        $state.go('scenario-detail');
                    });
                }]
            })
            .state('scenario-list.new', {
                parent: 'scenario-list',
                url: '/new',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/maincontent/apiauto/scenario/scenario-list-dialog.html',
                        controller: 'ScenarioListDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    tagName: null,
                                    scenarioName: null,
                                    title: null,
                                    version: null,
                                    _id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('scenario-list', null, { reload: 'scenario-list' });
                    }, function() {
                        $state.go('scenario-list');
                    });
                }]
            });
    }

})();
