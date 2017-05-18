(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('ServerJenkinsDetailController', ServerJenkinsDetailController);

    ServerJenkinsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ServerJenkins'];

    function ServerJenkinsDetailController($scope, $rootScope, $stateParams, previousState, entity, ServerJenkins) {
        var vm = this;

        vm.serverJenkins = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('demoApp:serverJenkinsUpdate', function(event, result) {
            vm.serverJenkins = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
