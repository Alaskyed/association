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
                //没有登录
                $("#userName").html("<a class='btn btn-primary btn-sm' href='#' data-toggle='modal' data-target='#loginForm'>登录|注册</a>")
                $("#logout").css("display", "none");
                $("#myAssociation").css("display", "none");
            } else {
                //已登录
                $("#logreg").css("display", "none");
                $("#myAssociation").css("display", "block");
                $("#userName").html("" +
                    "<li class='nav-item dropdown'>" +
                    "   <a id='userInfo' class='nav-item text-primary dropdown-toggle' href='#' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>" + user.userName + "</a>" +
                    "   <div class='dropdown-menu' aria-labelledby='navbarDropdown' id='userNameMenu'>" +
                    "   </div>" +
                    "</li>" +
                    "");
                $("#logout").css("display", "block");
            }
        },
        //响应失败执行的方法
        error: function () {
            console.log("服务器响应失败!")
        }
    });
}
