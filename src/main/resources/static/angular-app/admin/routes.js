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

            .otherwise('/');
    }]);