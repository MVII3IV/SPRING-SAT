angular.module("app")
    .config(['$routeProvider', function ($routeProvider) {

        'use strict';

        $routeProvider

            .when('/landing-page', {
                templateUrl: 'angular-app/home/home.html',
                controller: 'mainController'
            })
            .when('/income', {
                templateUrl: 'angular-app/income/income.html',
                controller: 'incomeController'
            })
            .when('/outgoings', {
                templateUrl: 'angular-app/outgoings/outgoings.html',
                controller: 'outgoingsController'
            })
            .when('/tax-simulator', {
                templateUrl: 'angular-app/tax-simulator/tax-simulator.html',
                controller: 'taxSimulatorController'
            })
            .when('/declarations', {
                templateUrl: 'angular-app/declarations/declarations.html',
                controller: 'declarationsController'
            })
            .when('/bills', {
                templateUrl: 'angular-app/bills/bills.html',
                controller: 'billsController'
            })
            .when('/paysheets', {
                templateUrl: 'angular-app/paysheets/paysheets.html',
                controller: 'paysheetsController'
            })
            .when('/social-security', {
                templateUrl: 'angular-app/social-security/socialsecurity.html',
                controller: 'socialSecurityController'
            })
            .when('/payments', {
                templateUrl: 'angular-app/payments/payments.html',
                controller: 'paymentsController'
            })
            .when('/help', {
                templateUrl: 'angular-app/help/help.html',
                controller: 'helpController'
            })
            .otherwise('/landing-page');
    }]);