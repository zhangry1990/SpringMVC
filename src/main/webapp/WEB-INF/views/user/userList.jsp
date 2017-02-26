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
    <%--<script type="text/javascript" src="${ctxp}/js/modules/userList.js"></script>--%>
</ex-section>
<ex-section id="CSS">
    <style type="text/css">
        table thead {
            display: block;
        }
    </style>
</ex-section>
<html>
<head>
    <title>UserList</title>


    <script type="text/javascript">
        $(document).ready(function () {
            gridTable = $("#gridTable").bootstrapTable({
                columns: [
                    {field: "num", title: "序号", width: 60},
                    {field: "id", title: "ID", width: 100},
                    {field: "name", title: "姓名", width: 100},
                    {field: "sex", title: "性别", width: 180},
                    {field: "age", title: "年龄", width: 120, align: "center"},
                    {field: "address", title: "地址", width: 180}
                ],
                striped: true,
                pagination: true,
                pageNumber: 1,
                pageSize: 25,
                pageList: [10, 25, 50, 100],
                sidePagination: "server",
                // sortName: "reportTime",
                // sortOrder: "desc",
                // showColumns: true,
                method: "post",
                url: "../user/gridTable",
                queryParams: function () {
                    userListManager.searchData(1, 25);
                },
                onPageChange: function (number, size) {
                    userListManager.searchData(number, size);
                }

            });
            // sss();
        });


        userListManager = {
            searchData: function (number, size) {
                var params = {
                    pageNumber: number ? number : 1,
                    pageSize: size ? size : 25
                };
                $.ajax({
                    url: "http://localhost:8080/SpringMVC/user//gridTable",
                    type: "post",
                    data: JSON.stringify(params),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (data) {
                        //table赋值方式待定
                        gridTable.pageSize = data["pageSize"];
                        gridTable.pageNumber = data["pageNumber"];
                       // gridTable.setDate(data["data"]);
                    },
                    error: function (errMsg) {
                        alert(errMsg.statusText);
                    }
                });
            }
        };


        //        function sss() {
        //            $("#gridTable").bootstrapTable({
        //                columns: [
        //                    {field: "num", title: "序号", width: 60},
        //                    {field: "id", title: "ID", width: 100},
        //                    {field: "name", title: "事件类型", width: 100},
        //                    {field: "sex", title: "事件描述", width: 180},
        //                    {field: "age", title: "上报时间", width: 120, align: "center"},
        //                    {field: "address", title: "事件来源", width: 180}
        //                ]
        //            });
        //        }
    </script>
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
