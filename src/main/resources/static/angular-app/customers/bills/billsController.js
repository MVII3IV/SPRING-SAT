angular.module('app').controller("billsController", ['$scope', function ($scope) {
    $scope.title = "Facturas";
    $scope.showField = function(){
        if(window.location.href.contains("admin"))
            return true;
        else
            return false;
    }
}]);