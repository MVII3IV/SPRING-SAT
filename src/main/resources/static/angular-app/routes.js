angular.module("app").config(
    function ($routeProvider) {

    $routeProvider

        .when('/', {
            templateUrl: 'views/home.html',
            controller: 'mainController'
        })

        .when('/bills', {
            templateUrl: 'pages/devices/devices.html',
            controller: 'deviceController'
        })
});