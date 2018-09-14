angular.module('adminApp').controller("adminController", ['$scope', 'menuService', 'userService', '$http', function ($scope, menuService, userService, $http) {

    $scope.title = "Dashboard";
    $scope.users = [];

    $http.get("../user/?user=mock").then(function mySuccess(response) {
        userService.data = response.data;
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });

    $http.get("../user/").then(function mySuccess(response) {
        $scope.users = response.data;
    }, function myError(response) {
        //error
    });


    /*
    * Menu element object
    * Contains all the properties need to the proper functionality
    */
    $scope.menuElements = [
         {
            section: "Menu de Administradores",
            class: "title",
            icon: "",
            href: ""
         },
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
         },/*
         {
            section: "Usuarios",
            class: "",
            icon: "fa fa-archive",
            href: "#!/users"
         },*/
          {
            section: "Clientes",
            class: "",
            icon: "fa fa-archive",
            href: "#!/customers"
          },
          {
            section: "Visualizador de clientes",
            class: "title",
            icon: "",
            href: ""
          }
    ];





    var combineMenuElements = function(adminData, customerData){
        customerData.forEach(function(element){
            element.href = element.href.replace('../', '');
        });
        Array.prototype.push.apply(adminData,customerData)
        return adminData;
    }


     $scope.menuElements = combineMenuElements($scope.menuElements, menuService);


    /*
    * Add class="active" to the clicked list element
    */
    $scope.selectListElement = function(elementSelected){
        $scope.menuElements.forEach(function(menuElement){
            if(menuElement.class != 'title')
                menuElement.class = "";
        });
        elementSelected.class = "active";
    }

    $scope.setCustomer = function(customer){
        userService.data.common = customer;
    }

}]);