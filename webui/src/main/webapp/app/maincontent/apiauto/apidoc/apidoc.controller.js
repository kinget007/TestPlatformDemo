(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('ApiDocController', ApiDocController);

    ApiDocController.$inject = ['$scope', '$state', '$http'];

    function ApiDocController ($scope, $state, $http) {
        var vm = this;

        $http({
            method: 'GET',
            url: 'apiservice/api/apiDoc/infos',
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            $scope.apiDocTitleVersions = response.data.resultData.data;
            // console.log("/apiDoc/infos \n"+angular.toJson(response.data));
            // alert($scope.apiDocTitleVersions);

            // SwaggerParser.parse($scope.apiDoc.swagger)
            //     .then(function(api) {
            //         console.log("API name: %s, Version: %s", api.info.title, api.info.version,api.tags,api.paths);
            //     });

        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });

        $http({
            method: 'GET',
            url: 'apiservice/api/apiDoc',
        }).then(function successCallback(response) {

            $scope.apiDoc = response.data;

            if (!(JSON.stringify(response.data).length == 2)) {
                SwaggerParser.dereference($scope.apiDoc, {
                    $refs: {
                        internal: true
                    }
                }, function (err, api) {
                    if (err) {
                        console.error(err);
                    }
                    else {
                        $scope.apiDoc = api;
                    }
                });
            }
            // this callback will be called asynchronously
            // when the response is available
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });

        $scope.selectApiDocByTitleAndVersion = function (title,version) {
            $http({
                method: 'GET',
                url: "apiservice/api/apiDoc2?title="+title+"&&version="+version,
            }).then(function successCallback(response) {

                $scope.apiDoc = response.data;
                // console.log("selectApiDocByTitleAndVersion \n"+angular.toJson(response.data));

                if (!(JSON.stringify(response.data).length == 2)) {

                    SwaggerParser.dereference($scope.apiDoc, {
                        $refs: {
                            internal: true
                        }
                    }, function (err, api) {
                        if (err) {
                            console.error(err);
                        }
                        else {
                            $scope.apiDoc = api;
                        }
                    });
                }
                // this callback will be called asynchronously
                // when the response is available

            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        }

    }
})();
