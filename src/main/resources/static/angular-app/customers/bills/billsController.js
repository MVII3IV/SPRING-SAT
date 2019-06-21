angular.module('app').controller("billsController", ['$scope', '$http', 'billsService', '$q', function ($scope, $http, billsService, $q) {

    $scope.title = "Facturas";
    $scope.billsData = billsService.billsData;

    $scope.years = [2019];
    $scope.months = [
        {"value" : "01", "name" : "ENERO"},
        {"value" : "02", "name" : "FEBRERO"},
        {"value" : "03", "name" : "MARZO"},
        {"value" : "04", "name" : "ABRIL"},
        {"value" : "05", "name" : "MAYO"},
        {"value" : "06", "name" : "JUNIO"},
        {"value" : "07", "name" : "JULIO"},
        {"value" : "08", "name" : "AGOSTO"},
        {"value" : "09", "name" : "SEPTIEMBRE"},
        {"value" : "10", "name" : "OCTUBRE"},
        {"value" : "11", "name" : "NOVIEMBRE"},
        {"value" : "12", "name" : "DICIEMBRE"},
    ];

    /*
    * Initializer
    */
    if(billsService.bills.length === 0){
        $q.all([ $http.get("../bills/external?rfc=" + getCookie('username')) ]).then(function(result){
            billsService.bills = result[0].data;
            $scope.billsData = billsService.orderData();
        });
    }else{
        $scope.billsData = billsService.billsData;
    }



    $scope.filterBillsByDate = function(incomesFlag){

        var bills = [];
        if(incomesFlag)
            bills = billsService.billsData.emitted.bills;
        else
            bills = billsService.billsData.received.bills;

        //first month
        $scope.filteredBills = bills.find(function(bill){
            if(bill.month === $scope.monthIndex)
                return bill;
        });


        $scope.groupsTotal = 0;
        $scope.filteredBills.groups.forEach(function(element){
            element.bills.forEach(function(bill){
                $scope.groupsTotal += bill.total;
            });
        });



    }


    function formatMonth(month){
        month = parseInt(month);
        if(month > 9){
            month = month;
        }else{
            month = "0" + month
        }
        return month;
    }

    $scope.showField = function(){
        if(window.location.href.contains("admin"))
            return true;
        else
            return false;
    }

    $scope.getTotal = function(bills){
        console.log();
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