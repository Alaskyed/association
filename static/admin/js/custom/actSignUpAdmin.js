//# sourceURL=actSignUpAdmin.js
$(function () {
    checkSelect();
    getActSignUpAdminActNames();
});

/**
 * 获取活动名称
 */
function getActSignUpAdminActNames() {
    $.ajax({
        //请求地址
        url: "/java/getActSignUpAdminActNames",
        //请求类型
        type: "POST",
        //发送的数据
        data: {},
        //数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (actInfos) {
            // console.log(actInfos);
            $.each(actInfos, function (index, activeInfo) {
                $("#actSignUpAdminActNames").append("<option value='" + activeInfo.actId + "'>" + activeInfo.actName + " - " +
                    activeInfo.assName + "-" + activeInfo.universityName + "</option>");
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
    $("#actSignUpAdminActNames").change(function () {
        var actId = $("#actSignUpAdminActNames").val();
        //如果点击的是空, 清空表格
        if (actId == "null") {
            $("#actSignUpAdminTable").empty();
            return false;
        }

        $.ajax({
            //请求地址
            url: "/java/getActSignUpAdminSignUpInfo",
            //请求类型
            type: "GET",
            //发送的数据
            data: {
                "actId": actId
            },
            //数据格式
            dataType: "json",
            //响应成功执行的方法,参数为相应结果
            success: function (signUpInfos) {
                $("#actSignUpAdminTable").empty();
                $.each(signUpInfos, function (index, signUpInfo) {
                    $("#actSignUpAdminTable").append("" +
                        "<tr>" +
                        "<td>"+signUpInfo.actSignUpName+"</td>" +
                        "<td>"+signUpInfo.universityName+"</td>" +
                        "<td>"+signUpInfo.actSignUpRemarks+"</td>" +
                        "<td>"+signUpInfo.actSignUpTime+"</td>" +
                        "</tr>");
                });


                console.log(signUpInfos);
            },
            //响应失败执行的方法
            error: function () {
                alert("服务器响应失败!")
            }
        });
    });
}
