angular.module('adminApp').controller("billsController", ['$scope', '$http', function ($scope, $http) {
    $scope.title = "Facturas";

    //http://104.248.23.45:8080/crawler/extract/data?rfc=LULR860821MTA&pass=goluna21

    $scope.getData = function(){
        $http.get("/bills?rfc=" + $scope.rfc + "&pass=" + $scope.pass).then(function mySuccess(response) {
            $scope.bills = response.data;
        }, function myError(response) {
            $scope.myWelcome = response.statusText;
        });
    }



}]);
