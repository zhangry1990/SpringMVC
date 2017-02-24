// userListManager = {
//     searchData : function (number, size) {
//         var params = {
//             pageNumber: number ? number : 1,
//             pageSize: size ? size : 25
//         };
//         alert(params);
//         $.ajax({
//             url: "http://localhost:8080/SpringMVC/user//gridTable",
//             type: "post",
//             data: JSON.stringify(params),
//             contentType: "application/json; charset=utf-8",
//             dataType: "json",
//             success: function(data) {
//                 //table赋值方式待定
//                 gridTable.pageSize = data["pageSize"];
//                 gridTable.pageNumber = data["pageNumber"];
//                 gridTable.setDate(data["data"]);
//             },
//             error: function(errMsg) {
//                 alert(errMsg.statusText);
//             }
//         });
//     }
// };
/*
(function($) {
    $("#gridTable").bootstrapTable({
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
        url: "../user/gridTable"
        // queryParams: function(){
        //     alert(111);
        //     userListManager.searchData(1, 25);
        // },
        // onPageChange: function(number, size) {
        //     userListManager.searchData(number, size);
        // }
    });
})(jQuery);
*/


