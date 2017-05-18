(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('TestSuitDialogController', TestSuitDialogController);

    TestSuitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TestSuit'];

    function TestSuitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TestSuit) {
        var vm = this;

        vm.testSuit = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.testSuit.id !== null) {
                TestSuit.update(vm.testSuit, onSaveSuccess, onSaveError);
            } else {
                TestSuit.save(vm.testSuit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('webuiApp:testSuitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
