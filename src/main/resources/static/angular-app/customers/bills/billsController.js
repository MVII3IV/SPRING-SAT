angular.module('app').controller("billsController", ['$scope', '$http', 'billsService',  function ($scope, $http, billsService) {

    $scope.title = "Facturas";

    $scope.bills = billsService.bills;

    $scope.showField = function(){
            if(window.location.href.contains("admin"))
                return true;
            else
                return false;
        }

}]);