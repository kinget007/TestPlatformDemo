(function() {
    'use strict';

    angular
        .module('webuiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('api-postman', {
                parent: 'apimanage',
                url: '/api-postman',
                data: {
                    authorities: [],
                    pageTitle: 'webuiApp.apiInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/maincontent/apimanage/api-postman/api-postman.html',
                        controller: 'ApiPostmanController',
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
