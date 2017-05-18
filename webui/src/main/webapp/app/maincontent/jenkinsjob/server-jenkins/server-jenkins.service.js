(function() {
    'use strict';
    angular
        .module('webuiApp')
        .factory('ServerJenkins', ServerJenkins);

    ServerJenkins.$inject = ['$resource'];

    function ServerJenkins ($resource) {
        var resourceUrl =  'jenkinsservice/' + 'api/server-jenkins/:id';

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
