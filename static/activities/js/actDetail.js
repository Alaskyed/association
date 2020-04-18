$(function () {
    getActDetail();

    $("#actSignUpSubmit").click(function () {
        actSignUpSubmit();
    });
});

/**
 * 获取活动详细信息
 */
function getActDetail() {

    $.ajax({
        //请求地址
        url: "/java/getActDetail",
        //请求类型
        type: "GET",
        //发送的数据
        data: {},
        //数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            console.log(result);
            $("#actId").html(result.actId);
            $("#actName").html(result.actName);
            $("#university").html(result.universityName);
            $("#ass").html(result.assName);
            $("#releaseTime").html(result.releaseTime);
            $("#startTime").html(result.startTime);
            $("#endTime").html(result.endTime);
            $("#deadlineTime").html(result.deadlineTime);
            $("#address").html(result.address);
            $("#content").html(result.actContent);
            $("#contact").html(result.contactInfo);
            $("#remarks").html(result.remarks);

            //这个地方涉及到字符串嵌套的问题, 一定要这么写
            if (result.actEnclosureName == "0") {
                $("#enclosure").html("无");
            } else {
                var param = '"' + result.actEnclosureName + '"';
                $("#enclosure").html("<a href='#' onclick='downloadEnclosure(" + result.actId + "," + param + ")'>" + result.actEnclosureName + "</a>");
            }
        },
        //响应失败执行的方法
        error: function () {
            console.log("服务器响应失败!")
        }
    });

}

/**
 * 下载附件
 * @param actId
 * @param enclosureName
 */
function downloadEnclosure(actId, enclosureName) {
    window.location = "/static/getActEnclosure?actId=" + actId + "&fileName=" + enclosureName;
}


/**
 * 提交报名信息
 */
function actSignUpSubmit() {
    var actSignUpName = $("#actSignUpName").val();
    if (actSignUpName == "" || actSignUpName == null) {
        $("#actSignUpTip").html("姓名不能为空!");
        return false;
    }
    var actSignUpRemarks = $("#actSignUpRemarks").val();
    var actId = $("#actId").html();

    $.ajax({
        //请求地址
        url: "/java/sendActSignUpInfo",
        //请求类型
        type: "GET",
        //发送的数据
        data: {
            "actId": actId,
            "actSignUpName": actSignUpName,
            "actSignUpRemarks": actSignUpRemarks
        },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            console.log(result);
            if (result =="0") {
                $("#actSignUpTip").html("报名成功!");
                setTimeout(function () {
                    window.location.reload();
                }, 1000);
            }else if (result == "-1") {
                $("#actSignUpTip").html("请先登录!");
            }else if (result == "-2") {
                $("#actSignUpTip").html("报名失败, 请重试!");
            }else if (result == "-6") {
                $("#actSignUpTip").html("你已经报名过这个活动啦,请勿重复报名哦!");
            }
        },
        //响应失败执行的方法
        error: function () {
            console.log("服务器响应失败!")
        }
    });


}