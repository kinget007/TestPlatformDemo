(function () {
    'use strict';
    angular.module('webuiApp')
        .value('apiUrl', '/apimanageservice')
        .controller('ApiPostmanController', ApiPostmanController)
        .filter('to_trusted', ['$sce', function ($sce) {
            return function (text) {
                return $sce.trustAsHtml(text);
            };
        }]);

    ApiPostmanController.$inject = ['$scope', '$state', 'moment', 'WorkspaceResource', 'EnvironmentResource', 'ProjectResource', 'ApiRequestResource', 'TagResource', 'NodeResource', 'ApiResource', 'ConversationResource'];

    function ApiPostmanController($scope, $state, moment, WorkspaceResource, EnvironmentResource, ProjectResource, RfRequestResource, TagResource, NodeResource, ApiResource, ConversationResource) {
        var vm = this;

        vm.workspaceIndex = 0;
        vm.projectIndex = 0;
        vm.workspaces = [];
        vm.envs = [];
        vm.requestUrls = [];
        vm.requestHttpHeaders = [];

        vm.workspace = {};
        vm.projects = [];
        vm.tags = [];
        vm.project = {};
        vm.tree = {};

        vm.methods = ["GET", "POST", "PUT", "DELETE"];//,"HEAD","OPTIONS","TRACE"
        vm.assertComparators = ["=", "!=", "Contains", "! Contains", ">", ">=", "<", "<=", "Contains Key", "! Contains Key", "Contains Value", "! Contains Value"];

        vm.node = {};

        vm.apiRequestDto = {
            "apiUrl": "",
            "assertionDTO": {
                "bodyAssertDTOs": [],
                "bodyContentType": "",
                "responseSize": 0,
                "responseTime": 0,
                "statusCode": 0
            },
            "createdBy": "",
            "createdDate": "",
            "description": "",
            "lastModifiedBy": "",
            "lastModifiedDate": "",
            "methodType": "",
            "name": "",
            "auth": {
                "authType": "basic",
                "username": "",
                "password": "",
                "accessTokenUrl": "",
                "authorizationUrl": "",
                "accessTokenLocation": "",
                "clientId": "",
                "clientSecret": "",
                "createdBy": "",
                "createdDate": "",
                "description": "",
                "name": "",
                "scopes": ""
                // ,
                // "id": "",
                // "lastModifiedBy": "",
                // "lastModifiedDate": "",
                // "status": ""
            },
            "parameters": [],
            "status": "",
            "id": null,
            "conversationId": null,
            "workspaceId": null
        };

        var glyph_opts = {
            map: {
                doc: "glyphicon glyphicon-file",
                docOpen: "glyphicon glyphicon-file",
                checkbox: "glyphicon glyphicon-unchecked",
                checkboxSelected: "glyphicon glyphicon-check",
                checkboxUnknown: "glyphicon glyphicon-share",
                dragHelper: "glyphicon glyphicon-play",
                dropMarker: "glyphicon glyphicon-arrow-right",
                error: "glyphicon glyphicon-warning-sign",
                expanderClosed: "glyphicon glyphicon-menu-right",
                expanderLazy: "glyphicon glyphicon-menu-right",  // glyphicon-plus-sign
                expanderOpen: "glyphicon glyphicon-menu-down",  // glyphicon-collapse-down
                folder: "glyphicon glyphicon-folder-close",
                folderOpen: "glyphicon glyphicon-folder-open",
                loading: "glyphicon glyphicon-refresh glyphicon-spin"
            }
        };

        firstLoad();
        loadEnvs();
        loadRequestUrls();


        //resources
        function firstLoad() {
            WorkspaceResource.findAllUsingGET12(function (result) {
                vm.workspaces = result;
                loadProjectsByWorkspaceId(vm.workspaces[vm.workspaceIndex].id);
                loadWorkspaceByWorkspaceId(vm.workspaces[vm.workspaceIndex].id);
                loadTagsByWorkspaceId(vm.workspaces[vm.workspaceIndex].id);

                ProjectResource.findByIdUsingGET9({
                    workspaceId: vm.workspaces[vm.workspaceIndex].id,
                    id: vm.workspaces[vm.workspaceIndex].projects[vm.projectIndex].id
                }, function (result) {
                    vm.project = result;
                    loadTreeByProjectRefId(vm.project.projectRef.id);
                })

            });
        };

        function loadWorkspaces() {
            WorkspaceResource.findAllUsingGET12(function (result) {
                vm.workspaces = result;
            });
        };

        function loadEnvs() {
            EnvironmentResource.findAllUsingGET3(function (result) {
                vm.envs = result;
            })
        };

        function loadRequestUrls() {
            RfRequestResource.findUniqueApiUrlsUsingGET(function (result) {
                vm.requestUrls = result;
                $('#tree').treeview({data: vm.requestUrls});
            })
        };

        function loadProjectsByWorkspaceId(workspaceId) {
            ProjectResource.findProjectsFromAWorkspaceUsingGET({workspaceId: workspaceId}, function (result) {
                vm.projects = result;
            })
        };

        function loadWorkspaceByWorkspaceId(workspaceId) {
            WorkspaceResource.findByIdUsingGET13({id: workspaceId}, function (result) {
                vm.workspace = result;
            })
        };

        function loadTagsByWorkspaceId(workspaceId) {
            TagResource.findTagsFromAWorkspaceUsingGET({workspaceId: workspaceId}, function (result) {
                vm.tags = result;
            })
        };

        function loadProjectByWorkspaceIdAndProjectId(workspaceId, projectId) {
            ProjectResource.findByIdUsingGET9({workspaceId: workspaceId, id: projectId}, function (result) {
                vm.project = result;
            })
        };

        function loadTreeByProjectRefId(projectRefId) {
            NodeResource.getProjectTreeUsingGET({id: projectRefId}, function (result) {
                vm.tree = result;
                var treeObj = $("#collectionTree").fancytree("getTree");
                var uiTree = [];
                var uiSideTreeData = {};
                nodeConverter(vm.tree, uiSideTreeData);
                uiTree.push(uiSideTreeData);
                treeObj.reload(uiTree);
                $scope.expandAllNodes();
            })
        };

        function loadNodeByNodeId(nodeId) {
            NodeResource.findByIdUsingGET6({id: nodeId}, function (result) {
                if (result.nodeType != "WORKSPACE" || result.nodeType != "PROJECT" || result.nodeType != "SOCKET") {
                    vm.node = result;
                    console.log("loadNodeByNodeId : " + angular.toJson(vm.node));
                    vm.apiRequestDto = vm.node.conversation.apiRequest;
                    console.log("vm.node.conversation.apiRequest.assertion.bodyAsserts : " + angular.toJson(vm.node.conversation.apiRequest.assertion.bodyAsserts));
                    vm.apiRequestDto = {
                        "assertionDTO": {
                            "bodyAssertDTOs": vm.node.conversation.apiRequest.assertion.bodyAsserts
                        }
                    };

                    $scope.sendApiResponse = {
                        "apiRequestDTO":
                            {
                                "assertionDTO":
                                    {
                                       "bodyAssertDTOs":  vm.node.conversation.apiRequest.assertion.bodyAsserts
                                    }
                            },
                        "apiResponseDTO":{
                            "body":vm.node.conversation.apiResponse.body
                        }
                    };
                }
            })
        };


        //util function
        var getActiveFolder = function () {
            var node = $("#collectionTree").fancytree("getActiveNode");
            var folder = getParentFolder(node);
            if (folder) {
                return folder;
            } else {
                // return root folder
                return $("#collectionTree").fancytree("getRootNode").getFirstChild();
            }
        };

        var getParentFolder = function (node) {
            if (node) {
                if (node.isFolder()) {
                    return node;
                } else if (node.getParent().isFolder()) {
                    return node.getParent();
                } else {
                    return getParentFolder(node.getParent());
                }
            } else {
                return null;
            }
        };

        var getActiveFolderDataId = function () {
            return getActiveFolder().data.id;
        };

        var getActiveNodeDataId = function () {
            var node = $("#collectionTree").fancytree("getActiveNode");
            console.log("getActiveNodeDataId : " + node.data.id);
            return node.data.id;
        };

        var nodeConverter = function (serverNode, uiNode) {
            if (serverNode.nodeType == 'PROJECT' || serverNode.nodeType == 'FOLDER' || serverNode.nodeType == 'ENTITY') {
                uiNode.folder = true;
                uiNode.id = serverNode.id;
                uiNode.key = serverNode.id;
                uiNode.name = serverNode.name;
                uiNode.method = serverNode.method;
                uiNode.description = serverNode.description;
                uiNode.nodeType = serverNode.nodeType;
                uiNode.title = serverNode.name;

            }
            if (serverNode.children == undefined || serverNode.children.length == 0) {
                return;
            }

            uiNode.children = new Array();
            for (var i = 0; i < serverNode.children.length; i++) {
                if (serverNode.children[i].nodeType != 'FOLDER' && serverNode.children[i].nodeType != 'ENTITY') {
                    var colorCode = "";
                    var title = "";

                    if (serverNode.children[i].method) {
                        colorCode = getColorCode(serverNode.children[i].method);
                        // title = '<div><span class="lozenge left ' + colorCode + ' auth_required">' + serverNode.children[i].method + '</span>' + '<span class = "large-text ' + getTitleClass(serverNode.children[i].method) + '" title = "' + serverNode.children[i].name + '">' + serverNode.children[i].name + '</span>' +displayLastModified(serverNode.children[i])+ treeNodeView+'</div>';
                        title = '<div><font style="color: ' + colorCode + ' ">' + serverNode.children[i].method + '</font>&nbsp;&nbsp;' + serverNode.children[i].name + '' + '' + displayLastModified(serverNode.children[i]) + '';
                    } else {
                        title = serverNode.children[i].name;
                    }
                    uiNode.children.push({
                        title: title,
                        id: serverNode.children[i].id,
                        key: serverNode.children[i].id,
                        name: serverNode.children[i].name,
                        method: serverNode.children[i].method,
                        description: serverNode.children[i].description,
                        nodeType: serverNode.children[i].nodeType
                    });
                } else if (serverNode.children[i].nodeType == 'FOLDER' || serverNode.children[i].nodeType == 'ENTITY') {
                    uiNode.children.push({});
                    nodeConverter(serverNode.children[i], uiNode.children[i]);
                }

            }
        };

        var getColorCode = function (method) {
            switch (method) {
                case "GET":
                    return "blue";
                    break;
                case "POST":
                    return "green";
                    break;
                case "DELETE":
                    return "red";
                    break;
                case "PUT":
                    return "orange";
                    break;
            }
        };

        var getTitleClass = function (method) {
            if (method)
                return method.toLowerCase();
            else
                return "";
        };

        var displayLastModified = function (serverNodeChild) {
            var lastModifiedDate = serverNodeChild.lastModifiedDate;
            var lastModifiedBy = serverNodeChild.lastModifiedBy;
            var time = "";
            var runBy = "";
            var currentDate = moment(new Date());
            if (lastModifiedDate) {
                var requestDiff = currentDate.diff(lastModifiedDate, 'hours');
                if (requestDiff == 0) {
                    var min = currentDate.diff(lastModifiedDate, 'minutes')
                    if (min > 1) {
                        time = min + " minutes ago";
                    }
                    else {
                        time = min + " minute ago";
                    }
                }
                else if (requestDiff <= 1) {
                    time = requestDiff + ' hour ago';
                }
                else if (requestDiff < 24) {
                    time = requestDiff + ' hours ago';
                }
                else {
                    time = moment(lastModifiedDate).format('MMM DD hh:mma');
                }
            }
            if (lastModifiedBy !== null) {
                runBy = 'by ' + lastModifiedBy.name;
            }
            return '&nbsp;<span>' + time + '&nbsp;' + runBy + '</span>';
        };


        //config
        $("#collectionTree").fancytree({
            extensions: ["dnd", "edit", "glyph", "table"],
            checkbox: false,
            dnd: {
                focusOnClick: true,
                dragStart: function (node, data) {
                    return true;
                },
                dragEnter: function (node, data) {
                    return true;
                },
                dragDrop: function (node, data) {
                    data.otherNode.copyTo(node, data.hitMode);
                }
            },
            glyph: glyph_opts,
            source: [],
            // source: {url: "ajax-tree-taxonomy.json", debugDelay: 1000},
            table: {
                // checkboxColumnIdx: 1,
                nodeColumnIdx: 1
            },

            activate: function (event, data) {
                $scope.getNodeInfo();
            },
            lazyLoad: function (event, data) {
                data.result = {url: "ajax-sub2.json", debugDelay: 1000};
            },
            renderColumns: function (event, data) {
                var node = data.node,
                    $tdList = $(node.tr).find(">td");
                // $tdList.eq(0).text(node.getIndexHier());
                // $tdList.eq(2).text(!!node.folder);
                $tdList.eq(2).text(node.api);

            }
        });

        //click function
        $scope.newFolder = function (name, description, projectId, projrctRefId) {
            var NodeModel = {
                id: null,
                name: '',
                description: '',
                parentId: '',
                projectId: '',
                nodeType: 'Folder',
                method: '',
                conversationDTO: null,
                genericEntityDTO: null,
                tags: []
            };

            NodeModel.name = name;
            NodeModel.description = description;
            NodeModel.nodeType = 'FOLDER';
            NodeModel.parentId = getActiveFolderDataId();
            NodeModel.projectId = projectId;

            console.log(getActiveFolderDataId());
            console.log(angular.toJson(NodeModel))
            NodeResource.createUsingPOST6({parentId: getActiveFolderDataId()}, NodeModel, function (data) {
                console.log(angular.toJson(data));
                $("#folderModal").modal('hide');
                loadTreeByProjectRefId(projrctRefId);
            });
        };

        $scope.delNode = function (flag, projrctRefId) {
            if (flag == '0') {
                $("#delNodeModal").modal('show');
            } else if (flag == '1') {
                NodeResource.deleteUsingDELETE6({id: getActiveNodeDataId()}, function (data) {
                    console.log(angular.toJson(data));
                    $("#delNodeModal").modal('hide');
                    loadTreeByProjectRefId(projrctRefId);
                });
            }
        };

        $scope.expandAllNodes = function () {
            $("#collectionTree").fancytree("getRootNode").visit(function (node) {
                node.setExpanded(true);
            });
        };

        $scope.collapseAllNodes = function () {
            $("#collectionTree").fancytree("getRootNode").visit(function (node) {
                node.setExpanded(false);
            });
        };

        $scope.addParams = function (params, type) {
            params.push({
                "description": "",
                "example": "",
                "in": type,
                "name": "",
                "required": true,
                "value": ""
            });

            if(type=='body'){
                $("#addBodyParamBtn").hide();
                $("#delBodyParamBtn").show();
                // $("#addBodyParamBtn").attr("disabled", true);
                // $("#delBodyParamBtn").attr("disabled", false);
            }
        };

        $scope.removeBodyParams = function (params) {

            angular.forEach(params, function(data,index,array){
            //data等价于array[index]
                if(data.in=="body"){
                    params.splice(index, 1);
                    $("#addBodyParamBtn").show();
                    $("#delBodyParamBtn").hide();
                    // $("#addBodyParamBtn").attr("disabled", true);
                    // $("#delBodyParamBtn").attr("disabled", false);
                }
                console.log(data.a+'='+array[index].a);
            });

        };


        $scope.removeParams = function (params, index) {
            params.splice(index, 1);
        };

        $scope.addAssertParams = function (params) {
            params.push({
                "actualValue": "",
                "comparator": "",
                "expectedValue": "",
                "id": "",
                "propertyName": "",
                "success": true,
                "version": 0
            });
        };

        $scope.removeAssertParams = function (params, index) {
            params.splice(index, 1);
        };

        $scope.getNodeInfo = function () {
            console.log("call getNodeInfo");
            var nodeId = getActiveNodeDataId();
            loadNodeByNodeId(nodeId);
        };

        $scope.workspaceSave = function () {
            var workspaceName = $("#workspaceName").val();
            var workspaceDescription = $("#workspaceDescription").val();
            var workspace = {"name": workspaceName, "description": workspaceDescription};
            console.log(angular.toJson(workspace));
            WorkspaceResource.createUsingPOST14(workspace, function (data) {
                console.log(angular.toJson(data));
            });
            $("#workspaceModal").modal('hide');
        };

        $scope.projectSave = function (workspaceId) {
            var workspaceName = $("#projectTextField").val();
            var workspaceDescription = $("#projectTextArea").val();
            var workspace = {"name": workspaceName, "description": workspaceDescription};
            console.log(angular.toJson(workspace));
            ProjectResource.createUsingPOST9({workspaceId: workspaceId}, workspace, function (data) {
                console.log(angular.toJson(data));
            });
            $("#projectModal").modal('hide');
        };

        $scope.apiSend = function () {
            console.log(angular.toJson(vm.apiRequestDto));

            ApiResource.requestProcessorUsingPOST(vm.apiRequestDto, function (data) {
                console.log(angular.toJson(data));
                $scope.sendApiResponse = data;
                // console.log(data);

            });
        };

        $scope.newRequest = function (workspaceId,projectId, projrctRefId) {

            var requestName = $("#requestName").val();
            var requestTextArea = $("#requestTextArea").val();

            var conversationDTO = {
                "id": null,
                "apiRequestDTO": {},
                "apiResponseDTO": {},
                "name": "",
                "description": "",
                "workspaceId": null
            };

            // conversationDTO.apiRequestDTO = vm.apiRequestDto;
            conversationDTO = $scope.sendApiResponse;
            $scope.sendApiResponse.workspaceId = workspaceId;
            $scope.sendApiResponse.nodeId = "";

            vm.apiRequestDto.name = requestName;
            vm.apiRequestDto.description = requestTextArea;

            ConversationResource.createUsingPOST1(conversationDTO,function (data) {
                console.log(angular.toJson(data));
                console.log("name : " +requestName + "/n description : " + requestTextArea + "/n projectId : " + projectId+ "/n projrctRefId : " + projrctRefId);

                var nodeDto = {
                    "parentId": "",
                    "name": "",
                    "description": "",
                    "projectId": "",
                    "conversationDTO": {"id": null, "name": "", "description": "", "workspaceId": null},
                    "genericEntityDTO": null,
                    "nodeType": "Request",
                    "tags": [],
                    "id": null,
                    "method": ""
                };

                nodeDto.name = requestName;
                nodeDto.description = requestTextArea;
                nodeDto.parentId = getActiveFolderDataId();
                nodeDto.projectId = projectId;
                nodeDto.conversationDTO.id = data.id;

                NodeResource.createUsingPOST6({parentId: getActiveFolderDataId()}, nodeDto, function (data) {
                    console.log(angular.toJson(data));
                    $("#requestModal").modal('hide');
                    loadTreeByProjectRefId(projrctRefId);
                });

            });


        };


        // var EntityModel = {
        //     id : null,
        //     name : '',
        //     description : '',
        //     fields : null,
        //     entityDataList : null
        // };
        // var ConversationModel = {
        //     id : null,
        //     name : '',
        //     description : '',
        //     rfRequest : '',
        //     rfResponse : '',
        //     workspaceId : null
        // };
        // var NodeModel = {
        //     id : null,
        //     name : '',
        //     description : '',
        //     parentId : '',
        //     projectId: '',
        //     nodeType : '',
        //     method : '',
        //     conversationDTO : ConversationModel,
        //     genericEntityDTO : EntityModel,
        //     tags : []
        // };

        // $scope.queryParamsHtml = '';
        //
        // $scope.addQueryParams = function () {
        //     $scope.queryParamsHtml = $scope.queryParamsHtml + "<p><div class=\"row\"> <div class=\"col-md-5\"><input type=\"text\" class=\"form-control\" placeholder=\"Enter Key\" value=\"\"></div><div class=\"col-md-5\"><input type=\"text\" class=\"form-control\" placeholder=\"Enter Value\" value=\"\"></div><div class=\"col-md-2\"><button type=\"button\" class=\"btn btn-default destroy\"><span class=\"glyphicon glyphicon-remove\"></span></button></div></div></p>";
        // };
        //
        // $scope.removeQueryParams = function () {
        //     $scope.queryParamsHtml = $scope.queryParamsHtml + "<p><div class=\"row\"> <div class=\"col-md-5\"><input type=\"text\" class=\"form-control\" placeholder=\"Enter Key\" value=\"\"></div><div class=\"col-md-5\"><input type=\"text\" class=\"form-control\" placeholder=\"Enter Value\" value=\"\"></div><div class=\"col-md-2\"><button type=\"button\" class=\"btn btn-default destroy\"><span class=\"glyphicon glyphicon-remove\"></span></button></div></div></p>";
        // };
    }
})
();
