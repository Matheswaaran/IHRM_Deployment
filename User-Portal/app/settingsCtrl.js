app.controller('settingsCtrl', function($scope, $route, $rootScope, toaster, $routeParams, $location, $http) {

    $scope.init = function(){
        $http.get("api/checkSession.php")
            .then(function (response) {
                if (response.data.status === "success"){
                    $rootScope.user_id = response.data.user_id;
                    $rootScope.user_username = response.data.user_username;
                    localStorage.setItem("user_id",response.data.user_id);
                    localStorage.setItem("user_username",response.data.user_username);
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
            uid: localStorage.getItem("user_id")
        };
        console.log($scope.password);
        $http.post("api/changeUserData.php", $scope.password)
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
            uid: localStorage.getItem("user_id")
        };
        $http.post("api/changeUserData.php", $scope.email)
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
            uid: localStorage.getItem("user_id")
        };
        $http.post("api/changeUserData.php", $scope.phone)
            .then(function (response) {
                toaster.pop(response.data.status,"",response.data.message,3000,'trustedHtml');
                if (response.data.status === "success"){
                    $route.reload();
                }
            });
    };
});