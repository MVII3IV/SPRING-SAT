angular.module('app').controller("mainController", ['$scope', '$http', '$timeout', 'menuService', 'userService', 'billsService',
function ($scope, $http,$timeout, menuService, userService, billsService) {

    $scope.title = "Ingresos y Gastos";
    $scope.incomesByMonth01 = 0;
    $scope.outcomesByMonth01 = 0;
    $scope.incomesByMonth02 = 0;
    $scope.outcomesByMonth02 = 0;
    $scope.totalToPay = 0;


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
        billsService.orderData();
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });


    $scope.filterBillsByDate = function(year, month){
        $scope.incomesByMonth01 = 0;
        $scope.outcomesByMonth01 = 0;

        $scope.incomesByMonth02 = 0;
        $scope.outcomesByMonth02 = 0;

        var nextMonth = getNextMonth(month);

        //first month
        billsService.billsData.emitted.find(function(bill){
            if(bill.month === month)
                $scope.incomesByMonth01 = bill.total;
        });

        billsService.billsData.received.find(function(bill){
            if(bill.month === month)
                $scope.outcomesByMonth01 =  bill.total;
        });

        //second month
        billsService.billsData.emitted.find(function(bill){
            if(bill.month === month)
                $scope.incomesByMonth01 = bill.total;
        });

        billsService.billsData.received.find(function(bill){
            if(bill.month === month)
                $scope.outcomesByMonth01 =  bill.total;
        });

    }

    function getNextMonth(month){
        month = parseInt(month);
        if(month > 9){
            month = month + 1;
        }else{
            month = "0" + (month + 1)
        }
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