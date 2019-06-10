angular.module('app').controller("billsController", ['$scope', '$http', 'billsService', '$q', function ($scope, $http, billsService, $q) {

    $scope.title = "Facturas";
    $scope.billsData = billsService.billsData;

    if(billsService.bills.length === 0){
        $q.all([ $http.get("../bills/external?rfc=" + getCookie('username')) ]).then(function(result){
            billsService.bills = result[0].data;
            $scope.billsData = billsService.orderData();
        });
    }else{
        $scope.billsData = billsService.billsData;
    }

    $scope.showField = function(){
            if(window.location.href.contains("admin"))
                return true;
            else
                return false;
        }

    /*
    *   Simple code to get the cookie value
    */
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