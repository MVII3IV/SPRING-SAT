angular.module('adminApp')

    .directive('loadFinished', function() {
      return function(scope, element, attrs) {
        if (scope.$last){
            $('body').css('height', $('.dev-page-content').prop('scrollHeight') + 20);
        }
      };
    })

    .controller("billsController", ['$scope', '$http', function ($scope, $http) {

    $scope.title = "Facturas";

    //http://104.248.23.45:8080/crawler/extract/data?rfc=LULR860821MTA&pass=goluna21
    $scope.mode = "Recibidas";

    /*
    $scope.bills =
    [
    	{
    		cancelationProcessDate: "",
    		cancelationProcessStatus: "",
            cancelationStatus: "Cancelable sin aceptación",
            certificationDate: "2019-01-02T09:36:05",
            certifiedPAC: "BBA830831LJ2",
            emisorName: "BBVA BANCOMER, S A",
            emisorRFC: "BBA830831LJ2",
            emited: false,
            emitedDate: "02/01/2019",
            fiscalId: "F53F7578-5D8B-43BF-8C77-0FEF2D80032A",
            receiverName: "ROSA IVET LUNA LOPEZ",
            receiverRFC: "LULR860821MTA",
            total: "$256.36",
            userId: "LULR860821MTA",
            voucherEffect: "Ingreso",
            voucherStatus: "Vigente"
    	},
        {
    		cancelationProcessDate: "AAAAAAAAAAAAAAAAAAAA",
    		cancelationProcessStatus: "AAAAAAAAAAAAAAAAA",
            cancelationStatus: "AAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
            certificationDate: "2019-01-02T09:36:05",
            certifiedPAC: "BBA830831LJ2",
            emisorName: "ERICK DOM",
            emisorRFC: "AAAAAAAAAAAAAAAAAAAAAAAAA",
            emited: true,
            emitedDate: "02/01/2019",
            fiscalId: "AAAAAAAAAAAAAAAAAAAAAA",
            receiverName: "AAAAAAAAAAAAAAAAAAAAA",
            receiverRFC: "AAAAAAAAAAAAAAAAAAAAA",
            total: "$256.36",
            userId: "AAAAAAAAAAAAAAAAAAAAAAAAAAA",
            voucherEffect: "AAAAAAAAAAAAAAAAAAAAA",
            voucherStatus: "AAAAAAAAAAAAAAAAAAAAAAAAAA"
    	}
    ]
    */

    $scope.getData = function(){

        var loading_layer = $("<div></div>").addClass("loader");
        $("body").append(loading_layer).find(".loader").animate({opacity: 1},200,"linear");

        $http.get("/bills?rfc=" + $scope.rfc + "&pass=" + $scope.pass).then(function mySuccess(response) {
            $scope.bills = response.data;
            $(".loader").remove();
        }, function myError(response) {
            $scope.myWelcome = response.statusText;
        });
    }



}]);
