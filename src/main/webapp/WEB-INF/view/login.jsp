<%--
  Created by IntelliJ IDEA.
  User: k.mironchik
  Date: 10/1/2014
  Time: 3:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enter to Chat</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
    <script src='/resources/angular.js'></script>
    <script src='/resources/ui-bootstrap-tpls-0.11.2.min.js'></script>
    <script src='/resources/controller.js'></script>
    <script src="//vk.com/js/api/openapi.js"></script>
</head>
<body class="container">
<header class="page-header">
    <img class="logo" src="" alt=""/>
    <h1 style="text-align: center;">Social Chat</h1>
</header>
<section class="jumbotron">
    <article >
        <h1>Login to chat using your VK account</h1>
        <p class="lead"></p>
        <div class="btn-group-sm">
        <span class="input-group-btn">
            <button id="login_button" class="btn btn-default" type="button"
                    onclick="VK.Auth.login(authInfo);">Login VK
            </button>
            <button class="btn btn-default" type="button" id="logout_button"
                    onclick="VK.Auth.logout(authInfo);">Logout VK
            </button>
        </span>
        </div>
    </article>
</section>
<footer class="page-footer">
    <img class="foologo" src="" alt=""/>
    <p>Site is powered by Konstantin Mironchik. All rigths reserved.</p>
</footer>
</body>
<script src='/resources/chat/js/login.js'></script>
</html>
