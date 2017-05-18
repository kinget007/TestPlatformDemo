(function() {
    'use strict';

    angular
        .module('webuiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('apidocs', {
            parent: 'apimanage',
            url: '/apidocs',
            data: {
                authorities: [],
                pageTitle: 'webuiApp.apiDoc.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/maincontent/apimanage/api-doc/api-doc.html'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', function ($translate) {
                    return $translate.refresh();
                }]
            }
        });
    }
})();
