(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('TestSuitDetailController', TestSuitDetailController);

    TestSuitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TestSuit'];

    function TestSuitDetailController($scope, $rootScope, $stateParams, previousState, entity, TestSuit) {
        var vm = this;

        vm.testSuit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('webuiApp:testSuitUpdate', function(event, result) {
            vm.testSuit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
