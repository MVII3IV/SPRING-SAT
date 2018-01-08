angular.module('app').controller("mainController", ['$scope', function ($scope) {

    $scope.menuElements = [{
            section: "Pagina Principal",
            class: "active"
        },
        {
            section: "Ingresos",
            class: ""
        },
        {
            section: "Gastos",
            class: ""
        }, {
            section: "Simulador de Impuestos",
            class: ""
        }, {
            section: "Declaracion",
            class: ""
        }, {
            section: "Facturas",
            class: ""
        }, {
            section: "Nominas",
            class: ""
        }, {
            section: "IMMS",
            class: ""
        }, {
            section: "Pagos",
            class: ""
        }, {
            section: "Ayuda",
            class: ""
        }
    ];



    var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

      $scope.xaxis = 'y';
      $scope.yaxis = '["a,b"]';

     	$scope.data = [
        { y: "2006-07", a: 100, b: 90 },
        { y: "2007-06", a: 75,  b: 65 },
        { y: "2008-05", a: 50,  b: 40 },
        { y: "2009-04", a: 40,  b: 65 },
        { y: "2010-03", a: 30,  b: 40 },
        { y: "2011-02", a: 10,  b: 65 },
        { y: "2012-01", a: 0, b: 90 }
    	];

      $scope.xLabelFormat = function(x) { // <--- x.getMonth() returns valid index
        var month = months[x.getMonth()];
        return month;
      }

      $scope.dateFormat = function(x) {
        var month = months[new Date(x).getMonth()];
        return month;
      }




}]);