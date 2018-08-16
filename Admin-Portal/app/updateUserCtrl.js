app.controller('updateUserCtrl', function($scope, $route, $rootScope, toaster, $routeParams, $location, $http){

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
                if($rootScope.edit_user === {} || typeof $rootScope.edit_user === 'undefined'){
                    $location.path('/viewUsers');
                }
            });
    };
});