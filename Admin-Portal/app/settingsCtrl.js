app.controller('settingsCtrl', function($scope, $route, $rootScope, toaster, $routeParams, $location, $http) {

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

    $scope.changePassword = function () {
        $scope.password = {
            ...$scope.password,
            action: 'CHANGE_PASSWORD',
            gid: localStorage.getItem("admin_id")
        };
        console.log($scope.password);
        $http.post("api/changeAdminData.php", $scope.password)
            .then(function (response) {
                toaster.pop(response.data.status,"",response.data.message,3000,'trustedHtml');
                if (response.data.status === "success"){
                    $route.reload();
                }
            });
    };

    $scope.changeEmail = function () {
        $scope.email = {
            ...$scope.email,
            action: 'CHANGE_EMAIL',
            gid: localStorage.getItem("admin_id")
        };
        $http.post("api/changeAdminData.php", $scope.email)
            .then(function (response) {
                toaster.pop(response.data.status,"",response.data.message,3000,'trustedHtml');
                if (response.data.status === "success"){
                    $route.reload();
                }
            });
    };

    $scope.changePhone = function () {
        $scope.phone = {
            ...$scope.phone,
            action: 'CHANGE_PHONE',
            gid: localStorage.getItem("admin_id")
        };
        $http.post("api/changeAdminData.php", $scope.phone)
            .then(function (response) {
                toaster.pop(response.data.status,"",response.data.message,3000,'trustedHtml');
                if (response.data.status === "success"){
                    $route.reload();
                }
            });
    };
});