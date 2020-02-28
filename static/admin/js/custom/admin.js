$(function () {
    getUserPosition();
});


function getUserPosition() {
    $.ajax({
        //请求地址
        url: "/java/getUserPosition",
        //请求类型
        type: "POST",
        //发送的数据
        data: {},
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (position) {
            //判断返回结果中是否含有对应的职位: 1 会长, 2 部长  3 干事
            if (position.indexOf("1") >= 0) {
                $("#assAdminNav").css("display", "block");
                $("#depAdminNav").css("display", "block");
            }
            if (position.indexOf("2") >= 0) {
                $("#depAdminNav").css("display", "block");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });

}



