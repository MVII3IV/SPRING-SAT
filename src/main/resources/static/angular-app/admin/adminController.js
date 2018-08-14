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
         },
         {
             section: "Usuarios",
             class: "",
             icon: "fa fa-archive",
             href: "#!/users"
         },
          {
              section: "Clientes",
              class: "",
              icon: "fa fa-archive",
              href: "#!/customers"
          },
         {
             section: "Declaraciones",
             class: "",
             icon: "fa fa-archive",
             href: "#!/declarations"
         },
         {
             section: "Nominas",
             class: "",
             icon: "fa fa-archive",
             href: "#!/paysheets"
         },
          {
              section: "Documentacion",
              class: "",
              icon: "fa fa-archive",
              href: "#!/documentation"
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