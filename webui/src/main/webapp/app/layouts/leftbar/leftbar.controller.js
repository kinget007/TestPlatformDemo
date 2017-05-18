(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('LeftbarController', LeftbarController);

    LeftbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function LeftbarController ($state, Auth, Principal, ProfileService, LoginService) {
        var vm = this;

        vm.isLeftbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        // vm.login = login;
        // vm.logout = logout;
        vm.toggleLeftbar = toggleLeftbar;
        vm.collapseLeftbar = collapseLeftbar;
        vm.$state = $state;

        // function login() {
        //     collapseNavbar();
        //     LoginService.open();
        // }

        // function logout() {
        //     collapseNavbar();
        //     Auth.logout();
        //     $state.go('home');
        // }

        function toggleLeftbar() {
            vm.isLeftbarCollapsed = !vm.isLeftbarCollapsed;
        }

        function collapseLeftbar() {
            vm.isLeftbarCollapsed = true;
        }
    }
})();
