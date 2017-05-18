(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('TestSuitDeleteController',TestSuitDeleteController);

    TestSuitDeleteController.$inject = ['$uibModalInstance', 'entity', 'TestSuit'];

    function TestSuitDeleteController($uibModalInstance, entity, TestSuit) {
        var vm = this;

        vm.testSuit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TestSuit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
