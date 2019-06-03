angular.module('app')

.directive('loadFinished', function() {
      return function(scope, element, attrs) {
        if (scope.$last){
            $('body').css('height', $('.dev-page-content').prop('scrollHeight') + 20);
        }
      };
    })

.controller("outgoingsController", ['$scope', '$http', 'userService', function ($scope, $http, userService) {
        $scope.title = "Gastos";
        $scope.grandTotal = 0;

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


        $http.get("../bills/external?rfc=" + userService.data.common).then(function mySuccess(response) {
            $scope.bills = response.data;
        }, function myError(response) {
        $scope.myWelcome = response.statusText;
        });


        $scope.getTotal = function(value){
            var total = 0;
            value.forEach(function(iteratedElement){
                total += Number(iteratedElement.total.replace(/[^0-9\.-]+/g,""))
            });
            $scope.grandTotal += total;
            return total;
        }
}]);