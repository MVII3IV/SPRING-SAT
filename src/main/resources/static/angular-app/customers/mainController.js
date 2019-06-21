angular.module('app').controller("mainController", ['$scope', '$http', '$timeout', 'menuService', 'userService', 'billsService', '$q',
function ($scope, $http,$timeout, menuService, userService, billsService, $q) {

    $scope.title = "Ingresos y Gastos";
    $scope.incomesByMonth01 = 0;
    $scope.outcomesByMonth01 = 0;
    $scope.incomesByMonth02 = 0;
    $scope.outcomesByMonth02 = 0;
    $scope.totalToPay = 0;
    $scope.billsData = [];

    $scope.years = [2019];
    $scope.monthsBimester = [
        {"value" : 01, "name" : "ENERO-FEBRERO"},
        {"value" : 03, "name" : "MARZO-ABRIL"},
        {"value" : 05, "name" : "MAYO-JUNIO"},
        {"value" : 07, "name" : "JULIO-AGOSTO"},
        {"value" : 09, "name" : "SEPTIEMBRE-OCTUBRE"},
        {"value" : 11, "name" : "NOVIEMBRE-DICIEMBRE"}
    ];

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
    *   This sends the cookie value "username" which is the rfc for the user
    *   then filtering by this field goes for the bills assigned to the user
    *   the bills are then stored in the billsService.bills
    */
    var setGraph = function(){
        $scope.incomes = [];
        $scope.outcomes = [];
        for(var i = 0 ; i < $scope.billsData.emitted.bills.length ; i++){
            $scope.incomes.push(($scope.billsData.emitted.bills[i].total.emitted).toFixed(2));
            $scope.outcomes.push(($scope.billsData.emitted.bills[i].total.received).toFixed(2));
        }

        var chart = document.getElementById("line-chart");

        if(chart == null)
            return;

            new Chart(chart, {
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


    }




    $q.all([ $http.get("../bills/external?rfc=" + getCookie('username')) ]).then(function(result){
        billsService.bills = result[0].data;
        $scope.billsData = billsService.orderData();
        setGraph();
    });


    /*
    * Menu element object
    * Contains all the properties need to the proper functionality
    */
    $scope.menuElements = menuService;


    /*
    *   Gets user's info from the local database
    */
    $http.get("../user/").then(function mySuccess(response) {
        userService.data = response.data[0];
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
        billsService.billsData.emitted.bills.find(function(bill){
            if(bill.month === monthIndex)
                $scope.incomesByMonth01 = bill.total.emitted;

            if(bill.month === $scope.nextMonth)
                $scope.incomesByMonth02 = bill.total.emitted;
        });

        billsService.billsData.received.bills.find(function(bill){
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


    $scope.isRifUser = function(){
        if(userService.data.taxRegime === "RIF")
            return true;
        else
            return false;
    }


}]);