/**
 * Created by kos on 24.09.2014.
 */
function sendData() {
    var messageEl = document.getElementById('messageInput');
    var messageObj = {sender: enteredUser, text: messageEl.value, recipients: []};
    Chat.socket.send(angular.toJson(messageObj));
    messageEl.value = "";
}

function authInfo(response) {
    if (response.session) {
        VK.Api.call('users.get', {uids: response.session.mid, fields: "screen_name,photo_200_orig"}, function (r) {
            if (r.response) {

                Chat.initialize('/ws/chat?screen_name=' + r.response[0].screen_name +
                    '&first_name=' + r.response[0].first_name +
                    '&last_name=' + r.response[0].last_name +
                    '&photo_200_orig=' + r.response[0].photo_200_orig, 'blob');
                Chat.socket.onmessage = function (evt) {
                    var dataObj = angular.fromJson(evt.data);
                    switch (dataObj.eventType) {
                        case 'MESSAGE':
                            var el = document.getElementsByClassName('ng-scope')[1];
                            var scope = angular.element(el).scope()
                            scope.pushToArray(dataObj.message);
                            scope.$apply();
                            break;
                        case 'USER_CONNECTED':
                            if (enteredUser == null) {
                                enteredUser = dataObj.member.nick;
                            }
                            break;
                        case 'USER_DISCONNECTED':
                            break;
                    }
                }
            }
        });
    } else {
        Chat.socket.close();
    }
}

VK.init({
    apiId: 4551676
});