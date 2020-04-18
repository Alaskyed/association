$(function () {
    getAssSignUpDetail();

    $("#assSignUpSubmit").click(function () {
        assSignUpSubmit();
    });
});

/**
 * 获取社团的详细信息
 */
function getAssSignUpDetail() {
    $.ajax({
        //请求地址
        url: "/java/getAssSignUpDetail",
        //请求类型
        type: "GET",
        data: {},
        //数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (ass) {
            // console.log(ass);
            $("#assUuid").html(ass.assUuid);
            $("#assDetailName").html(ass.assName);
            $("#assDetailAssName").html(ass.assName);
            $("#assDetailUniversity").html(ass.universityName);

            $.each(ass.depsInfo, function (index, dep) {
                $("#assDetailDeps").append("" +
                    "<tr>" +
                    "   <td>" + dep.departmentName + "</td>" +
                    "   <td>" + dep.departmentDescription + "</td>" +
                    "</tr>");

                $("#assSignUpDep").append("<option value='" + dep.depUuid + "'>" + dep.departmentName + "</option>");
            });
        },
        //响应失败执行的方法
        error: function () {
            console.log("服务器响应失败!")
        }
    });

}

/**
 * 提交报名信息
 */
function assSignUpSubmit() {
    var assUuid = $("#assUuid").html();
    var assSignUpName = $("#assSignUpName").val();
    var assSignUpGender = $("#assSignUpGender").val();
    var assSignUpDepUuid = $("#assSignUpDep").val();
    var assSignUpIntroduce = $("#assSignUpIntroduce").val();
    var assSignUpReason = $("#assSignUpReason").val();

    $.ajax({
        //请求地址
        url: "/java/assSignUp",
        //请求类型
        type: "GET",
        data: {
            "assUuid": assUuid,
            "assSignUpName": assSignUpName,
            "assSignUpGender": assSignUpGender,
            "assSignUpDepUuid": assSignUpDepUuid,
            "assSignUpIntroduce": assSignUpIntroduce,
            "assSignUpReason": assSignUpReason
        },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            // console.log(result);
            if (result == "0") {
                $("#assSignUpTip").html("提交成功!");
                setTimeout(function () {
                    location.reload();
                }, 1000);
            }else if (result == "-5") {
                $("#assSignUpTip").html("你已经是该社团的成员啦, 不需要再次提交报名表了呦!");
            } else {
                $("#assSignUpTip").html("提交失败, 请检查是否登录后重试!");
            }
        },
        //响应失败执行的方法
        error: function () {
            console.log("服务器响应失败!")
        }
    });

}