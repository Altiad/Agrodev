(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('production', {
            parent: 'entity',
            url: '/production',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.production.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/production/productions.html',
                    controller: 'ProductionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('production');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('production-detail', {
            parent: 'production',
            url: '/production/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.production.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/production/production-detail.html',
                    controller: 'ProductionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('production');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Production', function($stateParams, Production) {
                    return Production.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'production',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('production-detail.edit', {
            parent: 'production-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/production/production-dialog.html',
                    controller: 'ProductionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Production', function(Production) {
                            return Production.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('production.new', {
            parent: 'production',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/production/production-dialog.html',
                    controller: 'ProductionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dateofproduction: null,
                                quantity: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('production', null, { reload: 'production' });
                }, function() {
                    $state.go('production');
                });
            }]
        })
        .state('production.edit', {
            parent: 'production',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/production/production-dialog.html',
                    controller: 'ProductionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Production', function(Production) {
                            return Production.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('production', null, { reload: 'production' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('production.delete', {
            parent: 'production',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/production/production-delete-dialog.html',
                    controller: 'ProductionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Production', function(Production) {
                            return Production.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('production', null, { reload: 'production' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
