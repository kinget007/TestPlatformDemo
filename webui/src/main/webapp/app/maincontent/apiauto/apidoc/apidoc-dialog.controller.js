(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('ApiDocDialogController', ApiDocDialogController);

    ApiDocDialogController.$inject = ['$scope', '$state', '$http', '$uibModalInstance'];

    function ApiDocDialogController ($scope, $state, $http, $uibModalInstance) {
        var vm = this;

        vm.clear = clear;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        $scope.apiSave = function () {
                // vm.isSaving = true;

                // alert($scope.editor_code.data.info.title + ":"+ $scope.editor_code.data.info.version);
                $http({
                    method: 'GET',
                    url: 'apiservice/api/apiDoc2?title='+$scope.editor_code.data.info.title +'&&version='+$scope.editor_code.data.info.version
                }).then(function successCallback(response) {
                    console.log($scope.editor_code.data.info.title + ":"+ $scope.editor_code.data.info.version);
                    console.log(angular.toJson(response));
                    if (response.data == "") {
                        SwaggerParser.validate($scope.editor_code.data, {
                            $refs: {
                                internal: true
                            }
                        }, function (err, api) {
                            if (err) {
                                $scope.msg = "文档格式有误";
                                console.log(angular.toJson(err))
                            }
                            else {
                                $scope.swagger = {"swagger":$scope.editor_code.data};
                                $http({
                                    method: 'POST',
                                    url: 'apiservice/api/apiDoc',
                                    data: angular.toJson($scope.swagger.swagger)
                                }).then(function successCallback(response) {
                                    if(response.data.resultData.data){
                                        // $window.location = '/apiDocPage';
                                        console.log("$window.location = '/#/apidoc'");
                                    }else{
                                        $scope.msg = "录入失败";
                                    }
                                    // this callback will be called asynchronously
                                    // when the response is available
                                }, function errorCallback(response) {
                                    // called asynchronously if an error occurs
                                    // or server returns response with an error status.
                                    $scope.msg = "请求发生错误";
                                    console.log($scope.msg);

                                });
                            }
                        });
                    } else {
                        $scope.msg = "项目录入重复,请检查info中'title'与'version'属性";
                    }
                    // this callback will be called asynchronously
                    // when the response is available
                }, function errorCallback(response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                });
            };

        var json_swagger = {
            "swagger": "2.0",
            "info": {"version": "0.0.0", "title": "<enter your title>"},
            "paths": {
                "/persons": {
                    "get": {
                        "description": "Gets `Person` objects.Optional query param of **size** determines size of returned array",
                        "parameters": [{
                            "name": "size",
                            "in": "query",
                            "description": "Size of array",
                            "required": true,
                            "type": "number",
                            "format": "double"
                        }],
                        "responses": {
                            "200": {
                                "description": "Successful response",
                                "schema": {
                                    "title": "ArrayOfPersons",
                                    "type": "array",
                                    "items": {
                                        "title": "Person",
                                        "type": "object",
                                        "properties": {"name": {"type": "string"}, "single": {"type": "boolean"}}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
        $scope.editor_code = {data: json_swagger, options: {mode: 'code'}};

        $scope.editor_tree = {data: json_swagger, options: {mode: 'tree'}};

        $scope.onLoad = function (instance) {
            // instance.expandAll();
            // $scope.editor_tree.data = $scope.editor_code.data;
        };

        $scope.changeOptions = function () {
            $scope.editor_code.options.mode = $scope.editor_code.options.mode == 'tree' ? 'code' : 'tree';
        };

        //other
        $scope.pretty = function (obj) {
            return angular.toJson(obj, true);
        }

        $scope.newApiDoc = function () {
            $scope.editor_code.data = json_swagger;
        }
    }
})();
