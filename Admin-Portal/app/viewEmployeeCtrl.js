app.controller("viewEmployeeCtrl", function ($scope, $route, $rootScope, toaster, $routeParams, $location, $http) {

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

    $scope.action = { action: 'GET_EMPLOYEES' };
    $http.post('api/getData.php', $scope.action)
        .then(function (response) {
            $scope.employees = response.data.records.map(employee => {
                employee.selected = false;
                return employee;
            });
            $scope.pendingEmployees = $scope.employees.filter(x => x.auth === '0');
            $scope.approvedEmployees = $scope.employees.filter(x => x.auth === '1');
        });

    $scope.approveEmployees = function () {
        $scope.selected_employees = $scope.employees.filter(employee => employee.selected === true);
        if ($scope.selected_employees.length === 0){
            toaster.pop("warning","","Select Employees to approve",3000,'trustedHtml');
        } else {
            $scope.employees_data = {employees: $scope.selected_employees, action: 'APPROVE_EMPLOYEES'};
            $http.post("api/manageEmployees.php", $scope.employees_data)
                .then(function(response){
                    toaster.pop(response.data.status,"",response.data.message,3000,'trustedHtml');
                    if (response.data.status === "success"){
                        $route.reload();
                    }
                });
        }
    };


    $scope.disApproveEmployees = function () {
        $scope.selected_employees = $scope.employees.filter(employee => employee.selected === true);
        console.log($scope.selected_employees);
        if ($scope.selected_employees.length === 0){
            toaster.pop("warning","","Select employees to disapprove",3000,'trustedHtml');
        } else {
            $scope.employees_data = {employees: $scope.selected_employees, action: 'DIS_APPROVE_EMPLOYEES'};
            $http.post("api/manageEmployees.php", $scope.employees_data)
                .then(function(response){
                    toaster.pop(response.data.status,"",response.data.message,3000,'trustedHtml');
                    if (response.data.status === "success"){
                        $route.reload();
                    }
                });
        }
    };

});