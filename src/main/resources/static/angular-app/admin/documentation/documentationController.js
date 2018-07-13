angular.module('adminApp').controller("documentationController", ['$scope', function ($scope) {

    $scope.title = "Documentacion";
        $scope.display = function(){
            $scope.show = true;
        };

}]);