app.controller("viewEmployeeCtrl", function ($scope, $route, $rootScope, toaster, $routeParams, $location, $http) {

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

    $scope.action = { action: 'GET_EMPLOYEES' };
    $http.post('api/getData.php', $scope.action)
        .then(function (response) {
            $scope.employees = response.data.records;
            $scope.pendingEmployees = $scope.employees.filter(x => x.auth === '0');
            $scope.approvedEmployees = $scope.employees.filter(x => x.auth === '1');
        });
});