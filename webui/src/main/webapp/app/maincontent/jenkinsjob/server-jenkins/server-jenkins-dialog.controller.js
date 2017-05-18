(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('ServerJenkinsDialogController', ServerJenkinsDialogController);

    ServerJenkinsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ServerJenkins', 'Principal', 'User'];

    function ServerJenkinsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ServerJenkins, Principal, User) {
        var vm = this;

        vm.users = [];
        vm.account = null;
        vm.serverJenkins = entity;
        vm.clear = clear;
        vm.save = save;

        getAccount();
        loadAllUsers();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            vm.serverJenkins.userId = $scope.getUserByLogin().id;
            vm.serverJenkins.userName = $scope.getUserByLogin().login;
            if (vm.serverJenkins.id !== null) {
                ServerJenkins.update(vm.serverJenkins, onSaveSuccess, onSaveError);
            } else {
                ServerJenkins.save(vm.serverJenkins, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('demoApp:serverJenkinsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function loadAllUsers() {
            User.query(function(result) {
                vm.users = result;
            });
        }

        $scope.getUserByLogin = function () {
            var userLogin = {};
            var continueFlag = true;
            angular.forEach(vm.users, function(user){
                if(vm.account.login == user.login && continueFlag){
                    userLogin = user;
                    continueFlag = false;
                }
            });
            return userLogin;
        }
    }
})();
