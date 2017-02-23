<%--
  Created by IntelliJ IDEA.
  User: zhangry
  Date: 2017/2/22
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxp" value="${pageContext.request.contextPath}"></c:set>

<link href="${ctxp}/css/bootstrap/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="${ctxp}/css/bootstrap/bootstrap-table.css" rel="stylesheet">
<link href="${ctxp}/css/bootstrap/bootstrap-modal.css" rel="stylesheet"/>
<link href="${ctxp}/css/bootstrap/bootstrap-datetimepicker.css" rel="stylesheet"/>
<link href="${ctxp}/css/bootstrap/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
<link href="${ctxp}/css/themes/style-metro.css" rel="stylesheet" type="text/css"/>
<link href="${ctxp}/css/themes/style.css" rel="stylesheet" type="text/css"/>
<link href="${ctxp}/css/themes/style-responsive.css" rel="stylesheet" type="text/css"/>
<!-- 皮肤样式 -->
<link href="${ctxp}/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>

<ex-section id="ScriptHead">
    <script type="text/javascript" src="${ctxp}/js/jquery/jquery-1.12.0.min.js"></script>
    <script type="text/javascript" src="${ctxp}/js/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctxp}/js/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctxp}/js/ui/bootstrap-table.js"></script>
    <script type="text/javascript" src="${ctxp}/js/modules/userList.js"></script>
</ex-section>
<ex-section id="CSS">
    <style type="text/css">
        table thead{display: block;}
    </style>
</ex-section>
<html>
<head>
    <title>UserList</title>
</head>
<body>

<div class="row-fluid">
    <p class="breadcrumb">
        您所在的位置：基础信息 > 用户管理
    </p>
</div>

<div id="mainGrid">
    <table id="gridTable" class="table table-condensed table-hover table-striped"></table>
</div>
</body>
</html>
