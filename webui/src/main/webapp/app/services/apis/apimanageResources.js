(function (angular) {
    'use strict';

    var moduleName = 'apimanage';

    angular
        .module(moduleName, ['ngResource'])
        .provider('$resourceActionConfig', [function () {
            var actionConfigs = {$global: {}};

            this.addActionConfig = function (resourceName, actionName, config) {
                if (arguments.length === 1) {
                    actionConfigs.$global = resourceName; // resourceName === config
                    return this;
                } else if (arguments.length === 2) {
                    config = actionName;
                    actionName = '$global';
                }

                actionConfigs[resourceName] = actionConfigs[resourceName] || {};
                actionConfigs[resourceName][actionName] = config;
                return this;
            };

            this.$get = function () {
                return function (resourceName, actionName) {
                    return actionConfigs[resourceName]
                        ? (actionConfigs[resourceName][actionName] || actionConfigs[resourceName].$global)
                        : actionConfigs.$global;
                };
            };
        }])
        .provider('ApiInfoResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'getAllApiInfosUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/apiInfos',
                        isArray: true,
                    }, $resourceActionConfig('apiInfoResource', 'getAllApiInfosUsingGET')),
                    'createApiInfoUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/apiInfos',
                    }, $resourceActionConfig('apiInfoResource', 'createApiInfoUsingPOST')),
                    'updateApiInfoUsingPUT': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/apiInfos',
                    }, $resourceActionConfig('apiInfoResource', 'updateApiInfoUsingPUT')),
                    'getApiInfoUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/apiInfos/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('apiInfoResource', 'getApiInfoUsingGET')),
                    'deleteApiInfoUsingDELETE': angular.extend({
                        method: 'DELETE',
                        url: apiUrl + '/api/apiInfos/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('apiInfoResource', 'deleteApiInfoUsingDELETE')),
                });
            }]};
        }])
        .provider('ConversationResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'findAllUsingGET1': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/conversations',
                    }, $resourceActionConfig('conversationResource', 'findAllUsingGET1')),
                    'createUsingPOST1': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/conversations',
                    }, $resourceActionConfig('conversationResource', 'createUsingPOST1')),
                    'findByIdUsingGET1': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/conversations/:conversationId',
                        params: {
                            'conversationId': '@conversationId',
                        },
                    }, $resourceActionConfig('conversationResource', 'findByIdUsingGET1')),
                    'updateUsingPUT2': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/conversations/:conversationId',
                        params: {
                            'conversationId': '@conversationId',
                        },
                    }, $resourceActionConfig('conversationResource', 'updateUsingPUT2')),
                    'deleteUsingDELETE1': angular.extend({
                        method: 'DELETE',
                        url: apiUrl + '/api/conversations/:conversationId',
                        params: {
                            'conversationId': '@conversationId',
                        },
                    }, $resourceActionConfig('conversationResource', 'deleteUsingDELETE1')),
                });
            }]};
        }])
        .provider('DataMapResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'findAllUsingGET2': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/dataMaps',
                        isArray: true,
                    }, $resourceActionConfig('dataMapResource', 'findAllUsingGET2')),
                    'createUsingPOST2': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/dataMaps',
                    }, $resourceActionConfig('dataMapResource', 'createUsingPOST2')),
                    'findByIdUsingGET2': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/dataMaps/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('dataMapResource', 'findByIdUsingGET2')),
                    'updateUsingPUT3': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/dataMaps/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('dataMapResource', 'updateUsingPUT3')),
                    'deleteUsingDELETE2': angular.extend({
                        method: 'DELETE',
                        url: apiUrl + '/api/dataMaps/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('dataMapResource', 'deleteUsingDELETE2')),
                });
            }]};
        }])
        .provider('ReportResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'generateProjectApiDocumentationUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/documentation/projects/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('reportResource', 'generateProjectApiDocumentationUsingGET')),
                    'generateRunFolderReportUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/processor/folders/:id/report',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('reportResource', 'generateRunFolderReportUsingGET')),
                    'generateRunProjectReportUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/processor/projects/:id/report',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('reportResource', 'generateRunProjectReportUsingGET')),
                    'generateRunProjectReportUsingGET1': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/report/projects/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('reportResource', 'generateRunProjectReportUsingGET1')),
                });
            }]};
        }])
        .provider('GenericEntityResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'findAllUsingGET4': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/entities',
                        isArray: true,
                    }, $resourceActionConfig('genericEntityResource', 'findAllUsingGET4')),
                    'createUsingPOST4': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/entities',
                    }, $resourceActionConfig('genericEntityResource', 'createUsingPOST4')),
                    'findByIdUsingGET4': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/entities/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('genericEntityResource', 'findByIdUsingGET4')),
                    'updateUsingPUT5': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/entities/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('genericEntityResource', 'updateUsingPUT5')),
                    'deleteUsingDELETE4': angular.extend({
                        method: 'DELETE',
                        url: apiUrl + '/api/entities/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('genericEntityResource', 'deleteUsingDELETE4')),
                });
            }]};
        }])
        .provider('GenerateApiResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'generateApiByEntityIdUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/entities/:id/generate-api',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('generateApiResource', 'generateApiByEntityIdUsingPOST')),
                });
            }]};
        }])
        .provider('EnvironmentResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'findAllUsingGET3': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/environments',
                        isArray: true,
                    }, $resourceActionConfig('environmentResource', 'findAllUsingGET3')),
                    'createUsingPOST3': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/environments',
                    }, $resourceActionConfig('environmentResource', 'createUsingPOST3')),
                    'findByIdUsingGET3': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/environments/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('environmentResource', 'findByIdUsingGET3')),
                    'updateUsingPUT4': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/environments/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('environmentResource', 'updateUsingPUT4')),
                    'deleteUsingDELETE3': angular.extend({
                        method: 'DELETE',
                        url: apiUrl + '/api/environments/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('environmentResource', 'deleteUsingDELETE3')),
                });
            }]};
        }])
        .provider('ImportResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'importRestFiddleUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/import/com.king.yyl.restfiddle',
                    }, $resourceActionConfig('importResource', 'importRestFiddleUsingPOST')),
                    'importPostmanUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/import/postman',
                    }, $resourceActionConfig('importResource', 'importPostmanUsingPOST')),
                    'importRamlUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/import/raml',
                    }, $resourceActionConfig('importResource', 'importRamlUsingPOST')),
                    'importSwaggerUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/import/swagger',
                    }, $resourceActionConfig('importResource', 'importSwaggerUsingPOST')),
                });
            }]};
        }])
        .provider('ActivityLogResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'findAllUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/logs',
                        isArray: true,
                    }, $resourceActionConfig('activityLogResource', 'findAllUsingGET')),
                    'createUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/logs',
                    }, $resourceActionConfig('activityLogResource', 'createUsingPOST')),
                    'findByIdUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/logs/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('activityLogResource', 'findByIdUsingGET')),
                    'updateUsingPUT': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/logs/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('activityLogResource', 'updateUsingPUT')),
                    'deleteUsingDELETE': angular.extend({
                        method: 'DELETE',
                        url: apiUrl + '/api/logs/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('activityLogResource', 'deleteUsingDELETE')),
                });
            }]};
        }])
        .provider('ModuleResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'findAllUsingGET5': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/modules',
                        isArray: true,
                    }, $resourceActionConfig('moduleResource', 'findAllUsingGET5')),
                    'createUsingPOST5': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/modules',
                    }, $resourceActionConfig('moduleResource', 'createUsingPOST5')),
                    'findByIdUsingGET5': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/modules/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('moduleResource', 'findByIdUsingGET5')),
                    'updateUsingPUT6': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/modules/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('moduleResource', 'updateUsingPUT6')),
                    'deleteUsingDELETE5': angular.extend({
                        method: 'DELETE',
                        url: apiUrl + '/api/modules/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('moduleResource', 'deleteUsingDELETE5')),
                });
            }]};
        }])
        .provider('NodeResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'findAllUsingGET6': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/nodes',
                        isArray: true,
                    }, $resourceActionConfig('nodeResource', 'findAllUsingGET6')),
                    'findByIdUsingGET6': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/nodes/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('nodeResource', 'findByIdUsingGET6')),
                    'updateUsingPUT7': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/nodes/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('nodeResource', 'updateUsingPUT7')),
                    'deleteUsingDELETE6': angular.extend({
                        method: 'DELETE',
                        url: apiUrl + '/api/nodes/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('nodeResource', 'deleteUsingDELETE6')),
                    'copyUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/nodes/:id/copy',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('nodeResource', 'copyUsingPOST')),
                    'moveUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/nodes/:id/move',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('nodeResource', 'moveUsingPOST')),
                    'addTagsUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/nodes/:id/tags',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('nodeResource', 'addTagsUsingPOST')),
                    'getProjectTreeUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/nodes/:id/tree',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('nodeResource', 'getProjectTreeUsingGET')),
                    'getChildrenUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/nodes/:parentId/children',
                        params: {
                            'parentId': '@parentId',
                        },
                        isArray: true,
                    }, $resourceActionConfig('nodeResource', 'getChildrenUsingGET')),
                    'createUsingPOST6': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/nodes/:parentId/children',
                        params: {
                            'parentId': '@parentId',
                        },
                    }, $resourceActionConfig('nodeResource', 'createUsingPOST6')),
                    'findStarredNodesUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/workspaces/:workspaceId/nodes/starred',
                        params: {
                            'workspaceId': '@workspaceId',
                        },
                        isArray: true,
                    }, $resourceActionConfig('nodeResource', 'findStarredNodesUsingGET')),
                });
            }]};
        }])
        .provider('ApiResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'oauthFormRedirectUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/oauth/form',
                    }, $resourceActionConfig('apiResource', 'oauthFormRedirectUsingPOST')),
                    'oauth2RedirectUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/oauth/redirect',
                    }, $resourceActionConfig('apiResource', 'oauth2RedirectUsingPOST')),
                    'requestProcessorUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/processor',
                    }, $resourceActionConfig('apiResource', 'requestProcessorUsingPOST')),
                    'runFolderByIdUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/processor/folders/:id',
                        params: {
                            'id': '@id',
                        },
                        isArray: true,
                    }, $resourceActionConfig('apiResource', 'runFolderByIdUsingGET')),
                    'runProjectByIdUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/processor/projects/:id',
                        params: {
                            'id': '@id',
                        },
                        isArray: true,
                    }, $resourceActionConfig('apiResource', 'runProjectByIdUsingGET')),
                });
            }]};
        }])
        .provider('OAuth2Resource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'findAllUsingGET7': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/oauth2',
                        isArray: true,
                    }, $resourceActionConfig('OAuth2Resource', 'findAllUsingGET7')),
                    'createUsingPOST7': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/oauth2',
                    }, $resourceActionConfig('OAuth2Resource', 'createUsingPOST7')),
                    'findByIdUsingGET7': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/oauth2/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('OAuth2Resource', 'findByIdUsingGET7')),
                    'updateUsingPUT8': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/oauth2/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('OAuth2Resource', 'updateUsingPUT8')),
                    'deleteUsingDELETE7': angular.extend({
                        method: 'DELETE',
                        url: apiUrl + '/api/oauth2/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('OAuth2Resource', 'deleteUsingDELETE7')),
                });
            }]};
        }])
        .provider('ProfileInfoResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'getActiveProfilesUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/profileInfo',
                    }, $resourceActionConfig('profileInfoResource', 'getActiveProfilesUsingGET')),
                });
            }]};
        }])
        .provider('ApiRequestResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'findUniqueApiUrlsUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/requests/api-urls',
                        isArray: true,
                    }, $resourceActionConfig('ApiRequestResource', 'findUniqueApiUrlsUsingGET')),
                    'findHttpRequestHeadersUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/requests/http-headers',
                        isArray: true,
                    }, $resourceActionConfig('ApiRequestResource', 'findHttpRequestHeadersUsingGET')),
                });
            }]};
        }])
        .provider('AssertionResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'getSavedAssertsUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/requests/:nodeId/assertiondto',
                        params: {
                            'nodeId': '@nodeId',
                        },
                    }, $resourceActionConfig('assertionResource', 'getSavedAssertsUsingGET')),
                    'updateUsingPUT1': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/requests/:nodeId/assertiondto',
                        params: {
                            'nodeId': '@nodeId',
                        },
                    }, $resourceActionConfig('assertionResource', 'updateUsingPUT1')),
                    'findAssertsUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/requests/:nodeId/asserts',
                        params: {
                            'nodeId': '@nodeId',
                        },
                    }, $resourceActionConfig('assertionResource', 'findAssertsUsingGET')),
                    'saveUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/requests/:nodeId/asserts',
                        params: {
                            'nodeId': '@nodeId',
                        },
                    }, $resourceActionConfig('assertionResource', 'saveUsingPOST')),
                });
            }]};
        }])
        .provider('StarResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'findAllUsingGET10': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/stars',
                        isArray: true,
                    }, $resourceActionConfig('starResource', 'findAllUsingGET10')),
                    'createUsingPOST11': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/stars',
                    }, $resourceActionConfig('starResource', 'createUsingPOST11')),
                    'findByIdUsingGET11': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/stars/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('starResource', 'findByIdUsingGET11')),
                    'updateUsingPUT12': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/stars/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('starResource', 'updateUsingPUT12')),
                    'deleteUsingDELETE11': angular.extend({
                        method: 'DELETE',
                        url: apiUrl + '/api/stars/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('starResource', 'deleteUsingDELETE11')),
                });
            }]};
        }])
        .provider('SwaggerApisResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'getAllSwaggerApisUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/swagger-apis',
                        isArray: true,
                    }, $resourceActionConfig('swaggerApisResource', 'getAllSwaggerApisUsingGET')),
                    'createSwaggerApisUsingPOST': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/swagger-apis',
                    }, $resourceActionConfig('swaggerApisResource', 'createSwaggerApisUsingPOST')),
                    'updateSwaggerApisUsingPUT': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/swagger-apis',
                    }, $resourceActionConfig('swaggerApisResource', 'updateSwaggerApisUsingPUT')),
                    'getSwaggerApisUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/swagger-apis/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('swaggerApisResource', 'getSwaggerApisUsingGET')),
                    'deleteSwaggerApisUsingDELETE': angular.extend({
                        method: 'DELETE',
                        url: apiUrl + '/api/swagger-apis/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('swaggerApisResource', 'deleteSwaggerApisUsingDELETE')),
                });
            }]};
        }])
        .provider('TagResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'findAllUsingGET11': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/tags',
                        isArray: true,
                    }, $resourceActionConfig('tagResource', 'findAllUsingGET11')),
                    'createUsingPOST12': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/tags',
                    }, $resourceActionConfig('tagResource', 'createUsingPOST12')),
                    'findByIdUsingGET12': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/tags/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('tagResource', 'findByIdUsingGET12')),
                    'deleteUsingDELETE12': angular.extend({
                        method: 'DELETE',
                        url: apiUrl + '/api/tags/:id',
                        params: {
                            'id': '@id',
                        },
                    }, $resourceActionConfig('tagResource', 'deleteUsingDELETE12')),
                    'findTagsFromAWorkspaceUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/workspaces/:workspaceId/tags',
                        params: {
                            'workspaceId': '@workspaceId',
                        },
                        isArray: true,
                    }, $resourceActionConfig('tagResource', 'findTagsFromAWorkspaceUsingGET')),
                    'createUsingPOST13': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/workspaces/:workspaceId/tags',
                        params: {
                            'workspaceId': '@workspaceId',
                        },
                    }, $resourceActionConfig('tagResource', 'createUsingPOST13')),
                    'updateUsingPUT13': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/workspaces/:workspaceId/tags/:id',
                        params: {
                            'workspaceId': '@workspaceId',
                            'id': '@id',
                        },
                    }, $resourceActionConfig('tagResource', 'updateUsingPUT13')),
                    'findNodesByTagUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/workspaces/:workspaceId/tags/:tagId/nodes',
                        params: {
                            'workspaceId': '@workspaceId',
                            'tagId': '@tagId',
                        },
                        isArray: true,
                    }, $resourceActionConfig('tagResource', 'findNodesByTagUsingGET')),
                });
            }]};
        }])
        .provider('TestSuitResource', [function () {
            return {
                $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                    return $resource(null, null, {
                        'getAllTestSuitsUsingGET': angular.extend({
                            method: 'GET',
                            url: apiUrl + '/api/test-suits',
                            isArray: true,
                        }, $resourceActionConfig('testSuitResource', 'getAllTestSuitsUsingGET')),
                        'createTestSuitUsingPOST': angular.extend({
                            method: 'POST',
                            url: apiUrl + '/api/test-suits',
                        }, $resourceActionConfig('testSuitResource', 'createTestSuitUsingPOST')),
                        'updateTestSuitUsingPUT': angular.extend({
                            method: 'PUT',
                            url: apiUrl + '/api/test-suits',
                        }, $resourceActionConfig('testSuitResource', 'updateTestSuitUsingPUT')),
                        'getTestSuitUsingGET': angular.extend({
                            method: 'GET',
                            url: apiUrl + '/api/test-suits/:id',
                            params: {
                                'id': '@id',
                            },
                        }, $resourceActionConfig('testSuitResource', 'getTestSuitUsingGET')),
                        'deleteTestSuitUsingDELETE': angular.extend({
                            method: 'DELETE',
                            url: apiUrl + '/api/test-suits/:id',
                            params: {
                                'id': '@id',
                            },
                        }, $resourceActionConfig('testSuitResource', 'deleteTestSuitUsingDELETE')),
                    });
                }]
            };
        }])
        .provider('WorkspaceResource', [function () {
            return {
                $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                    return $resource(null, null, {
                        'findAllUsingGET12': angular.extend({
                            method: 'GET',
                            url: apiUrl + '/api/workspaces',
                            isArray: true,
                        }, $resourceActionConfig('workspaceResource', 'findAllUsingGET12')),
                        'createUsingPOST14': angular.extend({
                            method: 'POST',
                            url: apiUrl + '/api/workspaces',
                        }, $resourceActionConfig('workspaceResource', 'createUsingPOST14')),
                        'findByIdUsingGET13': angular.extend({
                            method: 'GET',
                            url: apiUrl + '/api/workspaces/:id',
                            params: {
                                'id': '@id',
                            },
                        }, $resourceActionConfig('workspaceResource', 'findByIdUsingGET13')),
                        'updateUsingPUT14': angular.extend({
                            method: 'PUT',
                            url: apiUrl + '/api/workspaces/:id',
                            params: {
                                'id': '@id',
                            },
                        }, $resourceActionConfig('workspaceResource', 'updateUsingPUT14')),
                        'deleteUsingDELETE13': angular.extend({
                            method: 'DELETE',
                            url: apiUrl + '/api/workspaces/:id',
                            params: {
                                'id': '@id',
                            },
                        }, $resourceActionConfig('workspaceResource', 'deleteUsingDELETE13')),
                        'exportUsingGET': angular.extend({
                            method: 'GET',
                            url: apiUrl + '/api/workspaces/:id/export',
                            params: {
                                'id': '@id',
                            },
                            isArray: true,
                        }, $resourceActionConfig('workspaceResource', 'exportUsingGET')),
                    });
                }]
            };
        }])
        .provider('ProjectResource', [function () {
            return {
            $get: ['$resource', 'apiUrl', '$resourceActionConfig', function ($resource, apiUrl, $resourceActionConfig) {
                return $resource(null, null, {
                    'findProjectsFromAWorkspaceUsingGET': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/workspaces/:workspaceId/projects',
                        params: {
                            'workspaceId': '@workspaceId',
                        },
                        isArray: true,
                    }, $resourceActionConfig('projectResource', 'findProjectsFromAWorkspaceUsingGET')),
                    'createUsingPOST9': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/workspaces/:workspaceId/projects',
                        params: {
                            'workspaceId': '@workspaceId',
                        },
                    }, $resourceActionConfig('projectResource', 'createUsingPOST9')),
                    'findByIdUsingGET9': angular.extend({
                        method: 'GET',
                        url: apiUrl + '/api/workspaces/:workspaceId/projects/:id',
                        params: {
                            'workspaceId': '@workspaceId',
                            'id': '@id',
                        },
                    }, $resourceActionConfig('projectResource', 'findByIdUsingGET9')),
                    'updateUsingPUT10': angular.extend({
                        method: 'PUT',
                        url: apiUrl + '/api/workspaces/:workspaceId/projects/:id',
                        params: {
                            'workspaceId': '@workspaceId',
                            'id': '@id',
                        },
                    }, $resourceActionConfig('projectResource', 'updateUsingPUT10')),
                    'deleteUsingDELETE9': angular.extend({
                        method: 'DELETE',
                        url: apiUrl + '/api/workspaces/:workspaceId/projects/:id',
                        params: {
                            'workspaceId': '@workspaceId',
                            'id': '@id',
                        },
                    }, $resourceActionConfig('projectResource', 'deleteUsingDELETE9')),
                    'copyUsingPOST1': angular.extend({
                        method: 'POST',
                        url: apiUrl + '/api/workspaces/:workspaceId/projects/:id/copy',
                        params: {
                            'workspaceId': '@workspaceId',
                            'id': '@id',
                        },
                    }, $resourceActionConfig('projectResource', 'copyUsingPOST1')),
                });
            }]};
        }]);

    if (typeof exports !== 'undefined') {
        if (typeof module !== 'undefined' && module.exports) {
            exports = module.exports = moduleName;
        }
        exports = moduleName;
    }
}(angular));
