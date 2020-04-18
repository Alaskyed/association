//# sourceURL=assAdmin.js
$(function () {
    getAssAdminAssInfo();
});

/**
 * 加载社团管理的板块
 */
function getAssAdminAssInfo() {
    $.ajax({
        //请求址
        url: "/java/getAssAdminAssInfo",
        //请求类型
        type: "POST",
        //发送的数据
        data: {},
        //数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (assAdminAssDatas) {
            $.each(assAdminAssDatas, function (index, assAdminAssData) {
                // console.log(assAdminAssData);
                $("#assAdminAssNames").append(assAdminAssName);
                $("#assAdminAssInfos").append(assAdminAssInfo);
                //加载内容
                $(".assAdminAssUuid").eq(index).html(assAdminAssData.assUuid);
                $(".assAdminAssName").eq(index).html(assAdminAssData.assName);
                $(".assAdminAssName2").eq(index).html(assAdminAssData.assUniversity + " - " + assAdminAssData.assName);
                $(".assAdminAssName3").eq(index).attr("value", assAdminAssData.assName);
                $(".assAdminAssUniversity").eq(index).attr("value", assAdminAssData.assUniversity);
                $(".assAdminAssCustomUrl").eq(index).attr("value", assAdminAssData.customUrl);

                $.each(assAdminAssData.departments, function (i, department) {
                    // console.log(department);
                    $(".departments").eq(index).append("" +
                        "<tr>" +
                        "<td class='addAdminDepartmentUuid' style='display: none'>" + department.departmentUuid + "</td>" +
                        "<td><input type='text' class='form-control-plaintext' readonly='readonly' value='" + department.departmentName + "'></td>" +
                        "<td><textarea class='form-control-plaintext' readonly='readonly'>" + department.departmentDescription + "</textarea></td>" +
                        "<td>" + department.departmentMemberCount + "</td>" +
                        "<td><span class='fa fa-pencil assAdminDepChange'></span> <span class='fa fa-check assAdminDepChangeSubmit' style='display: none;'></span></td>" +
                        "<td><span class='fa fa-close assAdminDepDelete'></span></td>" +
                        "</tr>");
                });
                //新增部门按钮
                $(".departments").eq(index).append("<button class='btn btn-primary fa fa-plus-square-o addAdminDepAdd'>新增部门</button>");
            });

            //显示第一个
            $(".assAdminAssName").eq(0).addClass("active");
            $(".assAdminAssInfo").eq(0).show();

            //点击不同的导航标签显示不同的社团内容
            $(".assAdminAssName").click(function () {
                $(this).addClass("active");
                $(".assAdminAssName").not($(this)).removeClass("active");
                var idx = $(this).index(".assAdminAssName");
                $(".assAdminAssInfo").eq(idx).show(500);
                $(".assAdminAssInfo").not($(".assAdminAssInfo").eq(idx)).hide(500);
            });


            //点击修改社团信息时的操作
            $(".assAdminChange").click(function () {
                //按钮的显示和隐藏
                $(this).css("display", "none");
                $(this).next().css("display", "block");
                $(this).prev().removeAttr("readonly");
            });
            $(".assAdminChangeSubmit").click(function () {
                //发送数据
                assAdminChangeSubmit($(this));
            });

            //点击按钮修改部门信息
            $(".assAdminDepChange").click(function () {
                $(this).css("display", "none");
                $(this).next().css("display", "block");
                //部门名称
                $(this).parent().prev().prev().prev().find("input").removeAttr("readonly");
                //部门介绍
                $(this).parent().prev().prev().find("textarea").removeAttr("readonly");
            });
            $(".assAdminDepChangeSubmit").click(function () {
                //发送数据
                assAdminDepChangeSubmit($(this));
            });

            //点击删除按钮
            $(".assAdminDepDelete").click(function () {
                assAdminDepDelete($(this));
            });

            //点击新增部门添加一行
            $(".addAdminDepAdd").click(function () {
                assAdminAssAddHtml($(this));
            });
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}


/**
 * 修改社团信息
 */
function assAdminChangeSubmit(thisObj) {

    //获取社团uuid
    var assUuid = thisObj.parents(".card-body").siblings(".card-header").find(".assAdminAssUuid").html();
    //获取社团修改类型
    var type = thisObj.parent().find("strong").html();
    //把修改的信息类型换成英文
    if (type == "社团名称") {
        type = "assName";
    } else if (type == "学校") {
        type = "assUniversity";
    } else if (type == "主页") {
        type = "assCustomUrl";
    } else {
        alert("编码有问题,请尝试刷新网页或切换浏览器!")
    }
    //获取新值
    var newData = thisObj.siblings("input").val();
    //发送ajax请求
    $.ajax({
        //请求地址
        url: "/java/assAdminChange/" + type,
        //请求类型
        type: "POST",
        //发送的数据
        data: {
            "assUuid": assUuid,
            "newData": newData
        },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            // console.log(result);
            if (result == "0") {
                //按钮操作
                thisObj.css("display", "none");
                thisObj.prev().css("display", "block");
                thisObj.prev().prev().attr("readonly", "readonly");
                thisObj.next().html("社团信息修改成功!");
                setTimeout(function () {
                    thisObj.next().empty();
                }, 2000);
            } else if (result == "-1") {
                thisObj.next().html("用户信息出错,请检查是否登录!");
            } else if (result == "-3") {
                thisObj.next().html("请检查学校名称是否正确,或者检查自己是否有足够的权限(会长团)!");
            } else {
                thisObj.next().html("修改失败,请重试!");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });

}


/**
 * 修改部门信息
 */
function assAdminDepChangeSubmit(thisObj) {

    //部门名称
    thisObj.parent().prev().prev().prev().find("input").attr("readonly", "readonly");
    //部门介绍
    thisObj.parent().prev().prev().find("textarea").attr("readonly", "readonly");


    //获取数据
    //部门uuid
    var departmentUuid = thisObj.parent().siblings(".addAdminDepartmentUuid").html();
    //部门名称
    var departmentName = thisObj.parent().prev().prev().prev().find("input").val();
    //部门介绍
    var departmentDescription = thisObj.parent().prev().prev().find("textarea").val();

    //发送ajax请求
    $.ajax({
        //请求地址
        url: "/java/assAdminDepChange",
        //请求类型
        type: "POST",
        //发送的数据
        data: {
            "departmentUuid": departmentUuid,
            "departmentName": departmentName,
            "departmentDescription": departmentDescription
        },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            // console.log(result);
            if (result == "0") {
                thisObj.css("display", "none");
                thisObj.prev().css("display", "block");
                thisObj.parents("#departmentsInfo").prev().find(".depTips").html("修改部门信息成功!");
                setTimeout(function () {
                    thisObj.parents("#departmentsInfo").prev().find(".depTips").empty();
                }, 2000);
            } else if (result == "-1") {
                thisObj.next().html("用户信息出错,请检查是否登录!");
            } else if (result == "-4") {
                thisObj.next().html("请检查学校名称是否正确,或者检查自己是否有足够的权限(会长团)!");
            } else {
                thisObj.next().html("修改失败,请重试!");
            }

        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}

/**
 * 删除部门
 */
function assAdminDepDelete(thisObj) {
    if (confirm("是否确认删除这个部门? \n 注意,确认之后,将删除这个部门的所有信息和部门中的成员!!")) {
        //确认删除,获取部门uuid
        var departmentUuid = thisObj.parent().siblings(".addAdminDepartmentUuid").html();
        //发送ajax请求
        $.ajax({
            //请求地址
            url: "/java/assAdminDepDelete",
            //请求类型
            type: "POST",
            //发送的数据
            data: {
                "departmentUuid": departmentUuid
            },
            //数据格式
            dataType: "text",
            //响应成功执行的方法,参数为相应结果
            success: function (result) {
                // console.log(result);
                if (result == "0") {
                    thisObj.css("display", "none");
                    thisObj.prev().css("display", "block");
                    thisObj.parents("#departmentsInfo").prev().find(".depTips").html("部门删除成功!");
                    setTimeout(function () {
                        thisObj.parents("#departmentsInfo").prev().find(".depTips").empty();
                        thisObj.parent().parent().empty();
                    }, 1000);
                } else if (result == "-1") {
                    alert("用户信息出错,请检查是否登录!");
                } else if (result == "-4") {
                    alert("请检查自己是否有足够的权限(会长)!");
                } else if (result == "-5") {
                    alert("检测到你要删除会长团,会长团不能被删除!!");
                } else {
                    alert("删除失败,请重试!");
                }
            },
            //响应失败执行的方法
            error: function () {
                alert("服务器响应失败!")
            }
        });
    } else {
        //不删除
        return false;
    }


}


/**
 * 新增部门表单
 */
function assAdminAssAddHtml(thisObj) {
    //在表格底部添加一行新数据
    thisObj.before("" +
        "<tr>" +
        "<td class='addAdminDepartmentUuid' style='display: none'></td>" +
        "<td><input type='text' class='form-control-plaintext' placeholder='前输入新的部门名称'></td>" +
        "<td><textarea class='form-control-plaintext' placeholder='部门介绍'></textarea></td>" +
        "<td>0</td>" +
        "<td colspan='2' style='display: none;'><a href='#button'>已添加</a></td>" +
        "<td colspan='2'><a href='#button' class='fa fa-check text-primary assAdminDepAddSubmit' onclick='assAdminAssAdd($(this))'>确认添加</a></td>" +
        "</tr>");
}

/**
 * 新增部门
 * @param thisObj
 */
function assAdminAssAdd(thisObj) {
    //获取社团uuid
    var depAddassUuid = thisObj.parents(".card-body").siblings(".card-header").find(".assAdminAssUuid").html();
    //获取社团名称
    var depAddName = thisObj.parent().prev().prev().prev().prev().find("input").val();
    if (depAddName == null || depAddName == "") {
        thisObj.parents("#departmentsInfo").prev().find(".depTips").html("部门名称不能为空!");
        return false;
    }
    //获取社团介绍
    var depAddDescription = thisObj.parent().prev().prev().prev().find("textarea").val();
    //发送ajax请求
    $.ajax({
        //请求地址
        url: "/java/assAdminDepAdd",
        //请求类型
        type: "POST",
        //发送的数据
        data: {
            "assUuid": depAddassUuid,
            "departmentName": depAddName,
            "departmentDescription": depAddDescription
        },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == "0") {
                thisObj.parent().prev().css("display", "block");
                thisObj.parent().css("display", "none");
                thisObj.parents("#departmentsInfo").prev().find(".depTips").html("部门添加成功!");
                setTimeout(function () {
                    thisObj.parents("#departmentsInfo").prev().find(".depTips").empty();
                }, 2000);
            } else if (result == "-1") {
                thisObj.next().html("用户信息出错,请检查是否登录!");
            } else if (result == "-4") {
                thisObj.next().html("请检查自己是否有足够的权限(会长)!");
            } else {
                thisObj.next().html("添加失败,请重试!");
            }

        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });

}


/**
 * 删除社团
 */
function deleteAss(thisObj) {
    var con = confirm("确定删除社团吗?\n这将会清空所有的社团数据, 并删除所有的社团人员!");
    if (con) {
        var assUuid = thisObj.siblings(".assAdminAssUuid").html();
        $.ajax({
            //请求地址
            url: "/java/assAdminDelAss",
            //请求类型
            type: "POST",
            //发送的数据
            data: {
                "assUuid":assUuid
            },
            //数据格式
            dataType: "text",
            //响应成功执行的方法,参数为相应结果
            success: function (result) {
                if (result == "0") {
                    alert("删除成功!");
                    location.reload();
                }else if (result == "-1") {
                    alert("请检查登录!");
                }else if (result == "-2") {
                    alert("删除失败, 请重试!");
                }else if (result == "-3") {
                    alert("权限不够, 只有会长级别才可以删除社团!");
                } else {
                    alert("操作失败!");
                }
            },
            //响应失败执行的方法
            error: function () {
                alert("服务器响应失败!")
            }
        });


    } else {
        return false;
    }
}


/**
 * 加载的部分内容
 * @type {string}
 */
assAdminAssName = "" +
    "<li class='nav-item'>" +
    "   <a class='assAdminAssName nav-link' href='#'></a>" +
    "</li>";

assAdminAssInfo = "<div class='assAdminAssInfo' style='margin-top: 20px; display: none;'>" +
    "    <div class='col-lg-12 mb-5'>" +
    "        <div class='card'>" +
    "            <div class='row card-header'>" +
    "                <h4 class='text-uppercase assAdminAssName2 col-md-10'></h4>" +
    "                <div class='assAdminAssUuid' style='display: none;'></div>" +
    "                <button class='btn btn-primary col-md-2' onclick='deleteAss($(this))'>删除该社团</button>" +
    "            </div>" +
    "            <div class='card-body'>" +
    "                <div class='form-group row'>" +
    "                    <label class='col-md-2 form-control-label'>" +
    "                        <strong class='text-primary'>社团名称</strong>" +
    "                    </label>" +
    "                    <input type='text' class='col-md-3 form-control-plaintext assAdminAssName3' readonly='readonly'/> " +
    "                    <span class='col-md-1 fa fa-pencil assAdminChange'></span>" +
    "                    <span class='col-md-1 fa fa-check assAdminChangeSubmit' style='display: none;'></span>" +
    "                    <span class='' style='color: red;'></span>" +
    "                </div>" +
    "                <div class='form-group row'>" +
    "                    <label class='col-md-2 form-control-label'>" +
    "                        <strong class='text-primary'>学校</strong>" +
    "                    </label>" +
    "                    <input type='text' class='col-md-3 form-control-plaintext assAdminAssUniversity' readonly='readonly'/> " +
    "                    <span class='col-md-1 fa fa-pencil assAdminChange'></span>" +
    "                    <span class='col-md-1 fa fa-check assAdminChangeSubmit' style='display: none;'></span>" +
    "                    <span class='' style='color: red;'></span>" +
    "                </div>" +
    "                <div class='form-group row'>" +
    "                    <label class='col-md-2 form-control-label'>" +
    "                        <strong class='text-primary'>主页</strong>" +
    "                    </label>" +
    "                    <input type='text' class='col-md-3 form-control-plaintext assAdminAssCustomUrl' readonly='readonly'/> " +
    "                    <span class='col-md-1 fa fa-pencil assAdminChange'></span>" +
    "                    <span class='col-md-1 fa fa-check assAdminChangeSubmit' style='display: none;'></span>" +
    "                    <span class='' style='color: red;'></span>" +
    "                </div>" +
    "                <div class='form-group'>" +
    "                   <div class='row'>" +
    "                    <label  class='form-control-label'>" +
    "                        <strong class='text-primary'>部门</strong><span class='depTips' style='color: red;'></span>" +
    "                    </label>" +
    "                   </div>" +
    "                    <div class='row' id='departmentsInfo'>" +
    "                            <table class='table table-hover table-responsive'>" +
    "                                <thead>" +
    "                                    <tr class='text-center'>" +
    "                                        <th>部门名称</th>" +
    "                                        <th>部门介绍</th>" +
    "                                        <th>当前人数</th>" +
    "                                    </tr>" +
    "                                </thead>" +
    "                                <tbody class='departments'>" +
    "" +
    "                                </tbody>" +
    "                            </table>"
"                    </div>" +
"                </div>" +
"" +
"            </div>" +
"        </div>" +
"    </div>" +
"</div>";