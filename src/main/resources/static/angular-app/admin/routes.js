angular.module("adminApp")
    .config(['$routeProvider', function ($routeProvider) {

        'use strict';

        $routeProvider

            .when('/', {
                templateUrl: '../angular-app/admin/home/home.html',
                controller: 'adminController'
            })
            .when('/inbox', {
                templateUrl: '../angular-app/admin/inbox/inbox.html',
                controller: 'inboxController'
            })
            .when('/declarations', {
                templateUrl: '../angular-app/admin/declarations/declarations.html',
                controller: 'declarationsController'
            })
            .when('/paysheets', {
                templateUrl: '../angular-app/admin/paysheets/paysheets.html',
                controller: 'paysheetsController'
            })
            .when('/documentation', {
                templateUrl: '../angular-app/admin/documentation/documentation.html',
                controller: 'documentationController'
            })

            .otherwise('/');
    }]);