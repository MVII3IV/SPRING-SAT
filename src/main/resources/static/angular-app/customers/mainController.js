angular.module('app').controller("mainController", ['$scope', '$http', '$timeout', 'menuService', 'userService', 'billsService',
function ($scope, $http,$timeout, menuService, userService, billsService) {

    $scope.title = "Ingresos y Gastos";
    $scope.incomesByMonth01 = 0;
    $scope.outcomesByMonth01 = 0;
    $scope.incomesByMonth02 = 0;
    $scope.outcomesByMonth02 = 0;
    $scope.totalToPay = 0;
    $scope.billsData = [];

    $scope.years = [2019];
    $scope.months = [
        {"value" : 01, "name" : "ENERO-FEBRERO"},
        {"value" : 03, "name" : "MARZO-ABRIL"},
        {"value" : 05, "name" : "MAYO-JUNIO"},
        {"value" : 07, "name" : "JULIO-AGOSTO"},
        {"value" : 09, "name" : "SEPTIEMBRE-OCTUBRE"},
        {"value" : 11, "name" : "NOVIEMBRE-DICIEMBRE"}
    ];

    /*
    * Menu element object
    * Contains all the properties need to the proper functionality
    */
    $scope.menuElements = menuService;


    /*
    *   Gets user's info from the local database
    */
    $http.get("../user/").then(function mySuccess(response) {
        userService.data = response.data;
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });


    /*
    *   This sends the cookie value "username" which is the rfc for the user
    *   then filtering by this field goes for the bills assigned to the user
    *   the bills are then stored in the billsService.bills
    */
    $http.get("../bills/external?rfc=" + getCookie('username')).then(function mySuccess(response) {
        billsService.bills = response.data;
        $scope.billsData = billsService.orderData();

        $scope.incomes = [];
        $scope.outcomes = [];
        for(var i = 0 ; i < $scope.billsData.emitted.length ; i++){
            $scope.incomes.push(($scope.billsData.emitted[i].total.emitted).toFixed(2));
            $scope.outcomes.push(($scope.billsData.emitted[i].total.received).toFixed(2));
        }








            new Chart(document.getElementById("line-chart"), {
              type: 'line',
              data: {
                labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
                datasets: [{
                    data: $scope.incomes,
                    label: "Ingresos",
                    borderColor: "#82b440",
                    fill: false
                  }, {
                    data: $scope.outcomes,
                    label: "Gastos",
                    borderColor: "#e74e40",
                    fill: false
                  }
                ]
              },
              options: {
                title: {
                  display: false,
                  text: 'Tabla de Ingresos y Gastos'
                }
              }
            });













    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });


    $scope.filterBillsByDate = function(){
        $scope.incomesByMonth01 = 0;
        $scope.outcomesByMonth01 = 0;

        $scope.incomesByMonth02 = 0;
        $scope.outcomesByMonth02 = 0;

        var monthIndex = formatMonth($scope.monthIndex);
        $scope.nextMonth = getNextMonth(monthIndex);


        //first month
        billsService.billsData.emitted.find(function(bill){
            if(bill.month === monthIndex)
                $scope.incomesByMonth01 = bill.total.emitted;

            if(bill.month === $scope.nextMonth)
                $scope.incomesByMonth02 = bill.total.emitted;
        });

        billsService.billsData.received.find(function(bill){
            if(bill.month === monthIndex)
                $scope.outcomesByMonth01 =  bill.total.received;

            if(bill.month === $scope.nextMonth)
                $scope.outcomesByMonth02 =  bill.total.received;
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


    function getNextMonth(month){
        month = parseInt(month);
        if(month > 9){
            month = month + 1;
        }else{
            month = "0" + (month + 1)
        }
        return month;
    }


    $scope.fileChooser = function(component){
        $('#file-input').trigger('click');
        $('#' + component).attr('class', 'btn btn-success');
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