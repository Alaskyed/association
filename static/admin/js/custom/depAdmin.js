//# sourceURL=depAdmin.js
$(function () {
    getDepAdminAssInfo();
});

function getDepAdminAssInfo() {
    $.ajax({
        //请求址
        url: "/java/getDepAdminAssInfo",
        //请求类型
        type: "POST",
        //发送的数据
        data: {},
        //数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (depAdminAssDatas) {
            // console.log(depAdminAssDatas);
            //每个社团
            $.each(depAdminAssDatas, function (index, depAdminAssData) {
                //添加社团标题
                $("#depAdminAssNames").append("<li class='nav-item'><a class='depAdminAssName nav-link' href='#'>" +
                    depAdminAssData.assName +
                    "</a></li>");
                $("#depAdminDepInfos").append(depAdminDepInfo);
                //每个部门
                $.each(depAdminAssData.departments, function (depi, department) {
                    //部门的导航栏
                    $(".depNavs").eq(index).append("<a class='flex-sm-fill text-sm-center nav-link depNav' href='#'>" +
                        department.departmentName +
                        "</a>");
                    $(".depTable").eq(index).append("<tbody class='stuInfo' style='display: none;'></tbody>");
                    //人员表
                    $.each(department.members, function (memi, member) {
                        //将职位转为文字
                        if (member.position == "1") {
                            member.position = "会长";
                        }else if (member.position == "2") {
                            member.position = "部长";
                        }else if (member.position == "3") {
                            member.position = "干事";
                        }
                        //这个地方获取的是当前社团下人员表的第几个部门
                        $(".depTable").eq(index).find(".stuInfo").eq(depi).append("<tr>" +
                            "<td class='depAdminDepUuid' style='display: none;'>" + department.departmentUuid + "</td>" +
                            "<td class='depAdminStuUuid' style='display: none;'>" + member.stuUuid + "</td>" +
                            "<td>" + member.stuName + "</td>" +
                            "<td>" + member.position + "</td>" +
                            "<td><span class='fa fa-close deleteStu'></span><span style='color: red'></span></td>" +
                            "</tr>");
                    })
                });
            });


            //显示第一个社团
            $(".depAdminAssName").eq(0).addClass("active");
            $(".depAdminDepInfo").eq(0).show();
            //点击不同的社团名称切换不同的社团
            $(".depAdminAssName").click(function () {
                $(this).addClass("active");
                $(".depAdminAssName").not($(this)).removeClass("active");
                var idx = $(this).index(".depAdminAssName");
                $(".depAdminDepInfo").eq(idx).show(500);
                $(".depAdminDepInfo").not($(".depAdminDepInfo").eq(idx)).hide(500);
            });

            //点击切换部门
            $(".depNav").click(function () {
                //高亮显示
                $(this).addClass("active");
                $(".depNav").not($(this)).removeClass("active");
                var idx = $(this).index(".depNav");
                $(".stuInfo").eq(idx).show(500);
                $(".stuInfo").not($(".stuInfo").eq(idx)).hide(50);
            });

            //删除人员
            $(".deleteStu").click(function () {
                deleteStu($(this));
            });


        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}


/**
 * 删除干事
 */
function deleteStu(thisObj) {
    if (confirm("确定从该部门中删除该成员?")) {
        var depUuid = thisObj.parent().parent().find(".depAdminDepUuid").html();
        var stuUuid = thisObj.parent().parent().find(".depAdminStuUuid").html();

        $.ajax({
            //请求地址
            url: "/java/depAdmin/deleteStu",
            //请求类型
            type: "POST",
            //发送的数据
            data: {
                "depUuid": depUuid,
                "stuUuid": stuUuid
            },
            //数据格式
            dataType: "text",
            //响应成功执行的方法,参数为相应结果
            success: function (result) {
                // console.log(result);
                if (result == "0") {
                    thisObj.parent().parent().empty().css("display", "none");
                } else if (result == "-1") {
                    thisObj.next().html("用户信息出错,请检查是否登录!");
                }else if (result == "-6") {
                    thisObj.next().html("注意,该部门只剩下唯一的管理员,所以不能删除!");
                } else {
                    thisObj.next().html("删除失败,请重试!");
                }
            },
            //响应失败执行的方法
            error: function () {
                alert("服务器响应失败!")
            }
        });
    }else {
        //取消删除
        return false;
    }
}


depAdminDepInfo = "<ul class='depAdminDepInfo' style='margin-top: 20px; display: none;'>" +
    "            <nav class='nav nav-pills flex-column flex-sm-row depNavs' ></nav>" +
    "            <div>" +
    "                <div>" +
    "                    <div class='tab-content' id='v-pills-tabContent'>" +
    "                        <div class='tab-pane fade show active' id='v-pills-home' role='tabpanel'" +
    "                             aria-labelledby='v-pills-home-tab'>" +
    "                                <table class='table depTable'>" +
    "                                    <thead>" +
    "                                    <tr>" +
    "                                        <th style='display: none;'>stuUuid</th>" +
    "                                        <th>姓名</th>" +
    "                                        <th>职位</th>" +
    "                                        <th>操作</th>" +
    "                                    </tr>" +
    "                                    </thead>" +
    "                                </table>" +
    "                        </div>" +
    "                        <div class='tab-pane fade' id='v-pills-profile' role='tabpanel'" +
    "                             aria-labelledby='v-pills-profile-tab'>" +
    "                        </div>" +
    "                    </div>" +
    "                </div>" +
    "            </div>" +
    "        </div>";