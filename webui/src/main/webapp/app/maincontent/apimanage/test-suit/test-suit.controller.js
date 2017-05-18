(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('TestSuitController', TestSuitController);

    TestSuitController.$inject = ['$scope', '$state', 'TestSuit'];

    function TestSuitController ($scope, $state, TestSuit) {
        var vm = this;

        vm.testSuits = [];

        loadAll();

        function loadAll() {
            TestSuit.query(function(result) {
                vm.testSuits = result;
                vm.searchQuery = null;
            });
        }
    }
})();
