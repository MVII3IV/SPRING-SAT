angular.module('app').controller("mainController", ['$scope', function ($scope) {

    $scope.menuElements = [
        {
            section: "Pagina Principal",
            class: "active",
            icon: "icon ion-home"
        },
        {
            section: "Ingresos",
            class: "",
            icon: "icon ion-archive"
        },
        {
            section: "Gastos",
            class: "",
            icon: "icon ion-document-text"
        },
        {
            section: "Simulador de Impuestos",
            class: "",
            icon: "icon ion-document"
        },
        {
            section: "Declaracion",
            class: "",
            icon: "icon ion-clipboard"
        },
        {
            section: "Facturas",
            class: "",
            icon: "icon ion-folder"
        },
        {
            section: "Nominas",
            class: "",
            icon: "icon ion-compose"
        },
        {
            section: "IMMS",
            class: "",
            icon: "icon ion-person-stalker"
        },
        {
            section: "Pagos",
            class: "",
            icon: "icon ion-forward"
        },
        {
            section: "Ayuda",
            class: "",
            icon: "icon ion-help-buoy"
        }
    ];


    var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"];

      $scope.xaxis = 'y';
      $scope.yaxis = '["a", "b"]';

     $scope.data = [
          { y: "2006", a: 100, b: 90 },
          { y: "2007", a: 75,  b: 65 },
          { y: "2008", a: 50,  b: 40 },
          { y: "2009", a: 75,  b: 65 },
          { y: "2010", a: 50,  b: 40 },
          { y: "2011", a: 75,  b: 65 },
          { y: "2012", a: 100, b: 90 }
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