(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('JobJenkinsDeleteController',JobJenkinsDeleteController);

    JobJenkinsDeleteController.$inject = ['$uibModalInstance', 'entity', 'JobJenkins'];

    function JobJenkinsDeleteController($uibModalInstance, entity, JobJenkins) {
        var vm = this;

        vm.jobJenkins = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            JobJenkins.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
