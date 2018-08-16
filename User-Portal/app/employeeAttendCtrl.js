app.controller( "employeeAttendCtrl", function ($scope, $rootScope, toaster, $routeParams, $location, $http) {

    $scope.attendance = {};

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

    $scope.action = { action: 'GET_EMPLOYEE_ATTENDANCE' };

    $http.post('api/getData.php', $scope.action)
        .then(function (response) {
            $scope.attendance = response.data.records;
        });

});