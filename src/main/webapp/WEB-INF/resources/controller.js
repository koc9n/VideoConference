/**
 * Created by k.mironchik on 9/17/2014.
 */
var myApp = angular.module('ChatApp',[]);

myApp.controller('ChatController', ['$scope', function($scope) {
    $scope.messageArr = messages;

    $scope.pushToArray = function(item) {

        // Add new item to collection.
        $scope.messageArr.push({
            sender: item.sender,
            text: item.text,
            recipients:item.recipients
        });

    };
}]);
