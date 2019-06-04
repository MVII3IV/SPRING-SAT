angular.module('app').controller("mainController", ['$scope', '$http', '$timeout', 'menuService', 'userService', 'billsService',
function ($scope, $http,$timeout, menuService, userService, billsService) {

    $scope.title = "Ingresos y Gastos";
    $scope.incomesByMonth = 0;
    $scope.outcomesByMonth = 0;


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
        billsService.orderData(billsService.bills);
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });


    $scope.filterBillsByDate = function(year, month){

        $scope.incomesByMonth = 0;
        $scope.outcomesByMonth = 0;

        var billsFilteredByDate = [];

        for(var i = 0; i < billsService.bills.length ; i++){
            if(billsService.bills[i].emitedDate.contains('/' + month + '/' + year)){
                billsFilteredByDate.push(billsService.bills[i]);
            }
        }

        //bills filtered and emitted
        for(var i = 0; i < billsFilteredByDate.length ; i++){
            if(billsFilteredByDate[i].emited )
                $scope.incomesByMonth +=  parseInt(billsFilteredByDate[i].total.replace('$','').replace(',',''), 10);
        }

        //bills filtered and received
        for(var i = 0; i < billsFilteredByDate.length ; i++){
            if(!billsFilteredByDate[i].emited )
                $scope.outcomesByMonth +=  parseInt(billsFilteredByDate[i].total.replace('$','').replace(',',''), 10);
        }


        //$scope.incomesByMonth;
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