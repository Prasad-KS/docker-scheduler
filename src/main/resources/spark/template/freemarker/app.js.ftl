/**
 * Created by shekhargulati on 10/06/14.
 */

var app = angular.module('dockerscheduler', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute',
    'ui.bootstrap',
    'xeditable',
    'angular-quartz-cron',
    'ui.router',
    'ct.ui.router.extras',
    'satellizer',
    'bgf.paginateAnything'
]);

app.config(['$stateProvider', '$urlRouterProvider','$locationProvider','$authProvider',function($stateProvider, $urlRouterProvider,$locationProvider,$authProvider) {
    $urlRouterProvider.otherwise('/tasks');
    $stateProvider.state('tasks',{
        url: '/tasks',
        templateUrl: '/views/tasks.html',
        controller: 'TaskController',
        resolve: {
            loginRequired: loginRequired
        }
    }).state('createtask',{
        url: '/tasks/create',
        templateUrl: '/views/createtask.html',
        controller: 'TaskController',
        resolve: {
            loginRequired: loginRequired
        }
    }).state('scheduledtasks',{
        url: '/scheduledtasks',
        templateUrl: '/views/scheduledtasks.html',
        controller: 'TaskController',
        resolve: {
            loginRequired: loginRequired
        }
    }).state('createscheduledtask',{
        url: '/scheduledtasks/create',
        templateUrl: '/views/createscheduledtask.html',
        controller: 'TaskController',
        resolve: {
            loginRequired: loginRequired
        }
    }).state('environmentvariables',{
        url: '/environmentvariables',
        templateUrl: '/views/environmentvariables.html',
        controller: 'EnvironmentVariableController',
        resolve: {
            loginRequired: loginRequired
        }
    }).state('executions',{
        url: '/tasks/:taskId/executions',
        templateUrl: '/views/executions.html',
        controller: 'ExecutionController',
        resolve: {
            loginRequired: loginRequired
        }
    }).state('login', {
        url: '/login',
        templateUrl: '/views/login.html',
        controller: 'LoginCtrl',
        resolve: {
            skipIfLoggedIn: skipIfLoggedIn
        }
    }).state('logout', {
        url: '/logout',
        template: null,
        controller: 'LogoutCtrl'
    }).state('executetask',{
        url: '/tasks/:taskId/:taskName/executetask',
        templateUrl: '/views/executetask.html',
        controller: 'ExecuteTaskController',
        resolve: {
            loginRequired: loginRequired
        }
    }).state('settings',{
        url: '/settings',
        templateUrl: '/views/settings.html',
        controller: 'SettingsController',
        resolve: {
            loginRequired: loginRequired
        }
    });

    <#if githubClientId?? && githubClientSecret??>
    $authProvider.github({
        clientId: '${githubClientId}',
        scope: ['user:email','read:org']
    });

    function skipIfLoggedIn($q, $auth) {
        var deferred = $q.defer();
        if ($auth.isAuthenticated()) {
            deferred.reject();
        } else {
            deferred.resolve();
        }
        return deferred.promise;
    }

    function loginRequired($q, $location, $auth) {
        var deferred = $q.defer();
        if ($auth.isAuthenticated()) {
            deferred.resolve();
        } else {
            $location.path('/login');
        }
        return deferred.promise;
    }
    <#else>
    function skipIfLoggedIn($q, $auth) {
        var deferred = $q.defer();
        $location.path('/');
        return deferred.promise;
    }

    function loginRequired($q, $location, $auth) {
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise;
    }
    </#if>
}]);

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

