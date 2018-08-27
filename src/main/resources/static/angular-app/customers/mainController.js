angular.module('app').controller("mainController", ['$scope', '$timeout', 'menuService', function ($scope, $timeout, menuService) {

    /*
    * Menu element object
    * Contains all the properties need to the proper functionality
    */
    $scope.menuElements = menuService;


    $scope.title = "Ingresos y Gastos";

}]);