(function() {
    'use strict';

    angular
        .module('webuiApp')
        .controller('JobJenkinsController', JobJenkinsController);

    JobJenkinsController.$inject = ['$scope', '$state', 'JobJenkins', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'JenkinsJob'];

    function JobJenkinsController ($scope, $state, JobJenkins, ParseLinks, AlertService, paginationConstants, pagingParams, JenkinsJob) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        $scope.buildJob = function buildJenkinsJob(id){
            JenkinsJob.get({id:id},function(){AlertService.toast("Build Success") });
        }


        loadAll();

        function loadAll () {
            JobJenkins.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.jobJenkins = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
