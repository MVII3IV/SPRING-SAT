angular.module('app')

.directive('loadFinished', function() {
      return function(scope, element, attrs) {
        if (scope.$last){
            $('body').css('height', $('.dev-page-content').prop('scrollHeight') + 20);
        }
      };
    })

/*
    .filter('myDateFormat', function myDateFormat($filter){
      return function(text){
        var  tempdate= new Date(text.replace(/-/g,"/"));
        return $filter('date')(tempdate, "MMM-dd-yyyy");
      }
    })*/

.controller("incomeController", ['$scope', '$http', 'userService', function ($scope, $http, userService) {
    $scope.title = "Ingresos";
    $scope.grandTotal = 0;
    $scope.paidBills = [];
    $scope.currentBill = {};
    $scope.selectDate;

    $scope.bills = [
        {
            "fiscalId": "Folio Fiscal",
             "emisorRFC": "RFC Emisor",
             "emisorName": "Nombre o Razón Social del Emisor",
             "receiverRFC": "RFC Receptor",
             "receiverName": "Nombre o Razón Social del Receptor",
             "emitedDate": "Fecha de Emisión",
             "certificationDate": "Fecha de Certificación",
             "certifiedPAC": "PAC que Certificó",
             "total": "Total",
             "voucherEffect": "Efecto del Comprobante",
             "voucherStatus": "Estado del Comprobante"
         }
    ];


    $http.get("../incomes/?userRFC=" + userService.data.common).then(function mySuccess(response) {
        $scope.bills = response.data;
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });


    $scope.noPaid = function(bill, event){
        if(event.target.checked){
            $scope.paidBills.push(bill);
            $("#" + bill.fiscalId).attr("style","background-color:#ff3c3c");
            $("#" + bill.fiscalId + " > td").attr("style", "color:white");
        }
        else{
            $("#" + bill.fiscalId).removeAttr("style");
            $("#" + bill.fiscalId + " > td").removeAttr("style");
            $scope.paidBills = [];
        }

    }

    $scope.toPaid = function(bill){
        $scope.currentBill = bill;
    }

    $scope.registerBill = function(){
        $("#" + $scope.currentBill.fiscalId).removeAttr("style");
        $("#" + $scope.currentBill.fiscalId + " > td").removeAttr("style");
        $('#check_' + $scope.currentBill.fiscalId ).remove();
        $scope.paidBills = [];
    }

    $("#datetimepicker").on("dp.change", function (e) {
         $scope.selectDate = $("#selectDate").val();
         console.log($scope.selectDate);
    });

    $scope.getTotal = function(value){
    /*
        var total = 0;
        value.forEach(function(iteratedElement){
            total += Number(iteratedElement.total.replace(/[^0-9\.-]+/g,""))
        });
        $scope.grandTotal += total;
        return total;*/
    }
}]);

