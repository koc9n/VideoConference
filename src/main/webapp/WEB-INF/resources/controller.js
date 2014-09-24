/**
 * Created by k.mironchik on 9/17/2014.
 */
var myApp = angular.module('ChatApp',[]);

myApp.controller('ChatController', ['$scope', function($scope) {
    $scope.messageArr = [];
    $scope.memberArr = [];

    $scope.pushToMessages = function(item) {
        $scope.messageArr.push({
            sender: item.sender,
            text: item.text,
            recipients:item.recipients
        });
    };

    $scope.pushToMembers = function(item) {
        $scope.memberArr.push({
            nick: item.nick,
            photoUrl: item.photoUrl,
            firstName: item.firstName,
            lastName: item.lastName
        });
    };
}]);
