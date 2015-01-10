var ctrl = angular.module('Auth');
ctrl.controller('AuthLoginController',
		[
			'$rootScope',
			'$scope',
			'$location',
			'NotificationService',
			'AuthService',
			function ($rootScope, $scope, $location, NotificationService, AuthService) {
				$scope.login = function () {
					var loginResult = function (result) {
						if (result === 'OK' || result === 'ALREADY_CONNECTED') {
							$rootScope.updateCurrentUser();
							$location.path('/');
						} else if (result === 'INTERNAL_ERROR') {
							NotificationService.notify('ERROR', 'error.internal', result);
						} else {
							NotificationService.notify('ERROR', 'auth.result.unknownUserPassword');
						}
					};
					AuthService.login({
						login: $scope.email,
						password: $scope.password
					}, loginResult);
				};
			}
		]
		);