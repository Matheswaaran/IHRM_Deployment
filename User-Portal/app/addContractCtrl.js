app.controller( "addContractCtrl", function ($scope, $rootScope, $route, toaster, $routeParams, $location, $http) {

    $scope.contractor = {};
    $scope.emailIsAvailable = false;

    $scope.init = function(){
        $http.get("api/checkSession.php")
            .then(function (response) {
                console.log(response);
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
        $scope.contractor.password = pass;
    };

    $scope.chkId = function(){

        id = $scope.contractor.cid;

        if (id){
            $scope.data = {data: id, type: 'CONTRACTOR_CHK_ID'};
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

        email = $scope.contractor.email;

        if (email) {
            $scope.data = { data: email, type: "CONTRACTOR_CHK_EMAIL" };
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

    $scope.addContractor = function() {
        $scope.contractor.uid = localStorage.getItem("user_id");
        
        if ($scope.emailIsAvailable){
            $http.post('api/addContractor.php', $scope.contractor)
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