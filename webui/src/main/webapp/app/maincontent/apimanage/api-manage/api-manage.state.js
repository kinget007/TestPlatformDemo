(function() {
    'use strict';

    angular
        .module('webuiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('api-manage', {
                parent: 'apimanage',
                url: '/api-manage',
                data: {
                    authorities: [],
                    pageTitle: 'webuiApp.apiInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/maincontent/apimanage/api-manage/api-manage.html',
                        controller: 'ApiManageController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('apiInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
    }

})();
