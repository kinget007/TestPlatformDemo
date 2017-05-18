(function() {
    'use strict';

    angular
        .module('webuiApp')
        .factory('PasswordResetFinish', PasswordResetFinish);

    PasswordResetFinish.$inject = ['$resource'];

    function PasswordResetFinish($resource) {
        var service = $resource('auth/api/account/reset_password/finish', {}, {});

        return service;
    }
})();
