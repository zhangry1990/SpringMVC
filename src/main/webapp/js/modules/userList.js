/**
 * Created by zhangry on 2017/2/22.
 */
userListManager = {
    searchData: function () {
        var params = {
            pageSize: gridTable.pageSize, //页面大小
            pageNumber: gridTable.pageNumber //页码
        };
        $.ajax({
            url: "/wxgl/event/gridTable",
            type: "post",
            data: params,
            dataType: "json",
            success: function (data) {
                gridTable.pageSize = data["pageSize"];
                gridTable.pageNumber = data["pageNumber"];
                gridTable.setDate(data["data"]);
            },
            error: function (errMsg) {
                alert(errMsg.responseText);
            }
        });
    }
};
(function ($) {
    gridTable = $("#gridTable").bootstrapTable({
        columns: [
            {field: "num", title: "序号", width: 60},
            {field: "name", title: "事件类型", width: 100},
            {field: "sex", title: "事件描述", width: 180},
            {field: "age", title: "事件来源", width: 180},
            {field: "address", title: "上报时间", width: 120, align: "center"}
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
        url: "/event/gridTable",
        queryParams: function () {
            userListManager.searchData();
        }
    });

    $("#searchBtn").click(function () {
        userListManager.searchData();
    });

})(jQuery);
