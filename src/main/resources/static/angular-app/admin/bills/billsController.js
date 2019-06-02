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
    $scope.mode = "Recibidas";
    //http://104.248.23.45:8080/crawler/extract/data?rfc=LULR860821MTA&pass=goluna21


   $scope.getData = function(){

        var loading_layer = $("<div></div>").addClass("loader");
        $("body").append(loading_layer).find(".loader").animate({opacity: 1},200,"linear");

        $http.get("/bills?rfc=" + $scope.rfc + "&pass=" + $scope.pass).then(function mySuccess(response) {


            $scope.bills = response.data;

            var options = { year: 'numeric', month: 'numeric', day: 'numeric' };

/*
            for(var i = 0 ; i < $scope.bills.length ; i++){
                var dateString = $scope.bills[i].emitedDate.split('/');
                $scope.bills[i].emitedDate = new Date(dateString[2], dateString[1] - 1, dateString[0]).toLocaleDateString('es-ES', options);
            }*/


            $(".loader").remove();
        }, function myError(response) {
            $scope.myWelcome = response.statusText;
        });
    }



}]);
