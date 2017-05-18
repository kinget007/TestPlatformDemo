(function () {
    'use strict';

    angular
        .module('webuiApp')
        .controller('ScenarioListController', ScenarioListController);

    ScenarioListController.$inject = ['$scope', '$state', '$http'];

    function ScenarioListController($scope, $state, $http) {
        var vm = this;

        $http({
            method: 'GET',
            url: 'auth/api/account',
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            $scope.userInfo = response.data;
            $http({
                method: 'GET',
                url: 'apiservice/api/simplescenarios?emailUser=' + $scope.userInfo.email,
            }).then(function successCallback(response) {
                console.log(response.data);
                $scope.simpleScenarios = response.data.resultData.data;
                // this callback will be called asynchronously
                // when the response is available
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });
    }
})();
