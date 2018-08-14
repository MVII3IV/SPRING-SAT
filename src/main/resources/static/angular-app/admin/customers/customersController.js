angular.module('adminApp').controller("customersController", ['$scope', function ($scope) {
    $scope.title = "Clientes";


    $scope.customers = [
        {
            name: "Ramon Duran",
            pass: "privado123",
            rfc: "RAD123789"
        },
         {
             name: "Francisco Quiroz",
             pass: "privado23",
             rfc: "FRAN987654"
         },
         {
             name: "Ramon Quintana",
             pass: "privado188",
             rfc: "12345789"
         },
         {
             name: "Enrique Gonzalez",
             pass: "privado193",
             rfc: "ENRI123456"
         }
         ,
         {
             name: "Antoio Ortega",
             pass: "privado153",
             rfc: "ANT154863"
         }
    ];

}]);
