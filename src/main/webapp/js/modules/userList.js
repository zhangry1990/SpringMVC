userListManager = {
    searchData: function(number, size) {
        var params = {
        };
        if (gridTable.pageSize) {
            params.pageSize = gridTable.pageSize; //页面大小
        } else {
            params.pageSize = 1;
        }
        if (gridTable.pageNumber) {
            params.pageNumber = gridTable.pageNumber; //页码
        } else {
            params.pageNumber = 25;
        }

        $.ajax({
            url: "${ctxp}/user/gridTable",
            type: "post",
            data: params,
            dataType: "json",
            success: function(data) {
                gridTable.pageSize = data["pageSize"];
                gridTable.pageNumber = data["pageNumber"];
                gridTable.setDate(data["data"]);
            },
            error: function(errMsg) {
                alert(errMsg.responseText);
            }
        });
        ui.ajax.ajaxPost(
            "${ctxp}/user/gridTable",
            "post",
            params,
            function(result) {
                gridTable.pageSize = result["pageSize"];
                gridTable.setData(result["data"]);
            },
            function(error) {
                alert(error.responseText);
            }
        );
    }
}
; (function($) {
    gridTable = $("#gridTable").bootstrapTable({
        columns: [
            {field: "num", title: "序号", width: 60},
            {field: "id", title: "ID", width: 100},
            {field: "name", title: "事件类型", width: 100},
            {field: "sex", title: "事件描述", width: 180},
            {field: "age", title: "上报时间", width: 120, align: "center"},
            {field: "address", title: "事件来源", width: 180}
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
        url: "${ctxp}/user/gridTable",
        queryParams: function(){
            userListManager.searchData(1, 25);
        },
        onPageChange: function(number, size) {

        }
    });

    $("#searchBtn").click(function () {
        userListManager.searchData(gridTable.pageNumber, gridTable.pageSize);0
    });
})(jQuery);