(function() {
    'use strict';
    angular
        .module('webuiApp')
        .factory('JobJenkins', JobJenkins);

    JobJenkins.$inject = ['$resource'];

    function JobJenkins ($resource) {
        var resourceUrl =  'jenkinsservice/' + 'api/job-jenkins/:id';

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
