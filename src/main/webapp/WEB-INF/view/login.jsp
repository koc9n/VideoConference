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
    <link rel="stylesheet" href="/resources/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/resources/chat/css/chat.css"/>
</head>
<body class="container">
<header class="navbar navbar-default">
    <div class="navbar-header">
        <span class="navbar-brand">Social Chat</span>
    </div>
</header>
<section class="jumbotron">
    <article>
        <div class="auth-block">
            <h1>Login to chat using your VK account</h1>

            <div class="btn-group-lg">
                <a class="btn btn-default btn-block"
                   href="https://oauth.vk.com/authorize?client_id=4551676&scope=friends,photos,offline&redirect_uri=http://192.168.0.102/login&response_type=code&v=5.28"
                   target="_top">Authorize</a>
            </div>
        </div>
    </article>
</section>
<footer class="page-footer">
    <img class="foologo" src="" alt=""/>
    <p>Site is powered by Konstantin Mironchik. All rigths reserved.</p>
</footer>
</body>
</html>
