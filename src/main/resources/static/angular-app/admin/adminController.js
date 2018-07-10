angular.module('adminApp').controller("adminController", ['$scope', function ($scope) {

    $scope.title = "Dashboard";

    /*
    * Menu element object
    * Contains all the properties need to the proper functionality
    */
    $scope.menuElements = [
        {
            section: "Dashboard",
            class: "active",
            icon: "fa fa-home",
            href: "#!/"
        },
        {
            section: "Alertas",
            class: "",
            icon: "fa fa-archive",
            href: "#!/inbox"
        }
    ];


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