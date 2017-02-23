/**
 * Created by zhangry on 2017/2/23.
 */
;(function(window) {
    var treeSetting;
    var roleResTreeSetting;
    //页面逻辑
    var pageLogic = {

        /*页面初始化*/
        init: {

            //在布局计算前创建控件
            before: function () {
                //定义表格columns数组
                var columns = [
                    { checkbox: true},
                    { field: 'id', title: 'ID', visible: false},
                    { field: 'name', title: '姓名'},
                    { field: 'sex', title: '性别'},
                    { field: 'age', title: '年龄'},
                    { field: 'address', title: '地址'}
                ];

                //定义树配置文件
                treeSetting = {
                    check: {
                        enable: true,
                        chkStyle: "checkbox",
                        chkboxType: {"Y": "ps", "N": "ps"}
                    },
                    data : {
                        key : {
                            name : "NAME"
                        },
                        simpleData : {
                            enable : true,
                            idKey : "ID",
                            pIdKey : "PID"
                        }
                    }
                };

                //定义角色资源树配置文件
                roleResTreeSetting = {
                    check: {
                        enable: true,
                        chkStyle: "checkbox",
                        chkboxType: {"Y": "ps", "N": "ps"}
                    },
                    data : {
                        key : {
                            name : "resourceName"
                        },
                        simpleData : {
                            enable : true,
                            idKey : "id",
                            pIdKey : "pResource.id"
                        }
                    }
                };

                //初始化表格
                var btTable = this.btTable = bt.init({
                    id: "tb_roles",
                    url: masterpage.ctxp + '/roles/search',
                    columns: columns
                });

                //注册表格事件
                btTable.on("check-all.bs.table", function (rows) {

                });

            },

            //布局计算
            layout: function () {
                fristMenuHeight();
            },

            //布局计算后创建控件
            after: function() {
                //设置文本框下拉树， 设置日历等
                /***************** 初始化添加角色弹出层 start ********************/
                pageLogic.roleAddModalObj = $.beamDialog({
                    id: "roleAddModalDiv",
                    title: '角色信息',
                    width: 600,
                    otherButtons: ['保存'],
                    otherButtonStyles: ['btn-primary-ok']
                });
                /***************** 初始化添加角色弹出层 end ********************/

                pageLogic.roleFormValidator = pageLogic.roleAddModalObj.form.validator({
                    rules: {
                        roleName: {
                            required: true,
                            maxlength: 100,
                            remote: {
                                url: masterpage.ctxp + "/roles/roleName",
                                type: "get",
                                data: {
                                    roleName: function() {
                                        return $("#addRoleName").val();
                                    }
                                },
                                beforeSend: function() {
                                    return $("#addRoleName").val() != $("#oldRoleName").val()
                                }
                            }
                        },

                        seq: {
                            required: true,
                            digits:true,
                            min: 0,
                            max: 10000
                        }

                    },

                    messages: {
                        roleName: {
                            required: "角色名不能为空。",
                            maxlength: "最大长度不能超过100",
                            remote: "该角色名称已存在"
                        },

                        seq: {
                            required: "序号不能为空",
                            digits: "必须输入正整数",
                            min: "序号最小0，最大10000",
                            max: "序号最小0，最大10000"
                        }

                    }
                });


                /***************** 初始化添加用户弹出层 start ********************/
                pageLogic.userRoleAddModalObj = $.beamDialog({
                    id: "userRoleAddModalDiv",
                    title: '添加用户',
                    width: 600,
                    otherButtons: ['保存'],
                    otherButtonStyles: ['btn-primary-ok']
                });
                /***************** 初始化添加用户弹出层 end ********************/


                /***************** 初始化角色权限弹出层 start ********************/
                pageLogic.roleResAddModalObj = $.beamDialog({
                    id: "roleResourceAddModalDiv",
                    title: '资源设置',
                    width: 600,
                    otherButtons: ['保存'],
                    otherButtonStyles: ['btn-primary-ok']
                });
                /***************** 初始化角色权限弹出层 end ********************/
            },

            //页面控件事件绑定(一般为按钮的事件绑定)
            events: function() {
                //添加查询按钮click事件
                $("#searchBtn").on("click", function() {
                    searchRole();
                });

                //查询框keydown事件
                $("#roleName, #enabled").on("keydown", function() {
                    if (event.keyCode == 13) {
                        searchRole();
                    }
                });

                /**************角色添加/编辑/删除 start************/
                //添加按钮click事件
                $("#addRoleBtn").on("click", function() {
                    pageLogic.roleAddModalObj.modal("show");
                });

                //编辑按钮click事件
                $("#editRoleBtn").on("click", function() {
                    if (checkSelectRow()) {
                        pageLogic.roleAddModalObj.modal("show");

                        //获取角色基本信息
                        getRole(1, setRoleInfo);
                    }
                });

                //添加保存按钮click事件
                this.roleAddModalObj.btn[0].on("click", function () {
                    if (!pageLogic.roleAddModalObj.form.valid()) {
                        return;
                    }

                    saveRole();
                });

                //添加/编辑弹出层关闭事件
                this.roleAddModalObj.on("hide.bs.modal", function() {
                    //重置form
                    pageLogic.roleAddModalObj.form.get(0).reset();
                    pageLogic.roleFormValidator.resetForm();

                    searchRole();
                });


                //删除按钮click事件
                $("#deleteRoleBtn").on("click", function() {
                    deleteRole();
                });

                /**************角色添加/编辑/删除 end************/



                /**************添加用户 start**************/
                //添加添加用户click事件
                $("#userRoleBtn").on("click", function() {
                    //只能选择一条数据验证
                    if (checkSelectRow()) {
                        //显示添加用户弹出框
                        pageLogic.userRoleAddModalObj.modal("show");
                        //初始添加用户树
                        initZtree(treeSetting);
                        //获取角色基本信息
                        getRole(1, setRoleUserInfo);
                    }
                });

                //添加保存按钮click事件
                this.userRoleAddModalObj.btn[0].on("click", function () {
                    addUserRole();
                });
                /**************添加用户 end**************/



                /**************给角色赋权限 start**************/
                //添加角色权限click事件
                $("#roleResourceBtn").on("click", function() {
                    //只能选择一条数据验证
                    if (checkSelectRow()) {
                        //显示角色资源弹出框
                        pageLogic.roleResAddModalObj.modal("show");
                        //初始化角色资源树
                        roleResInitZtree(roleResTreeSetting);
                        //获取角色基本信息
                        getRole(1, setRoleResInfo);
                    }
                });

                //添加保存按钮click事件
                this.roleResAddModalObj.btn[0].on("click", function () {
                    addRoleResource();
                });
                /**************给角色赋权限 end**************/


            },

            //数据初始化(加载表格数据；其他的数据量大可异步加载的数据（如下拉框树的字典数据）)
            load: function() {

            }
        }

    };

    /*************************添加用户操作 start***************************/
    //树初始化
    function initZtree(setting) {
        $.ajax({
            url: masterpage.ctxp + '/roles/userTree',
            type:'GET', //GET
            timeout:5000,    //超时时间
            dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            success:function(data, textStatus, jqXHR){
                pageLogic.zTree = $.fn.zTree.init($("#treeDemo"), setting, data);
                // pageLogic.zTree.expandAll(true);
            },
            error: function(xhr,textStatus){
            }
        });
    }

    //添加用户角色
    function addUserRole() {
        //取表格的选中行数据
        var rowNums = $("#tb_roles").bootstrapTable('getSelections');
        var roleId = rowNums[0].id;

        var userIdS = getRoleUserOnCkeckId();
        $.ajax({
            type: "post",
            url: masterpage.ctxp + "/roles/userRole",
            contentType: "application/json",
            data: JSON.stringify({
                roleId: roleId,
                userIdS: userIdS
            }),
            success: function (data) {
                if (data) {
                    layer.msg('添加成功！', {icon: 1});
                    pageLogic.userRoleAddModalObj.modal("hide");
                    searchRole();
                } else {
                    layer.msg('添加失败！', {icon: 2});
                }
            },
            error: function () {
                layer.msg('系统异常，请联系管理员！', {icon: 2});
            }
        });
    }

    //设置角色用户信息(修改时，默认选中)
    function setRoleUserInfo(data) {
        var userIdS = "";
        var userArray = [];
        if (data.users && data.users.length > 0) {
            for ( var i = 0; i < data.users.length; i++) {
                userIdS += data.users[i].id + ",";
            }
            userIdS = userIdS.substr(0, userIdS.length - 1);
            userArray = userIdS.split(",");
        }

        var interval = setInterval(function() {
            if (pageLogic.zTree) {
                if (userArray && userArray.length > 0) {
                    for ( var i = 0; i < userArray.length; i++) {
                        var userId = userArray[i];
                        var userNodeArray = pageLogic.zTree.getNodesByParam("ID", userId);
                        for (var j = 0; j < userNodeArray.length; j++) {
                            userNodeArray[j].checked = true;
                            pageLogic.zTree.updateNode(userNodeArray[j], true);
                        }
                    }
                    pageLogic.zTree.expandAll(true);
                }
                clearInterval(interval);
            }
        }, 0);
    }

    //获取checkbox选中数据Id
    function getRoleUserOnCkeckId() {
        var treeObj = pageLogic.zTree;
        var checkedNodeS = treeObj.getCheckedNodes(true);
        var userIdS = "";
        var userIdSArray = [];

        for ( var index = 0; index < checkedNodeS.length; index++) {
            //是否为组织，是：“true”，否：“false”
            var ISORG = checkedNodeS[index].ISORG;
            if (ISORG == "false") {
                userIdS += checkedNodeS[index].ID + ",";
            }
        }

        if (!userIdS) {
            userIdS = userIdS.substr(0, userIdS.length - 1);
        }
        //去除重复的用户Id
        userIdS.replace(/[^,]+/g, function($0, $1) {
            (userIdS.indexOf($0) == $1) && userIdSArray.push($0)
        });

        return userIdSArray.toString();
    }
    /*************************添加用户操作 end***************************/

    /*************************资源设置操作 start***************************/
    //初始化角色资源树
    function roleResInitZtree(setting) {
        $.ajax({
            url: masterpage.ctxp + '/roles/resourceTree',
            type:'GET', //GET
            timeout:5000,    //超时时间
            dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            success:function(data, textStatus, jqXHR){
                pageLogic.roleResZTree = $.fn.zTree.init($("#roleReTreeDemo"), setting, data);
                // pageLogic.roleResZTree.expandAll(true);
            },
            error:function(xhr,textStatus){
            }
        });
    }


    //添加角色资源
    function addRoleResource() {
        //取表格的选中行数据
        var rowNums = $("#tb_roles").bootstrapTable('getSelections');
        var roleId = rowNums[0].id;

        var resourceIdS = getRoleResOnCheckId();
        $.ajax({
            type: "post",
            url: masterpage.ctxp + "/roles/roleResource",
            contentType: "application/json",
            data: JSON.stringify({
                roleId: roleId,
                resourceIdS: resourceIdS
            }),
            success: function (data) {
                if (data) {
                    layer.msg('授权成功！', {icon: 1});
                    pageLogic.roleResAddModalObj.modal("hide");
                    searchRole();
                } else {
                    layer.msg('授权失败！', {icon: 2});
                }
            },
            error: function () {
                layer.msg('系统异常，请联系管理员！', {icon: 2});
            }
        });
    }

    //设置角色资源信息(修改时，默认选中)
    function setRoleResInfo(data) {

        var resourceIdS = "";
        var resourceIdArray = [];
        if (data.resources && data.resources.length > 0) {
            for ( var i = 0; i < data.resources.length; i++) {
                resourceIdS += data.resources[i].id + ",";
            }
            resourceIdS = resourceIdS.substr(0, resourceIdS.length - 1);
            resourceIdArray = resourceIdS.split(",");
        }

        var interval = setInterval(function() {
            if (pageLogic.roleResZTree) {
                if (resourceIdArray && resourceIdArray.length > 0) {
                    for ( var i = 0; i < resourceIdArray.length; i++) {
                        var resourceId = resourceIdArray[i];
                        var resourceNode = pageLogic.roleResZTree.getNodeByParam("id", resourceId);
                        if (resourceNode != null) {
                            resourceNode.checked = true;
                            pageLogic.roleResZTree.updateNode(resourceNode, true);
                        }
                    }
                    pageLogic.roleResZTree.expandAll(true);
                }
                clearInterval(interval);
            }
        }, 0);
    }

    //获取checkbox选中的资源Id
    function getRoleResOnCheckId() {
        var treeObj = pageLogic.roleResZTree;
        var checkedNodeS = treeObj.getCheckedNodes(true);
        var resourceIdS = "";

        for ( var index = 0; index < checkedNodeS.length; index++) {
            resourceIdS += checkedNodeS[index].id + ",";
        }
        if (resourceIdS) {
            resourceIdS = resourceIdS.substr(0, resourceIdS.length - 1);
        }
        return resourceIdS;
    }
    /*************************资源设置操作 end***************************/

    //查询
    function searchRole() {
        //角色名称
        var roleName = $("#name").val();
        //角色描述
        var remarks = $("#address").val();
        //角色是否启用
        var enabled = $("#sex").val();

        //调用表格方法，本方法是刷新列表数据
        pageLogic.btTable.bootstrapTable("refresh", {
            url: masterpage.ctxp + '/roles/search',
            query: {
                name: name,
                address: address,
                sex: sex
            }
        });
    }

    //角色添加/修改
    function saveRole() {
        //角色Id
        var roleId = $("#roleId").val();
        //角色名称
        var roleName = $("#addRoleName").val();
        //是否启用
        var enabled = $("#addEnabled").val();
        //排序
        var seq = $("#addSeq").val();
        //备注
        var remarks = $("#addRemarks").val();

        var url;

        if(roleId) {
            url = masterpage.ctxp + '/roles/' + roleId;
        } else {
            url = masterpage.ctxp + '/roles';
        }

        var type = roleId ? "put" : "post";

        $("#saveRole").attr("disabled", "disabled");
        $.ajax({
            type: type,
            url: url,
            contentType: "application/json",
            data: JSON.stringify({
                roleId: roleId,
                roleName: roleName,
                enabled: enabled,
                seq: seq,
                remarks: remarks
            }),
            success: function (data) {
                if (data) {
                    layer.msg('保存成功！', {icon: 1});
                    pageLogic.roleAddModalObj.modal("hide");

                } else {
                    layer.msg('保存失败！', {icon: 2});
                }
            },
            error: function () {
                layer.msg('系统异常，请联系管理员！', {icon: 2});
            }
        });
    }

    //角色删除
    function deleteRole() {
        //取表格的选中行数据
        var rowNums = $("#tb_roles").bootstrapTable('getSelections');

        var roleIdS = "";
        if (rowNums.length <= 0) {
            alert('请选择有效数据');
            return;
        } else {
            for ( var i = 0; i < rowNums.length; i++) {
                roleIdS += rowNums[i].id + ",";
            }
            roleIdS = roleIdS.substr(0, roleIdS.length-1);
        }

        layer.confirm('您确定要删除吗？', {btn: ['确定','取消']}, function(){
            $.ajax({
                type: "delete",
                url: masterpage.ctxp + "/roles/" + roleIdS,
                data: {
                },
                success: function (data) {
                    if (data) {
                        layer.msg('角色删除成功！', {icon: 1});
                        searchRole();
                    } else {
                        layer.msg('存在关联关系，删除失败！请先解除关联再删除', {icon: 2});
                    }
                },
                error: function () {
                    layer.msg('系统异常，请联系管理员！', {icon: 2});
                }
            });
        });
    }

    //获取角色信息
    function getRole(type, callback) {
        var roleId = $("#tb_roles").bootstrapTable('getSelections')[0].id;
        $.ajax({
            type: "get",
            url: masterpage.ctxp + "/roles/" + roleId,
            data: {
                type: type
            },
            success: function (data) {
                callback(data);
            },
            error: function () {
                layer.msg('系统异常，请联系管理员！', {icon: 2});
            }
        });
        return true;
    }



    //有且只能选择一条数据验证
    function checkSelectRow() {
        //取表格的选中行数据
        var rowNums = $("#tb_roles").bootstrapTable('getSelections');
        if (rowNums.length <= 0) {
            alert('请选择有效数据！');
            return false;
        } else if (rowNums.length > 1) {
            alert('只能选择一条角色数据！');
            return false;
        }
        return true;
    }


    //设置角色基本信息
    function setRoleInfo(data) {
        $("#id").val(data.id);
        $("#name").val(data.name);
        $("#sex").val(data.sex);
        $("#address").val(data.address);
    }

    window.pageLogic = pageLogic;
})(window);