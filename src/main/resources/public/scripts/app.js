/**
 * Created by shekhargulati on 10/06/14.
 */

var app = angular.module('todoapp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute',
    'ui.bootstrap'
]);

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        redirectTo: '/tasks'
    }).when('/tasks', {
        templateUrl: 'views/tasks.html',
        controller: 'TaskController'
    }).when('/tasks/create', {
        templateUrl: 'views/createtask.html',
        controller: 'TaskController'
    }).when('/scheduledtasks', {
        templateUrl: 'views/scheduledtasks.html',
        controller: 'ScheduledTaskController'
    }).when('/scheduledtasks/create', {
        templateUrl: 'views/createscheduledtask.html',
        controller: 'ScheduledTaskController'
    }).otherwise({
        redirectTo: '/tasks'
    })
});

app.directive('ngReallyClick', [function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('click', function() {
                var message = attrs.ngReallyMessage;
                if (message && confirm(message)) {
                    scope.$apply(attrs.ngReallyClick);
                }
            });
        }
    }
}]);

app.controller('TaskController', function ($scope, $http, $location, $route) {
    if($location.$$path == "/tasks") {
        $http.get('/v1/tasks').success(function (data) {
            $scope.tasks = data;
        }).error(function (data, status) {
            console.log('Error ' + data)
        });
    }

    $scope.task = {
        done: false
    };


    $scope.createTask = function () {
        $http.post('/v1/tasks', $scope.task).success(function (data) {
            $location.path('/tasks');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }

    $scope.deleteTask = function (task) {
        $http.delete('/v1/tasks/' + task.id).success(function (data) {
            $route.reload();
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

app.controller('ScheduledTaskController', function ($scope, $http, $location, $route) {
    if($location.$$path == "/scheduledtasks") {
        $http.get('/v1/scheduledtasks').success(function (data) {
            $scope.tasks = data;
        }).error(function (data, status) {
            console.log('Error ' + data)
        });
    }

    $scope.task = {
        done: false
    };


    $scope.createTask = function () {
        $http.post('/v1/scheduledtasks', $scope.task).success(function (data) {
            $location.path('/scheduledtasks');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }

    $scope.deleteTask = function (task) {
        $http.delete('/v1/scheduledtasks/' + task.id).success(function (data) {
            $route.reload();
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

app.controller('HeaderController', function ($scope, $http, $location) {
    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path();
    };
});