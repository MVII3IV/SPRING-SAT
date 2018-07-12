angular.module('adminApp').controller("declarationsController", ['$scope', function ($scope) {

    $scope.title = "Declaraciones";
    $scope.display = function(){
        $scope.show = true;
    };

}]);