app.controller('TaskController', function ($scope, $window, $http, $location, $state, $previousState) {
    if ($location.$$path == "/tasks") {
        $http.get('/v1/tasks').success(function (data) {
            $scope.tasks = data;
        }).error(function (data, status) {
           console.log('Error ' + data)
        });
    } else if($location.$$path == "/scheduledtasks") {
        $http.get('/v1/scheduledtasks').success(function (data) {
            $scope.tasks = data;
        }).error(function (data, status) {
            console.log('Error ' + data)
        });
    } else if($location.$$path == "/tasks/create") {
        $scope.task = {
            type: 'TASK'
        };
    } else if($location.$$path == "/scheduledtasks/create") {
        $scope.task = {
            type: 'SCHEDULED_TASK'
        };
        $scope.cronConfig = {
            options: {
                allowMonth : false,
                allowYear : false
            }
        };
    }





    $scope.createTask = function () {
        $http.post('/v1/tasks', $scope.task).success(function (data) {
            $previousState.go();
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }

    $scope.deleteTask = function (task) {
        $http.delete('/v1/tasks/' + task.id).success(function (data) {
            $state.reload();
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});


app.controller('EnvironmentVariableController', function ($scope, $http, $q, $filter) {

    $http.get('/v1/environmentvariables').success(function (data) {
        $scope.environmentvariables = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    });


    $scope.filterEnvironmentVariable = function (environmentvariable) {
        return environmentvariable.isDeleted !== true;
    }

    $scope.deleteEnvironmentVariable = function(id) {
        var filtered = $filter('filter')($scope.environmentvariables, {id: id});

        if(filtered.length) {
            filtered[0].isDeleted = true;
        }

        if (!filtered.length) {
            filtered = $filter('filter')($scope.environmentvariables, {tempId: id});
            if(filtered.length) {
              var index = $scope.environmentvariables.indexOf(filtered[0]);
              $scope.environmentvariables.splice(index,1);
            }
        }
    };

    $scope.addEnvironmentVariable = function() {
        $scope.inserted = {
          name: '',
          value: '',
          isNew: true,
          tempId: generateUUID()
        };
        $scope.environmentvariables.push($scope.inserted);
      };

    $scope.cancel = function() {
        for (var i = $scope.environmentvariables.length; i--;) {
            var environmentvariable = $scope.environmentvariables[i];
            // undelete
            if (environmentvariable.isDeleted) {
                delete environmentvariable.isDeleted;
            }
            // remove new
            if (environmentvariable.isNew) {
                $scope.environmentvariables.splice(i, 1);
            }
        };
    };

    $scope.saveAll = function () {
         var results = [];
         for (var i = $scope.environmentvariables.length; i--;) {
             var environmentvariable = $scope.environmentvariables[i];
             var create = false;
             var del = false;
             var update = false;

             if (environmentvariable.isDeleted) {
                 $scope.environmentvariables.splice(i, 1);
                 del = true;
             }

             // mark as not new
             if (environmentvariable.isNew) {
                 environmentvariable.isNew = false;
                 create = true;
             }

             if(create) {
                 results.push($http.post("/v1/environmentvariables", [environmentvariable]));
             } else if(del) {
                 results.push($http.delete("/v1/environmentvariables/" + environmentvariable.id));
             } else {
                results.push($http.put("/v1/environmentvariables/" + environmentvariable.id, environmentvariable));
             }
         }
         return $q.all(results);
    }

    function generateUUID(){
        var d = new Date().getTime();
        if(window.performance && typeof window.performance.now === "function"){
            d += performance.now(); //use high-precision timer if available
        }
        var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = (d + Math.random()*16)%16 | 0;
            d = Math.floor(d/16);
            return (c=='x' ? r : (r&0x3|0x8)).toString(16);
        });
        return uuid;
    }
});

app.controller('ExecutionController', function ($scope, $http, $location, $stateParams) {
    var taskId = $stateParams.taskId; //getting fooVal
    $scope.taskId = taskId;

    $scope.perPage = parseInt($location.search().perPage, 10) || 10;
    $scope.page = parseInt($location.search().page, 10) || 0;
    $scope.clientLimit = 20;
    $scope.url = '/v1/tasks/' + taskId + '/executions';

    $scope.$watch('page', function(page) { $location.search('page', page); });
    $scope.$watch('perPage', function(page) { $location.search('perPage', page); });
    $scope.$on('$locationChangeSuccess', function() {
        var page = +$location.search().page,perPage = +$location.search().perPage;
        if(page >= 0) { $scope.page = page; };
        if(perPage >= 0) { $scope.perPage = perPage; };
    });

    $http.get('/v1/tasks/' + taskId).success(function (data) {
        $scope.task = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    });
});

app.controller('HeaderController', function ($scope, $http, $location, $auth) {
    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path();
    };

    $scope.isAuthenticated = function() {
        <#if githubClientId?? && githubClientSecret??>
        return $auth.isAuthenticated();
        <#else>
        return true;
        </#if>
    };

    $scope.isAuthEnabled = function() {
        <#if githubClientId?? && githubClientSecret??>
        return true;
        <#else>
        return false;
        </#if>
    };
});

app.controller('LoginCtrl', function($scope, $location, $auth) {
    $scope.authenticate = function(provider) {
        $auth.authenticate(provider).then(function(response) {
            //console.log(response);
            console.log($auth.isAuthenticated());
            $location.path('/');
        }).catch(function(error) {
            if (error.error) {
                // Popup error - invalid redirect_uri, pressed cancel button, etc.
                console.log(error.error);
            } else if (error.data) {
                // HTTP response error from server
                console.log(error.data.message, error.status);
            } else {
                console.log(error);
            }
        });
    };
});

app.controller('LogoutCtrl', function($location, $auth) {
    if (!$auth.isAuthenticated()) {
        return;
    }
    $auth.logout().then(function() {
        console.log('You have been logged out');
        $location.path('/');
    });
});

app.controller('ExecuteTaskController', function ($scope, $http, $location, $stateParams, $state, $filter) {
    var taskName = $stateParams.taskName;
    var taskId = $stateParams.taskId;
    var id = 0;

    $scope.payloads = [];

    $scope.executeTask = function() {
      var data = [];
      $scope.payloads.forEach(function(e){
        data.push(e['value']);
      });
      $http.post('/v1/tasks/' + taskName + '/_execute', { payload: data }).success(function (data) {
          $state.go('executions',{'taskId': taskId});
      }).error(function (data, status) {
          console.log('Error ' + data);
      });
    }

    $scope.addNewPayload = function() {
        $scope.payloads.push({ id: id + 1 });
        id += 1;
    }

    $scope.removePayload = function(id) {
        var objToDelete = $filter('filter')($scope.payloads, function (d) {return d.id === id;});
        $scope.payloads.splice($scope.payloads.indexOf(objToDelete),1);
    };

    $http.get('/v1/tasks/' + taskId).success(function (data) {
        $scope.task = data;
    }).error(function (data, status) {
        console.log('Error ' + data);
    });
});

app.controller('SettingsController', function ($scope, $http, $location, $state) {
   $scope.data = {};
   $http.get('/v1/settings').success(function (data) {
      $scope.data = data;
   }).error(function (data,status){
      console.log('Error ' + data);
   });

   $scope.generateKey = function() {
      $http.post('/v1/settings/apiKey').success(function (data) {
          $state.reload();
      }).error(function (data,status){
          console.log('Error ' + data);
      });
   };
});