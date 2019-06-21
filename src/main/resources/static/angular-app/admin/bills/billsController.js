angular.module('adminApp')

    .directive('loadFinished', function() {
      return function(scope, element, attrs) {
        if (scope.$last){
            $('body').css('height', $('.dev-page-content').prop('scrollHeight') + 20);
        }
      };
    })

    .controller("billsController", ['$scope', '$http', 'billsService', function ($scope, $http, billsService) {

    $scope.title = "Facturas";
    $scope.mode = "Recibidas";
    //http://104.248.23.45:8080/crawler/extract/data?rfc=LULR860821MTA&pass=goluna21


   $scope.getData = function(){

        var loading_layer = $("<div></div>").addClass("loader");
        $("body").append(loading_layer).find(".loader").animate({opacity: 1},200,"linear");

        $http.get("/bills?rfc=" + $scope.rfc + "&pass=" + $scope.pass).then(function mySuccess(response) {

            $scope.bills = billsService.orderBillsByDate(response.data);

            $(".loader").remove();
        }, function myError(response) {
            $scope.myWelcome = response.statusText;
        });
    }



}]);
