app.controller("addSiteCtrl", function ($scope, $route, $rootScope, toaster, $routeParams, $location, $http) {

    $scope.site = {};

    $scope.init = function(){
        $http.get("api/checkSession.php")
            .then(function (response) {
                if (response.data.status === "success"){
                    $rootScope.admin_id = response.data.admin_id;
                    $rootScope.admin_username = response.data.admin_username;
                    localStorage.setItem("admin_id",response.data.admin_id);
                    localStorage.setItem("admin_username",response.data.admin_username);
                } else if (response.data.status === "error"){
                    toaster.pop(response.data.status,"",response.data.message,3000,'trustedHtml');
                    $location.path('/login');
                }
            });
    };

    $scope.addSite = function () {

        $scope.site = {
            ...$scope.site,
            address: $scope.site.address_1 + ", " + $scope.site.address_2,
            gid: localStorage.getItem("admin_id")
        };

        $http.post("api/addSite.php", $scope.site)
            .then(function (response) {
                toaster.pop(response.data.status,"",response.data.message,3000,'trustedHtml');
                if (response.data.status === "success"){
                    $route.reload();
                }
            });
    }
});
