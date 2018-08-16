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
      .when('/', {
        title: 'Login',
        templateUrl: 'partials/login.html',
        controller: 'authCtrl',
        role: '0'
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
      .when('/settings', {
        title: 'Settings',
        templateUrl: 'partials/settings.html',
        controller: 'settingsCtrl'
      })
      .when('/addContractors', {
        title: 'Add Contractors',
        templateUrl: 'partials/addcontractors.html',
        controller: 'addContractCtrl'
      })
      .when('/addPkgContractors', {
        title: 'Add Package Contractors',
        templateUrl: 'partials/addpackagecontractors.html',
        controller: 'addPkgContractCtrl'
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

		$rootScope.user_id = '0';
        $rootScope.user_username = null;
        $rootScope.user_blocked = 0;

    });
});

app.factory('IHRM_adminAppService', function () {

});