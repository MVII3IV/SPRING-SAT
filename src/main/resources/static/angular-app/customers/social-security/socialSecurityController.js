angular.module('app').controller("socialSecurityController", ['$scope', '$http', function ($scope, $http) {
    $scope.title = "IMSS";
    $scope.showCustomer = false;
    $scope.showAdmin = true;
    $scope.mode = "Administrador";


    if(getCookie('username') != "admin")
        $scope.showCustomer = true;
        $scope.showAdmin = false;
        $scope.mode = "Cliente";



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

    $http.get("../employees").then(function(result){
        $scope.employees = result.data;
    });

    function getCookie(cname) {
      var name = cname + "=";
      var decodedCookie = decodeURIComponent(document.cookie);
      var ca = decodedCookie.split(';');
      for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
          c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
          return c.substring(name.length, c.length);
        }
      }
      return "";
    }

}]);