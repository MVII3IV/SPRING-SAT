angular.module('adminApp').controller("usersController", ['$scope', function ($scope) {
    $scope.title = "Usuarios";

    $scope.users = [
        {
            img: "user_2",
            name: "Brayan Garcia",
            role: "Nominas",
            customers: [
                {
                    name: "Ramon Quintana",
                    rfc: "12345789"
                },
                {
                    name: "Enrique Gonzalez",
                    rfc: "ENRI123456"
                }
                ,
                {
                    name: "Anotio Ortega",
                    rfc: "ANT154863"
                }
            ]
        },
        {
            img: "user_3",
            name: "Britney Lopez",
            role: "Facturas",
            customers: [
                {
                    name: "Ramon Duran",
                    rfc: "RAD123789"
                },
                {
                    name: "Francisco Quiroz",
                    rfc: "FRAN987654"
                }
            ]
        },
        {
            img: "user_4",
            name: "TJ Lopez",
            role: "IMSS",
            customers: [
                {
                    name: "Ramon Duran",
                    rfc: "RAD123789"
                }
            ]
        }
    ];


    $scope.customers = [
        {
            name: "Ramon Duran",
            rfc: "RAD123789"
        },
         {
             name: "Francisco Quiroz",
             rfc: "FRAN987654"
         },
         {
             name: "Ramon Quintana",
             rfc: "12345789"
         },
         {
             name: "Enrique Gonzalez",
             rfc: "ENRI123456"
         }
         ,
         {
             name: "Antoio Ortega",
             rfc: "ANT154863"
         }
    ];

}]);
