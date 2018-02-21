angular.module('app').controller("taxSimulatorController", ['$scope', function ($scope) {
    $scope.title = "Simulador de Impuestos";

    $scope.taxes = [
         {
            lowerLimit : 0.01,
            maxLimit: 285.45,
            fixedFee: 0.00,
            aboveInferiorLimit: 1.92
         },
         {
            lowerLimit : 285.46,
            maxLimit: 2422.80,
            fixedFee: 5.55,
            aboveInferiorLimit: 6.40
         },
         {
             lowerLimit : 2422.81,
             maxLimit: 4257.90,
             fixedFee: 142.20,
             aboveInferiorLimit: 10.88
         },
         {
             lowerLimit : 4257.91,
             maxLimit: 4949.55,
             fixedFee: 341.85,
             aboveInferiorLimit: 16.00
         },
         {
              lowerLimit : 4949.56,
              maxLimit: 5925.90,
              fixedFee: 452.55,
              aboveInferiorLimit: 17.92
          },
          {
               lowerLimit : 5925.91	,
               maxLimit: 11951.85,
               fixedFee: 627.60,
               aboveInferiorLimit: 21.36
           }, {
               lowerLimit : 18837.76,
               maxLimit: 35964.30,
               fixedFee: 3534.30,
               aboveInferiorLimit: 30.00
           }, {
               lowerLimit : 35964.31,
               maxLimit: 47952.30,
               fixedFee: 8672.25,
               aboveInferiorLimit: 32.00
           }, {
               lowerLimit : 47952.31,
               maxLimit: 143856.90,
               fixedFee: 12508.35,
               aboveInferiorLimit: 34.00
           }, {
               lowerLimit : 143856.91,
               maxLimit: 'En adelante',
               fixedFee: 45115.95,
               aboveInferiorLimit: 35.00
           }


    ];
}]);







