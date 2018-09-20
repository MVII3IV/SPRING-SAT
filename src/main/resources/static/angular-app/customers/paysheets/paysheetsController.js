angular.module('app').controller("paysheetsController", ['$scope', 'userService', function ($scope, userService) {
    $scope.title = "Nominas";

    $scope.showField = function(){
        if(window.location.href.contains("admin"))
            return true;
        else
            return false;
    }
}]);