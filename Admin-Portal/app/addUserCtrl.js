app.controller("addUserCtrl", function ($scope, $route, $rootScope, toaster, $routeParams, $location, $http) {

    $scope.user = {};
    $scope.usernameIsAvailable = false;
    $scope.emailIsAvailable = false;

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

    $scope.generatePass = function () {
      let chars = "abcdefghijklmnopqrstuvwxyz!@#$%^&*()-+<>ABCDEFGHIJKLMNOP1234567890";
      let pass = "";
      for (let x = 0; x < 10; x++) {
        let i = Math.floor(Math.random() * chars.length);
        pass += chars.charAt(i);
      }
      $scope.user.password = pass;
    };

    $scope.chkUsername = function (){
      username = $scope.user.username;
      if (username) {
        $scope.data = {
          data: username,
          type: "username"
        };
        $http.post('api/chkAvailability.php',$scope.data)
            .then(function (response) {
              // console.log(response);
              if (response.data.status === "available") {
                document.getElementById('useravail').style.display = "none";
                  $scope.usernameIsAvailable = true;
              }else if (response.data.status === "not available") {
                document.getElementById('useravail').style.display = "block";
                  $scope.usernameIsAvailable = false;
              }
            });
      }
    };

    $scope.chkEmail = function (){
      email = $scope.user.email;
      if (email) {
        $scope.data = {
          data: email,
          type: "email"
        };
        $http.post('api/chkAvailability.php',$scope.data)
            .then(function (response) {
              if (response.data.status === "available") {
                document.getElementById('emailavail').style.display = "none";
                $scope.emailIsAvailable = true;
              }else if (response.data.status === "not available") {
                document.getElementById('emailavail').style.display = "block";
                $scope.emailIsAvailable = false;
              }
            });
      }
    };

    $scope.addUser = function (user) {

        $scope.user.gid = localStorage.getItem("admin_id");

        if ($scope.usernameIsAvailable && $scope.emailIsAvailable ){
            $http.post('api/addUser.php',$scope.user)
                .then(function (response) {
                    toaster.pop(response.data.status,"",response.data.message,3000,'trustedHtml');
                    if (response.data.status === "success"){
                        $route.reload();
                    }
                });
        } else {
            toaster.pop("error","","username or email-id not available",3000,'trustedHtml');
        }
    }
});
