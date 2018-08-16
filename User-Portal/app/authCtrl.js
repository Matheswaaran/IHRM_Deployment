app.controller( "authCtrl", function ($scope, $rootScope, toaster, $routeParams, $location, $http) {

      $scope.login = {};

      $scope.init = function(){
          $http.get("api/checkSession.php")
              .then(function (response) {
                  if (response.data.status === "success"){
                      toaster.pop(response.data.status,"",response.data.message,30000,'trustedHtml');
                      $location.path('/dashboard');
                  }else if (response.data.status === "error") {
                      $location.path('/login');
                  }
              });
      };

      $scope.doLogin = function () {
          console.log($scope.login);
          $http.post('api/login.php',$scope.login)
              .then(function (response) {
                  toaster.pop(response.data.status,"",response.data.message,3000,'trustedHtml');
                  if (response.data.status === "success"){
                      $location.path('/dashboard');
                  }
              });
      };

      $scope.doLogout = function () {
          $http.get("api/logout.php")
              .then(function (response) {
                  toaster.pop(response.data.status,"",response.data.message,3000,'trustedHtml');
                  if (response.data.status === "success"){
                      localStorage.clear();
                      $location.path('/login');
                  }
              });
      }
});
