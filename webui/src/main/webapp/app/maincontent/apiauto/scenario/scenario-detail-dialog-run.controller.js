
(function () {
    'use strict';

    angular
        .module('webuiApp')
        .controller('ScenarioDetailRunController', ScenarioDetailRunController);

    ScenarioDetailRunController.$inject = ['$scope', '$state', '$http', '$uibModalInstance', '$window'];

    function ScenarioDetailRunController($scope, $state, $http, $uibModalInstance, $window) {
        var vm = this;

        vm.clear = clear;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        // var scenarioCurrentId = $window.location.toString().split("/")[5];

        $http({
            method: 'GET',
            url: 'auth/api/account',
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            $scope.userInfo = response.data;
            $scope.scenarioId = $window.location.toString().split("/")[5];
            $http({
                method: 'GET',
                url: 'apiservice/api/simplescenarios?emailUser='+$scope.userInfo.email,
            }).then(function successCallback(response) {
                $scope.simpleScenarios = response.data.resultData.data;
                // this callback will be called asynchronously
                // when the response is available
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });

            $http({
                method: 'GET',
                url: 'apiservice/api/scenario/' + $window.location.toString().split("/")[5]+'/sendrequest'
            }).then(function successCallback(response) {
                $scope.requestAll = response.data.resultData.data;
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

        $scope.getParams = function (elementId, params) {
            $(elementId).find("tbody tr").each(function (i) {
                if (i > 0) {
                    var param = new Object();
                    param.name = $(elementId).find("tbody tr:eq(" + i + ")").find("td:eq(0)").text();
                    param.value = $(elementId).find("tbody tr:eq(" + i + ")").find("td:eq(1) input[type='text']").val();
                    param.in = $(elementId).find("tbody tr:eq(" + i + ")").find("td:eq(2)").text();
                    params.splice(0, 0, param);
                }
            });
        }


        $scope.getChecks = function (elementId, checks) {
            $(elementId).find("tbody tr").each(function (i) {
                if (i > 0) {
                    var check = new Object();
                    check.field = $(elementId).find("tbody tr:eq(" + i + ")").find("td:eq(0) input[type='text']").val();
                    check.checkMethod = $(elementId).find("tbody tr:eq(" + i + ")").find("td:eq(1) option:selected").text();
                    check.targetField = $(elementId).find("tbody tr:eq(" + i + ")").find("td:eq(2) input[type='text']").val();
                    check.expect = $(elementId).find("tbody tr:eq(" + i + ")").find("td:eq(3) input[type='text']").val();
                    checks.splice(checks.length, 0, check);
                }
            });

        }

        $scope.SendRequest = function (scenarioId, orderNum) {
            var dependsOnInfo = new Object();

            var checks = [];
            var params = [];
            this.getChecks("#myChecks_" + orderNum, checks);
            this.getParams("#myParams_" + orderNum, params);

            dependsOnInfo.orderNum = orderNum;
            dependsOnInfo.scheme = $("#myScheme_" + orderNum + " option:selected").text();
            dependsOnInfo.host = $("#myHost_" + orderNum).val();
            dependsOnInfo.urlPath = $("#myPath_" + orderNum).val();

            dependsOnInfo.headers = $.parseJSON($("#myHeader_" + orderNum).val());
            dependsOnInfo.checks = checks;
            dependsOnInfo.params = params;

            alert(JSON.stringify(params));

            $http({
                method: 'PUT',
                url: 'apiservice/api/scenario/'+scenarioId+'/dependon',
                data:dependsOnInfo
            }).then(function successCallback(response) {
                $scope.requestAll = response.data.resultData.data;
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        }



        $scope.addApi = function (apiInfo_out, APIInfos_in, APIInfos_out) {
            $scope.APIInfos_in = APIInfos_in;
            $scope.APIInfos_out = APIInfos_out;
            for (var i in APIInfos_out) {
                if (APIInfos_out[i].apiDocId == apiInfo_out.apiDocId && APIInfos_out[i].summary == apiInfo_out.summary && APIInfos_out[i].basePath == apiInfo_out.basePath && APIInfos_out[i].version == apiInfo_out.version && APIInfos_out[i].urlPath == apiInfo_out.urlPath && APIInfos_out[i].httpMethod == apiInfo_out.httpMethod) {
                    APIInfos_in.splice(APIInfos_in.length, 0, APIInfos_out[i]);
                    APIInfos_out.splice(i, 1);
                    APIInfos_out.splice(APIInfos_out.length,0, APIInfos_in[APIInfos_in.length-1]);
                    break;
                }
            }
        }

        $scope.delApi = function (apiInfo_in, APIInfos_in, APIInfos_out) {
            $scope.APIInfos_in = APIInfos_in;
            $scope.APIInfos_out = APIInfos_out;
            for (var i in APIInfos_in) {
                if (APIInfos_in[i].apiDocId == apiInfo_in.apiDocId && APIInfos_in[i].summary == apiInfo_in.summary && APIInfos_in[i].basePath == apiInfo_in.basePath && APIInfos_in[i].version == apiInfo_in.version && APIInfos_in[i].urlPath == apiInfo_in.urlPath && APIInfos_in[i].httpMethod == apiInfo_in.httpMethod) {
                    // APIInfos_out.splice(0, 0, APIInfos_in[i]);
                    APIInfos_in.splice(i, 1);
                    break;
                }
            }

        }

        $scope.addScenarioApi = function (scenarioDocId, APIInfos_in) {
            var object = new Object();
            object.apiListInfos = APIInfos_in;
            var jsonData = JSON.parse(JSON.stringify(object));

            $http({
                method: 'PUT',
                url: 'apiservice/api/scenario/' + scenarioDocId + '/apis',
                data: jsonData
            }).then(function successCallback(response) {
                // $window.location = '/#/scenario-list/'+scenarioDocId;
                // this callback will be called asynchronously
                // when the response is available
                $scope.$emit('webuiApp:issueInProjectUpdate', response.data);
                $uibModalInstance.close(response.data);
                vm.isSaving = false;
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                alert("failer");

                vm.isSaving = false;

            });

        }

        $scope.addChecks = function (orderNum) {
            $("#myChecks_" + orderNum).append("<tr><td class=\"col-md-3\"><input type=\"text\" class=\"form-control\"></td><td class=\"col-md-3\"><select id=\"CheckMethodSelect\" class=\"form-control select2\" style=\"width: 100%;\" ng-model=\"SchemeSelect\"><option ng-selected=\"true\">Save</option><option>Equal</option><option>Contain</option><option>IsNull</option><option>IsNotContain</option><option>HasItem</option><option>DoNotHasItem</option></select></td><td class=\"col-md-3\"><input type=\"text\" class=\"form-control\"></td><td class=\"col-md-3\"><input type=\"text\" class=\"form-control\" value=\"expect msg\"></td></tr>");

        }


        $scope.sendRequestAll = function (scenarioId) {
            $http({
                method: 'GET',
                url: 'apiservice/api/scenario/' + scenarioId+'/sendrequest'
            }).then(function successCallback(response) {
                $scope.requestAll = response.data.resultData.data;
                // this callback will be called asynchronously
                // when the response is available
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        }

        $scope.getResultFromResponse = function (checks) {
            var str = JSON.stringify(checks);
            if(str.indexOf('"status":"KO"')>0){
                return 'KO';
            }else{
                return 'OK';
            }
        }

        $scope.parseURL = function (url) {
            var a = document.createElement('a');
            a.href = url;
            return {
                source: url,
                protocol: a.protocol.replace(':', ''),
                host: a.hostname,
                port: a.port,
                query: a.search,
                params: (function () {
                    var ret = {},
                        seg = a.search.replace(/^\?/, '').split('&'),
                        len = seg.length, i = 0, s;
                    for (; i < len; i++) {
                        if (!seg[i]) {
                            continue;
                        }
                        s = seg[i].split('=');
                        ret[s[0]] = s[1];
                    }
                    return ret;
                })(),
                file: (a.pathname.match(/\/([^\/?#]+)$/i) || [, ''])[1],
                hash: a.hash.replace('#', ''),
                path: a.pathname.replace(/^([^\/])/, '/$1'),
                relative: (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [, ''])[1],
                segments: a.pathname.replace(/^\//, '').split('/')
            };

        }}
})();
