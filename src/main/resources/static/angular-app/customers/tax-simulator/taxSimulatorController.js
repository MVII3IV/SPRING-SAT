angular.module('app').controller("taxSimulatorController", ['$scope', function ($scope) {
    $scope.title = "Simulador de Impuestos";

    $scope.taxes = [
         {
            lowerLimit : 0.01,
            maxLimit: 578.52,
            fixedFee: 0,
            aboveInferiorLimit: 1.92
         },
         {
            lowerLimit : 578.53,
            maxLimit: 4910.18,
            fixedFee: 11.11,
            aboveInferiorLimit: 6.4
         },
         {
             lowerLimit : 4910.19,
             maxLimit: 8629.20,
             fixedFee: 288.33,
             aboveInferiorLimit: 10.88
         },
         {
             lowerLimit : 8629.21,
             maxLimit: 10031.07,
             fixedFee: 692.96,
             aboveInferiorLimit: 16
         },
         {
              lowerLimit : 10031.08,
              maxLimit: 12009.94,
              fixedFee: 917.26,
              aboveInferiorLimit: 17.92
          },
          {
               lowerLimit : 12009.95,
               maxLimit: 24222.31,
               fixedFee: 1271.84,
               aboveInferiorLimit: 21.36
           }, {
               lowerLimit : 24222.32,
               maxLimit: 38177.69,
               fixedFee: 3880.44,
               aboveInferiorLimit: 23.52
           }, {
               lowerLimit : 38177.70,
               maxLimit: 72887.50,
               fixedFee: 7162.74,
               aboveInferiorLimit: 30
           }, {
               lowerLimit : 72887.51,
               maxLimit: 97183.33,
               fixedFee: 17575.69,
               aboveInferiorLimit: 32
           }, {
               lowerLimit : 97183.34,
               maxLimit: 291550,
               fixedFee: 25350.35,
               aboveInferiorLimit: 34
           },
           , {
              lowerLimit : 291550.01,
              maxLimit: 0,
              fixedFee: 91435.02,
              aboveInferiorLimit: 35
          }
    ];

    $scope.feesToPay = 0;
    $scope.outcomes = 0;
    $scope.incomes = 0;
    $scope.incomesBeforeFees = 0;
    $scope.fees = 0;

    $scope.calculteFees = function(){

        $scope.incomesBeforeFees = $scope.incomes - $scope.outcomes;

        if($scope.incomesBeforeFees > 0){
            var done = false;
            $scope.taxes.forEach(function(fee){
                if($scope.incomes >= fee.lowerLimit && $scope.incomes <= fee.maxLimit){
                    $scope.fees = (($scope.incomesBeforeFees - fee.lowerLimit) * (fee.aboveInferiorLimit / 100)) + fee.fixedFee;
                    done = true;
                }else if(fee.maxLimit == 0 && !done){
                    $scope.fees = (($scope.incomesBeforeFees - fee.lowerLimit) * (.35) + fee.fixedFee);
                }
            });
        }else{
            $scope.fees = 0;
        }







    }


}]);







