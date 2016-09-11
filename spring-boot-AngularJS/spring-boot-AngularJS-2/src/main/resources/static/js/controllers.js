

app.controller('mainCtrl', function($scope) {
  // We have access to this new
  // $scope object where we can place
  // data and functions to interact with it
});

app.run(function($rootScope) {
  $rootScope.user = {
    name: 'Era'
  };
  $rootScope.message = "Welcome back";
});