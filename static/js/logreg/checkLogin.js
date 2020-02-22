//# sourceURL=checkLogin.js
$(function () {
    checkLogin();
});

function checkLogin() {
    console.log("检查登录状态...");
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
                $("#userName").html("<a class='btn btn-primary btn-sm' href='#' data-toggle='modal' data-target='#loginForm' style='color: #ffffff'>登录|注册</a>")
                $("#logout").css("display", "none");
                $("#myAssociation").css("display","none");
            } else {
                $("#logreg").css("display", "none");
                $("#myAssociation").css("display","block");
                $("#userName").html("<a href='#'>欢迎! " + user.userName + "</a>");
                $("#logout").css("display", "block");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}