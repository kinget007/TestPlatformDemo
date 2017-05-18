(function() {
    'use strict';

    angular
        .module('webuiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('jenkins', {
            abstract: true,
            parent: 'app'
        });
    }
})();
