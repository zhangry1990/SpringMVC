<%--
  Created by IntelliJ IDEA.
  User: zhangry
  Date: 2017/2/23
  Time: 14:02
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
    <script type="text/javascript" src="${ctxp}/js/ui/bootstrap-table.js"></script>
</ex-section>
<ex-section id="ScriptHead">
</ex-section>

<ex-section id="CSS">
</ex-section>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="row-fluid">
    <p class="breadcrumb">
        您所在的位置：基础信息 > 角色管理
    </p>
</div>

<div class="row-fluid">
    <form class="form-inline searchForm" id="searchForm">
        角色名称：<input type="text" id="name" class="form-control">

        <div class="form-group">
            <label for="sex" class="control-label">角色是否启用：</label>

            <select id="sex" class="m-wrap" tabindex="1">

                <option value="">全部</option>

                <option value="1">是</option>

                <option value="0">否</option>

            </select>
        </div>
        <button id="searchBtn" type="button" class="btn green">查 询</button>
        <div class="pull-right">
            <button id="addRoleBtn" type="button" class="btn red">添 加</button>
            <button id="editRoleBtn" type="button" class="btn red">编辑</button>
            <button id="deleteRoleBtn" type="button" class="btn red">删除</button>
            <button id="userRoleBtn" type="button" class="btn red">用户设置</button>
            <button id="roleResourceBtn" type="button" class="btn red">资源设置</button>
        </div>
    </form>
</div>

<div class="row-fluid">
    <div class="portlet-body span12">
        <table id="tb_roles"></table>

    </div>
    <!-- END EXAMPLE TABLE PORTLET-->
</div>

<%--添加/编辑模块 start--%>
<div id="roleAddModalDiv" style="display: none">
    <form class="form-horizontal formDialog">
        <div class="form-group">
            <label for="addRoleName" class="col-md-2 control-label"><i>*</i>角色名称</label>
            <div class="col-md-10">
                <input id="addRoleName" name="name" type="text" class="form-control" />
            </div>
        </div>

        <div class="form-group">
            <label for="addEnabled" class="col-md-2 control-label">是否启用</label>
            <div class="col-md-10">
                <select id="addEnabled" name="sex" class="form-control">
                    <option value="1">是</option>
                    <option value="0">否</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="addSeq" class="col-md-2 control-label"><i>*</i>排序序号</label>
            <div class="col-md-10">
                <input id="addSeq" name="seq" type="text" class="form-control" placeholder="" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label">角色描述</label>
            <div class="col-md-10">
                <textarea id="addRemarks" class="form-control" rows="3"></textarea>
            </div>
        </div>
        <input type="hidden" id="id" name="id" />
        <input type="hidden" id="oldRoleName" name="oldRoleName" />
    </form>
</div>
<%--添加/编辑模块 end--%>

<%--添加用户模块 start--%>
<div id="userRoleAddModalDiv" style="display: none">
    <form class="form-horizontal formDialog">
        <ul id="treeDemo" class="ztree"></ul>
    </form>
</div>
<%--添加用户模块 end--%>

<%--给角色赋权限（给角色添加资源）start --%>
<div id="roleResourceAddModalDiv" style="display: none">
    <form class="form-horizontal formDialog">
        <ul id="roleReTreeDemo" class="ztree"></ul>
    </form>
</div>
<%--给角色赋权限（给角色添加资源）end --%>
</body>
<ex-section id="ScriptBody">
    <script src="${ctxp}/js/modules/roles.js"></script>
    <script type="text/javascript">
        pageLogic.initData = {
        };
    </script>
</ex-section>
</html>
