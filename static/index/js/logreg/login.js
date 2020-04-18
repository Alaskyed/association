//# sourceURL=login.js
$(function () {
    $("#loginSubmit").click(function () {
        sendLoginInfo();
    });
    $("#logout").click(function () {
        logout();
    });
});

/**
 *  发送登录数据
 */
function sendLoginInfo() {
    var loginUserName = $("#loginUserName").val();
    var loginPassword = $("#loginPassword").val();
    //密码加密
    var loginPasswordSubmit = hex_md5(loginPassword);

    //发送登录数据
    $.ajax({

        //请求地址
        url: "/java/login",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {
                "loginUserName": loginUserName,
                "loginPassword": loginPasswordSubmit
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == "0") {
                $("#loginTip").html("<div class='alert alert-success' role='alert' style='text-align: center'>登录成功!</div>");
                //停顿1s,刷新页面
                setTimeout(function () {
                    location.reload();
                }, 1000);
            } else {
                $("#loginTip").html("<div class='alert alert-warning' role='alert' style='text-align: center'>登录失败,请检查用户名或密码!</div>");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });

}

/**
 * 退出登录
 */
function logout() {
    //发送登录数据
    $.ajax({

        //请求地址
        url: "/java/logout",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {},
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == "0") {
                //注销成功,刷新当前页面
                location.reload();
            } else {
                location.reload();
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败, 请刷新重试!");
            location.reload();
        }
    });

}
