angular.module('adminApp').controller("filesController", ['$scope', function ($scope) {

    $scope.title = "Archivos";
    $scope.item1 = true;
    $scope.item2 = false;
    $scope.item3 = false;

    $scope.setVisible = function(flag){
        if(flag == 0){
            $scope.item1 = true;
            $scope.item2 = false;
            $scope.item3 = false;
        }
        if(flag == 1){
            $scope.item1 = false;
            $scope.item2 = true;
            $scope.item3 = false;
        }
        if(flag == 2){
            $scope.item1 = false;
            $scope.item2 = false;
            $scope.item3 = true;
        }

    }

}]);