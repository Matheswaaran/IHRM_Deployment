app.controller( "addPkgContractCtrl", function ($scope, $rootScope, toaster, $route, $routeParams, $location, $http) {

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

    $scope.generatePass = function () {
        let chars = "abcdefghijklmnopqrstuvwxyz!@#$%^&*()-+<>ABCDEFGHIJKLMNOP1234567890";
        let pass = "";
        for (let x = 0; x < 10; x++) {
            let i = Math.floor(Math.random() * chars.length);
            pass += chars.charAt(i);
        }
        $scope.pkg_contractor.password = pass;
    };

    $scope.chkId = function(){

        id = $scope.pkg_contractor.pkg_id;

        if (id){
            $scope.data = {data: id, type: 'PKG_CONTRACTOR_CHK_ID'};
            $http.post('api/chkAvailability.php',$scope.data)
                .then(function (response) {
                    if (response.data.status === "available") {
                        document.getElementById('idavail').style.display = "none";
                        $scope.emailIsAvailable = true;
                    }else if (response.data.status === "not available") {
                        document.getElementById('idavail').style.display = "block";
                        $scope.emailIsAvailable = false;
                    }
                });
        }
    };

    $scope.chkEmail = function (){

        email = $scope.pkg_contractor.email;

        if (email) {
            $scope.data = { data: email, type: "PKG_CONTRACTOR_CHK_EMAIL" };
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

    $scope.addPkgContractor = function() {

        $scope.pkg_contractor.uid = localStorage.getItem("user_id");

        if ($scope.emailIsAvailable){
            $http.post('api/addPkgContractor.php', $scope.pkg_contractor)
                .then(function (response) {
                    toaster.pop(response.data.status,"",response.data.message,3000,'trustedHtml');
                    if (response.data.status === "success"){
                        $route.reload();
                    }
                });
        } else {
            toaster.pop("error","","Email-id not available or Already exists",3000,'trustedHtml');
        }
    };
});