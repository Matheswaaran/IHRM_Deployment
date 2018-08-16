app.controller( "dashboardCtrl", function ($scope, $rootScope, toaster, $routeParams, $location, $http) {

    $scope.records = {};

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

    $scope.action = { action: 'GET_ALL' };

    $http.post('api/getData.php', $scope.action)
        .then(function(response){
            $scope.records = response.data.records;
            $scope.approved_employees = $scope.records.employees.filter(employee => employee.auth === '1');
            $scope.pending_employees = $scope.records.employees.filter(employee => employee.auth === '0');
        });
});
