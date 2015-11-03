/**
 * Created by kos on 24.09.2014.
 */

var enteredUser = null;

function sendData() {
    var messageEl = document.getElementById('messageInput');
    var messageObj = {sender: enteredUser, text: messageEl.value, recipients: []};
    Chat.socket.send(angular.toJson(messageObj));
    messageEl.value = "";
}

function initChat(response) {
    if (response.session) {
        if (enteredUser == null) {
            VK.Api.call('users.get', {uids: response.session.mid, fields: "screen_name,photo_200_orig"}, function (r) {
                if (r.response) {
                    Chat.initialize('/ws/chat?screen_name=' + r.response[0].screen_name +
                        '&first_name=' + r.response[0].first_name +
                        '&last_name=' + r.response[0].last_name +
                        '&photo_200_orig=' + r.response[0].photo_200_orig, 'blob');
                    Chat.socket.onmessage = function (evt) {
                        var dataObj = angular.fromJson(evt.data);
                        var el = document.getElementsByClassName('ng-scope')[1];
                        var scope = angular.element(el).scope();
                        switch (dataObj.eventType) {
                            case 'MESSAGE':
                                scope.pushToMessages(dataObj.message);
                                scope.$apply();
                                break;
                            case 'USER_CONNECTED':
                                if (enteredUser == null) {
                                    enteredUser = dataObj.member;
                                }
                                scope.pushToMembers(dataObj.member);
                                scope.$apply();
                                break;
                            case 'USER_DISCONNECTED':
                                break;
                        }
                    }
                }
            });
        }
    } else {
        Chat.socket.close();
        enteredUser = null;
    }
}
function authInfo(response) {
    initChat(response);
}

VK.init({
    apiId: 4551676
});

VK.Auth.login(authInfo);