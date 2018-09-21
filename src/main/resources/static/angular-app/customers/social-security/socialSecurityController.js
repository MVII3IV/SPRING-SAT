angular.module('app').controller("socialSecurityController", ['$scope', function ($scope) {
    $scope.title = "IMSS";
    $scope.showCustomer = false;
    $scope.showAdmin = true;
    $scope.mode = "Administrador";

    $scope.setViewMode = function(checkbox){
        if(checkbox){
            $scope.showCustomer = true;
            $scope.showAdmin = false;
            $scope.mode = "Cliente";
        }else{
            $scope.showCustomer = false;
            $scope.showAdmin = true;
            $scope.mode = "Administrador";
        }
    }

    $scope.fileChooser = function(){
        $('#file-input').trigger('click');
    }

}]);