(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('ServerJenkinsDeleteController',ServerJenkinsDeleteController);

    ServerJenkinsDeleteController.$inject = ['$uibModalInstance', 'entity', 'ServerJenkins'];

    function ServerJenkinsDeleteController($uibModalInstance, entity, ServerJenkins) {
        var vm = this;

        vm.serverJenkins = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ServerJenkins.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
