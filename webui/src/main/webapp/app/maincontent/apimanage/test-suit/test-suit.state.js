(function() {
    'use strict';

    angular
        .module('webuiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('test-suit', {
            parent: 'apimanage',
            url: '/test-suit',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'webuiApp.testSuit.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/maincontent/apimanage/test-suit/test-suits.html',
                    controller: 'TestSuitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('testSuit');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('test-suit-detail', {
            parent: 'apimanage',
            url: '/test-suit/{id}',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'webuiApp.testSuit.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/maincontent/apimanage/test-suit/test-suit-detail.html',
                    controller: 'TestSuitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('testSuit');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TestSuit', function($stateParams, TestSuit) {
                    return TestSuit.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'test-suit',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('test-suit-detail.edit', {
            parent: 'test-suit-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/maincontent/apimanage/test-suit/test-suit-dialog.html',
                    controller: 'TestSuitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TestSuit', function(TestSuit) {
                            return TestSuit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('test-suit.new', {
            parent: 'test-suit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/maincontent/apimanage/test-suit/test-suit-dialog.html',
                    controller: 'TestSuitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                swaggerUri: null,
                                dependOn: null,
                                etc: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('test-suit', null, { reload: 'test-suit' });
                }, function() {
                    $state.go('test-suit');
                });
            }]
        })
        .state('test-suit.edit', {
            parent: 'test-suit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/maincontent/apimanage/test-suit/test-suit-dialog.html',
                    controller: 'TestSuitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TestSuit', function(TestSuit) {
                            return TestSuit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('test-suit', null, { reload: 'test-suit' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('test-suit.delete', {
            parent: 'test-suit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/maincontent/apimanage/test-suit/test-suit-delete-dialog.html',
                    controller: 'TestSuitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TestSuit', function(TestSuit) {
                            return TestSuit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('test-suit', null, { reload: 'test-suit' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
