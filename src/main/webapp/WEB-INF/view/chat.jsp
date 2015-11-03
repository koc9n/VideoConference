<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!-- saved from url=(0094)http://svn.apache.org/viewvc/tomcat/tc7.0.x/trunk/webapps/examples/websocket/chat.html?view=co -->
<html ng-app="ChatApp">
<head>
    <title>Enter to Chat</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
    <script src='/resources/angular.js'></script>
    <script src='/resources/ui-bootstrap-tpls-0.11.2.min.js'></script>
    <script src='/resources/controller.js'></script>
    <script src="https://vk.com/js/api/openapi.js"></script>
    <script src='/resources/chat/js/chatsocket.js'></script>
</head>
<body class="container-fluid">
<header>
    <img class="logo" src="" alt=""/>

    <h1 style="text-align: center;">Social Chat</h1>
</header>
<section ng-controller="ChatController">
    <div style="width:400px;height:500px;" class="row marketing">
        <div draggable="true" class="col-sm-10">
            <div class="panel-heading">Chat</div>
            <div>
                <div class="input-group">
                    <input id="messageInput" type="text" class="form-control">
                  <span class="input-group-btn">
                    <button class="btn btn-default" type="button"
                            onclick="sendData();"><span class="glyphicon glyphicon-envelope"></span></button>
                  </span>
                </div>
            </div>
            <br/>

            <div class="highlight" style="overflow: auto;width:auto;height: 600px;">
                <ul class="list-group">
                    <li class="list-group-item" ng-repeat="message in messageArr">
                        <a style="display:block;float:right;" id="thumb" href="http://vk.com/{{message.sender.nick}}"
                           target="_blank"
                           tooltip-placement="left"
                           tooltip="{{message.sender.firstName + ' ' + message.sender.lastName}}">
                            <img style="width:50px;height:auto" ng-src="{{message.sender.photoUrl}}"
                                 class="img-circle img-responsive">
                        </a>

                        <p style="padding-right: 50px;">{{message.text}}</p>

                        <div style="clear: both;"></div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="col-sm-2">
            <ul class="list-group">
                <li class="list-group-item" ng-repeat="member in memberArr">

                </li>
            </ul>
        </div>
    </div>
</section>
<footer>
    <img class="foologo" src="" alt=""/>

    <p>Site is powered by Konstantin Mironchik. All rigths reserved.</p>
</footer>
</body>
<script src='/resources/chat/js/login.js'></script>
</html>