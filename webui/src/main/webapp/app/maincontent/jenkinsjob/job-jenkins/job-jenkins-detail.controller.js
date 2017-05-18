(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('JobJenkinsDetailController', JobJenkinsDetailController);

    JobJenkinsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'JobJenkins'];

    function JobJenkinsDetailController($scope, $rootScope, $stateParams, previousState, entity, JobJenkins) {
        var vm = this;

        vm.jobJenkins = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('demoApp:jobJenkinsUpdate', function(event, result) {
            vm.jobJenkins = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
