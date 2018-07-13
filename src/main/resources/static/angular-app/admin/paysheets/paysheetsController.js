angular.module('adminApp').controller("paysheetsController", ['$scope', function ($scope) {
    $scope.title = "Nominas";
    $scope.display = function(){
        $scope.show = true;
    };

}]);