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

    $http.get("../employees?bossRfc=" + getCookie('username')).then(function(result){
        $scope.employees = result.data;
    });

    $scope.saveEmployee = function(){

        var data = {
           "name" : $scope.name,
           "lastName" : $scope.lastName,
           "secondLastName" : $scope.secondLastName,
           "securityNumber" : $scope.securityNumber,
           "curp" : $scope.curp,
           "dailySalary" : $scope.dailySalary,
           "bossRfc" : getCookie('username'),
           "statusId" : 2
         }

            /*
         $http.post("../employees/save").then(function(res){
            console.log(res);
         }, function(res){
            console.log(res);
         });
*/

        $http({
            url: 'employees',
            method: "POST",
            data: data,
            //withCredentials: true,
            headers : {
                'Accept': 'application/json',
                'Content-Type' : 'application/json'
                //'Authorization' : 'Basic ' + btoa("LULR860821MTA:goluna21")
            }
        })
        .then(function(response) {
                $scope.employees.push(response.data);
        },
        function(response) { // optional
                console.log(response)
        });
    }

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