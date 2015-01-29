angular.module('User').controller('UserListController', [
	'$scope',
	'$filter',
	'$location',
	'ngTableParams',
	'UserService',
	function ($scope, $filter, $location, ngTableParams, UserService) {

		$scope.tableParams = new ngTableParams(
				angular.extend({
					page: 1, // show first page
					count: 25, // count per page
					sorting: {
						lastname: 'asc',
						firstname: 'asc'
					}
				}, $location.search()), {
			//total: $scope.users.length, // length of data
			getData: function ($defer, params) {
				if ($scope.users) {
					params.total($scope.users.length);

					$location.search(params.url()); // put params in url

					var filteredData = params.filter() ? $filter('filter')($scope.users, params.filter()) : $scope.users;

					var orderedData = params.sorting() ? $filter('orderBy')(filteredData, params.orderBy()) : filteredData;

					$defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
				}
			}
		});

		var updateUserList = function () {
			UserService.list(function (data) {
				$scope.users = data;
				$scope.tableParams.reload();
			});
		};
		$scope.remove = function (user) {
			UserService.remove(user.id, function () {
				updateUserList();
			});
		};

		updateUserList();

	}
]);

	