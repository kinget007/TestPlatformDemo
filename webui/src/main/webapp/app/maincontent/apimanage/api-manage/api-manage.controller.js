(function () {
    'use strict';

    angular
        .module('webuiApp')
        .controller('ApiManageController', ApiManageController);

    ApiManageController.$inject = ['$scope', '$state', '$resource'];

    function ApiManageController($scope, $state, $resource) {

        // $(".select2").select2();
        var vm = this;

        vm.workspaces = [];
        vm.apiTags = [];
        vm.workspacesNodesStarreds = [];
        vm.DocumentationProjectsByWorkspaceId = [];

        // var Workspace = $resource('apimanageservice/api/workspaces', {}, {
        //     'query': {method: 'GET', isArray: true}});

        var api_info_urlpath = "/api/api-infos/:id";
        var api_conversation_urlpath = "/api/conversations/:conversationId";
        var dataMaps_urlpath = "/api/dataMaps/:id";
        var documentation_urlpath = "/api/documentation/projects/:id";
        var entities_urlpath = "/api/entities/:id";
        var entities_generate_api_urlpath = "/api/entities/:id/generate-api";
        var environments_urlpath = "/api/environments:/id";
        var import_restfiddle_urlpath = "/api/import/com.king.yyl.restfiddle";
        var import_postman_urlpath = "/api/import/postman";
        var import_raml_urlpath = "/api/import/raml";
        var import_swagger_urlpath = "/api/import/swagger";
        var api_logs_urlpath = "/api/logs/:id";
        var api_modules_urlpath = "/api/modules/:id";
        var api_nodes_urlpath = "/api/nodes/:id";
        var api_nodes_copy_urlpath = "/api/nodes/:id/copy";
        var api_nodes_move_urlpath = "/api/nodes/:id/move";
        var api_nodes_tags_urlpath = "/api/nodes/:id/tags";
        var api_nodes_tree_urlpath = "/api/nodes/:id/tree";
        var api_nodes_child_urlpath = "/api/nodes/:id/child";
        var oauth_form_urlpath = "/api/oauth/form";
        var oauth_redirect_urlpath = "/api/oauth/redirect";
        var oauth2_urlpath = "/api/oauth2/:id";
        var permissions_urlpath = "/api/permissions/:id";
        var ping_urlpath = "/api/ping";
        var processor_urlpath = "/api/processor";
        var processor_folders_urlpath = "/api/processor/folders/:id";
        var processor_folders_report_urlpath = "/api/processor/folders/:id/report";
        var processor_projects_urlpath = "/api/processor/projects/:id";
        var processor_projects_report_urlpath = "/api/processor/projects/:id/report";
        var profile_info_urlpath = "/api/profile-info";
        var report_projects_urlpath = "/api/report/projects/:id";
        var requests_api_urls_urlpath = "/api/requests/api-urls";
        var requests_http_headers_urlpath = "/api/requests/http-headers";
        var requests_assertiondto_urlpath = "/api/requests/:nodeId/assertiondto";
        var requests_asserts_urlpath = "/api/requests/:nodeId/asserts";
        var roles_urlpath = "/api/roles/:id";
        var stars_urlpath = "/api/stars/:id";
        var swagger_apis_urlpath = "/api/swagger-apis/:id";
        var tags_urlpath = "/api/tags/:id";
        var formparam_urlpath = "/api/test/formparam";
        var workspaces_urlpath = "/api/workspaces/:id";
        var workspaces_export_urlpath = "/api/workspaces/:id/export";
        var workspaces_nodes_starred_urlpath = "/api/workspaces/:workspaceId/nodes/starred";
        var workspaces_projects_urlpath = "/api/workspaces/:workspaceId/projects/:id";
        var workspaces_projects_copy_urlpath = "/api/workspaces/:workspaceId/projects/:id/copy";
        var workspaces_tags_urlpath = "/api/workspaces/:workspaceId/tags/:id";
        var workspaces_tags_nodes_urlpath = "/api/workspaces/:workspaceId/tags/:tagId/nodes";
        var entities_login_urlpath = "/api/:projectId/entities/login";
        var entities_logout_urlpath = "/api/:projectId/entities/logout";
        var entities_urlpath = "/api/:projectId/entities/:name";
        var entities_list_urlpath = "/api/:projectId/entities/:name/list";
        var entities_name_id_urlpath = "/api/:projectId/entities/:name/:id";

        var entities_uuid_urlpath = "/api/:projectId/entities/:name/{uuid}";

        var entities_urlpath = "/api/:projectId/entities/:name";
        var entities_urlpath = "/api/:projectId/entities/:name";

        var entities_login_urlpath = "/api/:projectId/entities/login";



        var Workspace = $resource('apimanageservice/api/workspaces:id', {}, {
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

        var ApiTags = $resource('apimanageservice'+tags_urlpath, {}, {
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

        var WorkspacesNodesStarred = $resource('apimanageservice'+workspaces_nodes_starred_urlpath, {}, {
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



        var DocumentationProjectsByWorkspaceId = $resource('apimanageservice'+workspaces_projects_urlpath, {}, {
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


        var DocumentationProjects = $resource('apimanageservice'+documentation_urlpath, {}, {
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






        var ActivityLog = $resource('apimanageservice/api/logs:id', {}, {
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


        var ApiUrls = $resource('apimanageservice/api/users/:id', {}, {
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


        Workspace.query(function (result) {
            vm.workspaces = result;
            $scope.workspaceId = vm.workspaces[0].id;

            WorkspacesNodesStarred.query({workspaceId:vm.workspaces[0].id},function (result) {
                vm.workspacesNodesStarreds = result;
                // alerts(angular.toJson(vm.workspacesNodesStarreds));
            });

            DocumentationProjectsByWorkspaceId.query({workspaceId:vm.workspaces[0].id},function (result) {
                vm.DocumentationProjectsByWorkspaceId = result;
                // alert(angular.toJson(vm.DocumentationProjectsByWorkspaceId));
            });
        });

        ApiTags.query(function (result) {
            vm.apiTags = result;
        });


    }
})();
