(function () {
    'use strict';

    angular
        .module('webuiApp')
        .controller('ScenarioListDialogController', ScenarioListDialogController);

    ScenarioListDialogController.$inject = ['$scope', '$state', '$uibModalInstance', '$http'];

    function ScenarioListDialogController($scope, $state, $uibModalInstance, $http) {
        var vm = this;

        vm.clear = clear;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        $http({
            method: 'GET',
            url: 'auth/api/account',
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            $scope.userInfo = response.data;
            $http({
                method: 'GET',
                url: 'apiservice/api/simplescenarios?emailUser='+$scope.userInfo.email,
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

        $http({
            method: 'GET',
            url: 'apiservice/api/apiDoc/infos',
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            $scope.apiDocTitleVersions = response.data.resultData.data;
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });

        $scope.addScenario = function (tagName, scenarioName) {
            // $.parseJSON();
            var postData = new Object();
            postData.tagName = tagName;
            postData.scenarioName = scenarioName;
            postData.title = $("#select_title option:selected").text();
            postData.version = $("#select_version option:selected").text();
            var flag = true;
            for (var simpleScenario in $scope.simpleScenarios) {
                if (tagName == $scope.simpleScenarios[simpleScenario].tagName && scenarioName == $scope.simpleScenarios[simpleScenario].scenarioName) {
                    $scope.msg = "场景数据存在重复,请修改";
                    flag = false;
                }
            }
            if (flag) {
                $http({
                    method: 'POST',
                    url: 'apiservice/api/scenario?emailUser=' + $scope.userInfo.email,
                    data: postData
                }).then(function successCallback(response) {
                    $scope.$emit('webuiApp:issueInProjectUpdate', response.data);
                    $uibModalInstance.close(response.data);
                    vm.isSaving = false;
                    // alert(angular.toJson(response.data))
                    // $window.location = '/scenariosPage';
                    // this callback will be called asynchronously
                    // when the response is available
                }, function errorCallback(response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    vm.isSaving = false;
                });
            }
        }

    }
})();
