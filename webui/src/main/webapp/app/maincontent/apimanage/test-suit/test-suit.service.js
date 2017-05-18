(function() {
    'use strict';
    angular
        .module('webuiApp')
        .factory('TestSuit', TestSuit);

    TestSuit.$inject = ['$resource'];

    function TestSuit ($resource) {
        var resourceUrl =  'apimanageservice/' + 'api/test-suits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
