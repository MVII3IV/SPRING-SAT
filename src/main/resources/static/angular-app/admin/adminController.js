angular.module('app').controller("adminController", ['$scope', function ($scope) {

    /*
    * Menu element object
    * Contains all the properties need to the proper functionality
    */
    $scope.menuElements = [
        {
            section: "Pagina Principal",
            class: "active",
            icon: "fa fa-home",
            href: "../#!/"
        },
        {
            section: "Usuarios",
            class: "",
            icon: "fa fa-archive",
            href: "../#!/income"
        }
    ];

    $scope.title = "Panel de Administradores";

    /*
    * Add class="active" to the clicked list element
    */
    $scope.selectListElement = function(elementSelected){
        $scope.menuElements.forEach(function(menuElement){
            menuElement.class = "";
        });
        elementSelected.class = "active";
    }

}]);