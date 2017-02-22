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
<link href="${ctxp}/css/bootstrap/bootstrap-modal.css" rel="stylesheet" />
<link href="${ctxp}/css/bootstrap/bootstrap-datetimepicker.css" rel="stylesheet" />
<link href="${ctxp}/css/bootstrap/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
<link href="${ctxp}/css/themes/style-metro.css" rel="stylesheet" type="text/css"/>
<link href="${ctxp}/css/themes/style.css" rel="stylesheet" type="text/css"/>
<link href="${ctxp}/css/themes/style-responsive.css" rel="stylesheet" type="text/css"/>
<!-- 皮肤样式 -->
<link href="themes/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>

<ex-section id="ScriptHead">
    <script type="text/javascript" src="${ctxp}/js/ui/bootstrap-table.js"></script>
    <script type="text/javascript" src="${ctxp}/js/modules/userList.js"></script>
</ex-section>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div id="mainGrid">
        <table id="gridTable" class="table table-condensed table-hover table-striped"></table>
    </div>

    <%-- 事件采集框--%>
    <div class="modal fade" id="eventCreateModal" tabindex="-1" role="dialog" aria-labelledby="事件采集">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="关闭">
                        <%--<span aria-hidden="true">&times;</span>--%>
                    </button>
                    <h4 class="modal-title">事件采集</h4>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <tr>
                                    <td class="col-md-2">
                                        <span>时间：</span>
                                    </td>
                                    <td class="col-md-4">
                                        <div class="input-group">
                                            <input class="form-control" type="text">
                                            <div class="input-group-addon">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="col-md-2">
                                        <span>天气：</span>
                                    </td>
                                    <td class="col-md-4">
                                        <input class="form-control" type="text">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="col-md-2">
                                        <span>巡查单位：</span>
                                    </td>
                                    <td class="col-md-4">
                                        <input class="form-control" type="text">
                                    </td>
                                    <td class="col-md-2">
                                        <span>路线方向：</span>
                                    </td>
                                    <td class="col-md-4">
                                        <input class="form-control" type="text">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="col-md-2">
                                        <span>起止桩号：</span>
                                    </td>
                                    <td class="col-md-4">
                                        <input class="form-control" type="text">
                                    </td>
                                    <td class="col-md-2">
                                        <span>经纬度：</span>
                                    </td>
                                    <td class="col-md-4">
                                        <input class="form-control" type="text">
                                    </td>
                                </tr>
                            </table>
                            <table class="table">
                                <tr>
                                    <td class="col-md-2">
                                        <span>事件类型</span>
                                    </td>
                                    <td class="col-md-4">
                                        <label class="radio-inline">
                                            <input type="radio" name="eventType" value="养护" checked>养护
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="eventType" value="路政">路政
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="eventType" value="路网">路网
                                        </label>
                                    </td>
                                </tr>
                            </table>
                            <table class="table">

                            </table>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>1
                </div>
            </div>
        </div>
    </div>
</body>
</html>
