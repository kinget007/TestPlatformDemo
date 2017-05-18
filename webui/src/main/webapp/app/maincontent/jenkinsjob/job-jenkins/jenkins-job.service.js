(function() {
    'use strict';
    angular
        .module('webuiApp')
        .factory('JenkinsJob', JenkinsJob);

    JenkinsJob.$inject = ['$resource'];

    function JenkinsJob ($resource) {
        var resourceUrl =  'jenkinsservice/' + 'api/jenkins-job/:id';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }
})();

