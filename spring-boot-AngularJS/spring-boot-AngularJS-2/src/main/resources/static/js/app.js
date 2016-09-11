var app = angular.module('myApp', []);

angular.module('myApp', [])
.run(function($rootScope) {
  $rootScope.user = {
    email: 'balliue@gmail.com'
  };
  $rootScope.message = "Welcome back";
});

app.factory('MathService', function() {
   var factory = {};

   factory.multiply = function(a, b) {
      return a * b
   }

   return factory;
});

app.service('CalcService', function(MathService){
   this.square = function(a) {
      return MathService.multiply(a,a);
   }
});