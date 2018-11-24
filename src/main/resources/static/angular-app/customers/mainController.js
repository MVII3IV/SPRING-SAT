angular.module('app').controller("mainController", ['$scope', '$http', '$timeout', 'menuService', 'userService', function ($scope, $http,$timeout, menuService,userService) {

    /*
    * Menu element object
    * Contains all the properties need to the proper functionality
    */
    $scope.menuElements = menuService;

    $http.get("../user/").then(function mySuccess(response) {
        userService.data = response.data;
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });

    $scope.fileChooser = function(component){
        $('#file-input').trigger('click');
        $('#' + component).attr('class', 'btn btn-success');
    }

    $scope.title = "Ingresos y Gastos";

}]);