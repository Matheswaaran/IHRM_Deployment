var app = angular.module('IHRM_adminApp',['ngRoute','ngAnimate','toaster','datatables']);

app.config(['$locationProvider', '$routeProvider', function ($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');
	$routeProvider
      .when('/login', {
        title: 'Login',
        templateUrl: 'partials/login.html',
        controller: 'authCtrl'
      })
      .when('/dashboard', {
        title: 'Dashboard',
        templateUrl: 'partials/dashboard.html',
        controller: 'dashboardCtrl'
      })
      .when('/addUser', {
        title: 'Add user',
        templateUrl: 'partials/adduser.html',
        controller: 'addUserCtrl'
      })
      .when('/', {
        title: 'Login',
        templateUrl: 'partials/login.html',
        controller: 'authCtrl',
        role: '0'
      })
      .when('/addSite', {
        title: 'Add Site',
        templateUrl: 'partials/addsite.html',
        controller: 'addSiteCtrl'
      })
      .when('/viewUsers', {
        title: 'View Users',
        templateUrl: 'partials/viewusers.html',
        controller: 'viewUserCtrl'
      })
      .when('/viewContractors', {
        title: 'View Contractors',
        templateUrl: 'partials/viewcontract.html',
        controller: 'viewContractCtrl'
      })
      .when('/viewPkgContractors', {
        title: 'View Package Contractors',
        templateUrl: 'partials/viewpkgcontract.html',
        controller: 'viewPkgContractCtrl'
      })
      .when('/viewEmployees', {
        title: 'View Employees',
        templateUrl: 'partials/viewemployees.html',
        controller: 'viewEmployeeCtrl'
      })
      .when('/viewSites', {
        title: 'View Sites',
        templateUrl: 'partials/viewsites.html',
        controller: 'viewSiteCtrl'
      })
      .when('/updateUsers', {
        title: 'Update Users',
        templateUrl: 'partials/updateuser.html',
        controller: 'updateUserCtrl'
      })
      .when('/updateSites', {
        title: 'Update Sites',
        templateUrl: 'partials/updatesites.html',
        controller: 'updateSiteCtrl'
      })
      .when('/employeeAttendance', {
        title: 'Employees Attendance',
        templateUrl: 'partials/employeeattendance.html',
        controller: 'employeeAttendCtrl'
      })
      .when('/pkgContractorsReq', {
        title: 'Package Contractors Requests',
        templateUrl: 'partials/pkgcontractsreq.html',
        controller: 'pkgContractReqCtrl'
      })
      .when('/settings', {
        title: 'Settings',
        templateUrl: 'partials/settings.html',
        controller: 'settingsCtrl'
      })
      .otherwise({
        redirectTo: '/login'
      });
}]);

app.run(function ($rootScope, $location, $http, IHRM_adminAppService) {
    $rootScope.$on("$routeChangeStart", function (event, next, current) {

        if ($location.path() === '/' || $location.path() === '/login'){
            $rootScope.navBar = true;
            $rootScope.loginPage = false;
		} else {
            $rootScope.navBar = false;
            $rootScope.loginPage = true;
		}

		$rootScope.admin_id = 0;
        $rootScope.admin_username = null;

    });
});

app.factory('IHRM_adminAppService', function () {

});