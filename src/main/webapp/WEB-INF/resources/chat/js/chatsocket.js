var Chat = {};

Chat.socket = null;
var chatOnOpen = null;

Chat.connect = (function (host, binaryType) {
    if ('WebSocket' in window) {
        Chat.socket = new WebSocket(host);
    } else if ('MozWebSocket' in window) {
        Chat.socket = new MozWebSocket(host);
    } else {
        Console.log('Error: WebSocket is not supported by this browser.');
        return;
    }
    Chat.socket.binaryType = binaryType;
    Chat.socket.onopen = chatOnOpen;
});

Chat.initialize = function (socketUrl, binaryType) {
    if (window.location.protocol == 'http:') {
        Chat.connect('ws://' + window.location.host + socketUrl, binaryType);
    } else {
        Chat.connect('wss://' + window.location.host + socketUrl, binaryType);
    }
};

