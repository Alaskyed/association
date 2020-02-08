//# sourceURL=checkLogin.js
$(function () {
    // checkLogin();
});

function checkLogin() {
    console.log("haha");
    $.ajax({
        //请求地址
        url: "/java/checkLogin",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {},
        //接受响应的数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (user) {
            if (user.userName == null || user.userName == "") {
                $("#userName").html("<a href=\"#\" data-toggle=\"modal\" data-target=\"#login\">登录|注册</a>");
            } else {
                $("#myAssociation").css("display","block")
                $("#userName").html("<a href='#' class='text-capitalize'>欢迎! " + user.userName + "</a>");
                $("#logout").css("display", "block");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}