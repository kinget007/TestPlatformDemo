(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('JobJenkinsDialogController', JobJenkinsDialogController);

    JobJenkinsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'JobJenkins', 'ServerJenkins'];

    function JobJenkinsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, JobJenkins, ServerJenkins) {
        var vm = this;

        vm.jobJenkins = entity;
        vm.serverJenkins = [];
        vm.clear = clear;
        vm.save = save;

        loadServerJenkins();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.jobJenkins.id !== null) {
                JobJenkins.update(vm.jobJenkins, onSaveSuccess, onSaveError);
            } else {
                JobJenkins.save(vm.jobJenkins, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('demoApp:jobJenkinsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function loadServerJenkins(){
            ServerJenkins.query(function(result) {
                vm.serverJenkins = result;
            });
        }
    }
})();
