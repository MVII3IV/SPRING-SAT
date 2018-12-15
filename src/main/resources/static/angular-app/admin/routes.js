angular.module("adminApp")
    .config(['$routeProvider',  function ($routeProvider) {

        'use strict';

        $routeProvider
            .when('/', {
                templateUrl: '../angular-app/admin/home/home.html',
                controller: 'adminController'
            })
            .when('/files', {
                templateUrl: '../angular-app/admin/files/files.html',
                controller: 'filesController'
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
            .when('/users', {
                templateUrl: '../angular-app/admin/users/users.html',
                controller: 'usersController'
            })
            .when('/customers', {
                templateUrl: '../angular-app/admin/customers/customers.html',
                controller: 'customersController'
            })




            .when('/landing-page', {
                templateUrl: '../angular-app/customers/home/home.html',
                controller: 'mainController'
            })
            .when('/income', {
                templateUrl: '../angular-app/customers/income/income.html',
                controller: 'incomeController'
            })
            .when('/outgoings', {
                templateUrl: '../angular-app/customers/outgoings/outgoings.html',
                controller: 'outgoingsController'
            })
            .when('/tax-simulator', {
                templateUrl: '../angular-app/customers/tax-simulator/tax-simulator.html',
                controller: 'taxSimulatorController'
            })
            .when('/procedures', {
                templateUrl: '../angular-app/customers/procedures/procedures.html',
                controller: 'proceduresController'
            })
            .when('/declarations', {
                templateUrl: '../angular-app/customers/declarations/declarations.html',
                controller: 'declarationsController'
            })
            .when('/bills', {
                templateUrl: '../angular-app/customers/bills/bills.html',
                controller: 'billsController'
            })
            .when('/paysheets', {
                templateUrl: '../angular-app/customers/paysheets/paysheets.html',
                controller: 'paysheetsController'
            })
            .when('/social-security', {
                templateUrl: '../angular-app/customers/social-security/socialsecurity.html',
                controller: 'socialSecurityController'
            })
            .when('/payments', {
                templateUrl: '../angular-app/customers/payments/payments.html',
                controller: 'paymentsController'
            })
            .when('/help', {
                templateUrl: '../angular-app/customers/help/help.html',
                controller: 'helpController'
            })




            .otherwise('/');
    }]);