//# sourceURL=assSignUpAdmin.js
$(function () {
    //获取社团列表
    getAssSignUpAdminAssName();
    //监听选择的社团
    checkSelect();

    //绑定同意键入按钮
    $("#assSignUpAdminAgree").click(function () {
        assSignUpAdminAgree();
    });

    //绑定拒绝加入按钮
    $("#assSignUpAdminRefuse").click(function () {
        assSignUpAdminRefuse()
    });

});


/**
 * 获取社团名称列表
 */
function getAssSignUpAdminAssName() {
    $.ajax({
        //请求地址
        url: "/java/getAssSignUpAdminAssName",
        //请求类型
        type: "POST",
        //发送的数据
        data: {},
        //数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (assInfos) {
            // console.log(assInfos);
            $.each(assInfos, function (index, assiveInfo) {
                $("#assSignUpAdminActNames").append("<option value='" + assiveInfo.assUuid + "'>" + assiveInfo.assName + " - " +
                    assiveInfo.assName + "-" + assiveInfo.universityName + "</option>");
            });
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });

}


/**
 * 检测select(部门)数量的变化,从而显示相应的报名信息
 */
function checkSelect() {
    $("#assSignUpAdminActNames").change(function () {
        var assUuid = $("#assSignUpAdminActNames").val();
        //如果点击的是空, 清空表格
        if (assUuid == "null") {
            $("#actSignUpAdminTable").empty();
            return false;
        }

        $.ajax({
            //请求地址
            url: "/java/getAssSignUpAdminSignUpInfoList",
            //请求类型
            type: "GET",
            //发送的数据
            data: {
                "assUuid": assUuid
            },
            //数据格式
            dataType: "json",
            //响应成功执行的方法,参数为相应结果
            success: function (signUpInfos) {
                $("#assSignUpAdminTable").empty();
                $.each(signUpInfos, function (index, signUpInfo) {
                    $("#assSignUpAdminTable").append("" +
                        "<tr id='assSignUpId" + signUpInfo.id + "'>" +
                        "<td>" + signUpInfo.assSignUpName + "</td>" +
                        "<td>" + signUpInfo.majorName + "</td>" +
                        "<td>" + signUpInfo.assSignUpGender + "</td>" +
                        "<td>" + signUpInfo.assSignUpDepName + "</td>" +
                        "<td>" + signUpInfo.status + "</td>" +
                        "<td><span class='fa fa-file-text-o assSignUpDetail' onclick='showAssSignUpDetail(" + signUpInfo.id + ")'></span></td>" +
                        "</tr>");
                });
                // console.log(signUpInfos);
            },
            //响应失败执行的方法
            error: function () {
                alert("服务器响应失败!")
            }
        });
    });
}

/**
 * 获取该同学的详细报名信息
 * @param id 该报名信息的id
 */
//这里声明一个全局变量id, 同意或拒绝时, 将对应的id改变状态
assSignUpId = 0;

function showAssSignUpDetail(id) {
    //给全局变量赋值
    assSignUpId = id;

    //发送获取详细信息的消息
    $.ajax({
        //请求地址
        url: "/java/getAssSignUpInfoDetail",
        //请求类型
        type: "POST",
        //发送的数据
        data: {
            "id": id
        },
        //数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (signUpDetail) {
            console.log(signUpDetail);
            $("#assSignUpInfoDetailId").html(signUpDetail.id);
            $("#assSignUpInfoDetailTitle").html(signUpDetail.assSignUpName);
            $("#assSignUpInfoDetailName").html(signUpDetail.assSignUpName);
            $("#assSignUpInfoDetailGender").html(signUpDetail.assSignUpGender);
            $("#assSignUpInfoDetailDep").html(signUpDetail.assSignUpDepName);
            $("#assSignUpInfoDetailIntroduce").html(signUpDetail.assSignUpIntroduce);
            $("#assSignUpInfoDetailReason").html(signUpDetail.assSignUpReason);


            $("#assSignUpInfoDetail").modal();
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });

}


/**
 * 同意加入
 */
function assSignUpAdminAgree() {
    //获取报名表id
    var id = $("#assSignUpInfoDetailId").html();

    $.ajax({
        //请求地址
        url: "/java/assSignUpAdminOption/agree",
        //请求类型
        type: "GET",
        //发送的数据
        data: {
            "id": id
        },
        //数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == "0") {
                $("#assSignUpAdminTips").html("执行成功!");
                setTimeout(function () {
                    $("#assSignUpAdminTips").empty();
                    $("#assSignUpInfoDetail").modal("hide");
                }, 1500);

                //改变该表名的状态
                var test = $("@assSignUpId" + assSignUpId).children().eq(4).html();
            } else {
                $("#assSignUpAdminTips").html("执行失败, 请检查登录重试!");
            }

        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });

}

/**
 * 拒绝加入
 */
function assSignUpAdminRefuse() {
    //获取报名表id
    var id = $("#assSignUpInfoDetailId").html();

    $.ajax({
        //请求地址
        url: "/java/assSignUpAdminOption/refuse",
        //请求类型
        type: "GET",
        //发送的数据
        data: {
            "id": id
        },
        //数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == "0") {
                $("#assSignUpAdminTips").html("执行成功!");
                setTimeout(function () {
                    $("#assSignUpAdminTips").empty();
                    $("#assSignUpInfoDetail").modal("hide");
                }, 1500);
            } else {
                $("#assSignUpAdminTips").html("执行失败, 请检查登录重试!");
            }

        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });

}
