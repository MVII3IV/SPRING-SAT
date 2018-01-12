angular.module('app').controller("mainController", ['$scope', function ($scope) {

    $scope.menuElements = [
        {
            section: "Pagina Principal",
            class: "active",
            icon: "fa fa-home"
        },
        {
            section: "Ingresos",
            class: "",
            icon: "fa fa-archive"
        },
        {
            section: "Gastos",
            class: "",
            icon: "fa fa-sticky-note-o"
        },
        {
            section: "Simulador de Impuestos",
            class: "",
            icon: "fa fa-file-text"
        },
        {
            section: "Declaracion",
            class: "",
            icon: "fa fa-clipboard"
        },
        {
            section: "Facturas",
            class: "",
            icon: "fa fa-folder"
        },
        {
            section: "Nominas",
            class: "",
            icon: "fa fa-file-text-o"
        },
        {
            section: "IMMS",
            class: "",
            icon: "fa fa-users"
        },
        {
            section: "Pagos",
            class: "",
            icon: "fa fa-money"
        },
        {
            section: "Ayuda",
            class: "",
            icon: "fa fa-question"
        }
    ];

}]);