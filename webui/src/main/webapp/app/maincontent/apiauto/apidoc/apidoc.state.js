(function () {
    'use strict';

    angular
        .module('webuiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('apidoc', {
                parent: 'apiauto',
                url: '/apidoc',
                data: {
                    authorities: [],
                    pageTitle: 'webuiApp.apiauto.apidoc.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/maincontent/apiauto/apidoc/apidoc.html',
                        controller: 'ApiDocController',
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
            .state('apidoc-dialog', {
                parent: 'apidoc',
                url: '/apidoc-dialog',
                data: {
                    authorities: [],
                    pageTitle: 'webuiApp.apiauto.apidoc.home.title'
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/maincontent/apiauto/apidoc/apidoc-dialog.html',
                        controller: 'ApiDocDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg'
                        // ,
                        // resolve: {
                        //     entity: ['ApiInfo', function(ApiInfo) {
                        //         return ApiInfo.get({id : $stateParams.id}).$promise;
                        //     }]
                        // }
                    }).result.then(function() {
                        $state.go('^', {}, { reload: false });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })

        ;
    }

})();
