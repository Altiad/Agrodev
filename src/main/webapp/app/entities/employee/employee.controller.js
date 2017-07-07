(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmployeeController', EmployeeController);

    EmployeeController.$inject = ['Employee', 'EmployeeSearch'];

    function EmployeeController(Employee, EmployeeSearch) {

        var vm = this;

        vm.employees = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Employee.query(function(result) {
                vm.employees = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            EmployeeSearch.query({query: vm.searchQuery}, function(result) {
                vm.employees = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
