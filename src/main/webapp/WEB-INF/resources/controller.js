/**
 * Created by k.mironchik on 9/17/2014.
 */
var myApp = angular.module('ChatApp',['ui.bootstrap']);

myApp.controller('ChatController', ['$scope', function($scope) {
    $scope.messageArr = [];
    $scope.memberArr = [];

    $scope.pushToMessages = function(item) {
        $scope.messageArr.unshift({
            sender: item.sender,
            text: item.text,
            recipients: item.recipients,
            member: item.member
        });
    };

    $scope.pushToMembers = function(item) {
        $scope.memberArr.unshift({
            nick: item.nick,
            photoUrl: item.photoUrl,
            firstName: item.firstName,
            lastName: item.lastName
        });
    };
}]);
