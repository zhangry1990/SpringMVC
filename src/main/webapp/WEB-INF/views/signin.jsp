<%--
  Created by IntelliJ IDEA.
  User: zhangry
  Date: 2017/2/22
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctxp" value="${pageContext.request.contextPath}"></c:set>
<html>
    <head>
        <meta name="viewport" content="width=device-width, minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login</title>
        <!-- Bootstrap core CSS -->
        <link href="${ctxp}/css/bootstrap/bootstrap.css" rel="stylesheet">

        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <link href="${ctxp}/css/bootstrap/ie10-viewport-bug-workaround.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="${ctxp}/css/themes/signin.css" rel="stylesheet">

    </head>
    <body>
        <div class="container">
            <form class="form-signin" id="loginForm" name="loginForm" action="${ctxp}/login" method="POST">
                <h2 class="form-signin-heading">Please sign in</h2>
                <label for="username" class="sr-only"></label>
                <input type="text" id="username" name="username" class="form-control" placeholder="username" value="admin"
                       required autofocus>
                <label for="password" class="sr-only">Password</label>
                <input type="password" id="password" name="password" autocomplete="off" class="form-control"
                       placeholder="password" value="1" required>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> Remember me
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>
            <div id="errorPanel"></div>
        </div>
        <script src="${ctxp}/js/bootstrap/ie10-viewport-bug-workaround.js"></script>
    </body>
</html>
