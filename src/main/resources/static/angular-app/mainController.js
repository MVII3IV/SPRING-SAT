angular.module('app').controller("mainController", ['$scope', function($scope){
    $scope.menuElements = [ {section: "Ingresos", class: "Active"},
    {
        section:"Gastos",
        class: "Active"
    }, {
    section: "Simulador de Impuestos",
    class: "Active"
    }, {section: "Declaracion", class: "Active"}, {section: "Facturas", class: "Active"}, {section: "Nominas", class: "Active"}, {section:"IMMS", class: "Active"}, {section: "Pagos",class: "Active"}, {section:"Ayuda", class: "Active"}];
}]);