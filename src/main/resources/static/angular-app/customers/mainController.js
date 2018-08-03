angular.module('app').controller("mainController", ['$scope', '$timeout', function ($scope, $timeout) {

    /*
    * Menu element object
    * Contains all the properties need to the proper functionality
    */
    $scope.menuElements = [
        {
            section: "Pagina Principal",
            class: "active",
            icon: "fa fa-home",
            href: "../#!/landing-page"
        },
        {
            section: "Ingresos",
            class: "",
            icon: "fa fa-archive",
            href: "../#!/income"
        },
        {
            section: "Gastos",
            class: "",
            icon: "fa fa-sticky-note-o",
            href: "../#!/outgoings"
        },
        {
            section: "Tramites Diversos",
            class: "",
            icon: "fa fa-file-text",
            href: "../#!/procedures"
        },
        {
            section: "Simulador de Impuestos",
            class: "",
            icon: "fa fa-file-text",
            href: "../#!/tax-simulator"
        },
        {
            section: "Declaracion",
            class: "",
            icon: "fa fa-clipboard",
            href: "../#!/declarations"
        },
        {
            section: "Facturacion",
            class: "",
            icon: "fa fa-folder",
            href: "../#!/bills"
        },
        {
            section: "Nominas",
            class: "",
            icon: "fa fa-file-text-o",
            href: "../#!/paysheets"
        },
        {
            section: "IMSS",
            class: "",
            icon: "fa fa-users",
            href: "../#!/social-security"
        },
        {
            section: "Pagos",
            class: "",
            icon: "fa fa-money",
            href: "../#!/payments"
        },
        {
            section: "Ayuda",
            class: "",
            icon: "fa fa-question",
            href: "../#!/help"
        }
    ];

    $scope.title = "Ingresos y Gastos";
    $scope.icomeIn01 = 1000;
    $scope.icomeOut = 600;
    $scope.$broadcast('timer-start');

    $scope.runTimer = function(value){
        while(value < 5000){
        $timeout(1000);
        value++;
        }

    }

}]);