angular.module('app').controller("mainController", ['$scope', '$timeout', 'menuService', function ($scope, $timeout, menuService) {

    /*
    * Menu element object
    * Contains all the properties need to the proper functionality
    */
    $scope.menuElements = menuService;


    $scope.title = "Ingresos y Gastos";
    $scope.icomeIn01 = 1000;
    $scope.icomeOut = 600;
    $scope.$broadcast('timer-start');

    $scope.runTimer = function(value){
        while(value < 5000){
        $timeout(1000);
        value++;
        }

    }

}]);