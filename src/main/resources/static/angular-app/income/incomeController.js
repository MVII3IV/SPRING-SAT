angular.module('app')

.directive('loadFinished', function() {
      return function(scope, element, attrs) {
        if (scope.$last){
            $('body').css('height', $('.dev-page-content').prop('scrollHeight') + 20);
        }
      };
    })

.controller("incomeController", ['$scope', '$http', function ($scope, $http) {
    $scope.title = "Ingresos";

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
            },
            {
                "fiscalId": "AB930177-609A-4FBB-AA5E-5A730BC712AA",
                "emisorRFC": "LULR860821MTA",
                "emisorName": "ROSA IVET LUNA LOPEZ",
                "receiverRFC": "ARD631206SX5",
                "receiverName": "AUTOTRANSPORTES RAPIDOS DELICIAS SA DE CV",
                "emitedDate": "2017-01-05T12:02:46",
                "certificationDate": "2017-01-05T12:02:47",
                "certifiedPAC": "CCC1007293K0",
                "total": "$15,405.99",
                "voucherEffect": "Ingreso",
                "voucherStatus": "Vigente"
            },
            {
                "fiscalId": "CBC2A62E-F664-45EA-826B-3D236D658F87",
                "emisorRFC": "LULR860821MTA",
                "emisorName": "ROSA IVET LUNA LOPEZ",
                "receiverRFC": "BDE860408EF0",
                "receiverName": "BRASA DESARROLLOS SA DE CV",
                "emitedDate": "2017-01-05T12:10:32",
                "certificationDate": "2017-01-05T12:10:32",
                "certifiedPAC": "CCC1007293K0",
                "total": "$7,759.14",
                "voucherEffect": "Ingreso",
                "voucherStatus": "Vigente"
            }
    ];


    $http.get("../incomes/").then(function mySuccess(response) {
        $scope.bills = response.data;

    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });


    $scope.players = [
      {name: 'Gene', team: 'alpha'},
      {name: 'George', team: 'beta'},
      {name: 'Steve', team: 'gamma'},
      {name: 'Paula', team: 'beta'},
      {name: 'Scruath', team: 'gamma'}
    ];

}]);

