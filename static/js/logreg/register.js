//# sourceURL=register.js
$(function () {
    $("#registerSubmit").click(function () {
        sendRegisterInfo();
        return false;
    });

    //使注册的弹窗生效
    $(function () {
        $('[data-toggle="popover"]').popover()
    })
});

//发送用户注册的数据
function sendRegisterInfo() {
    /*
    获取数据并检测
     */
    //用户名
    var registerUserName = $("#registerUserName").val();
    if (registerUserName == "") {
        $("#registerUserName").popover("show");
        setTimeout(function () {
            $("#registerUserName").popover("hide");
        }, 2000);
        return false;
    }

    //密码
    var registerPassword = $("#registerPassword").val();
    if (registerPassword == "") {
        $("#registerPassword").popover("show");
        setTimeout(function () {
            $("#registerPassword").popover("hide");
        }, 2000);
        return false;
    }else{
        //检查密码位数(6~18)
        if (registerPassword.length < 6 || registerPassword.length > 18) {
            $("#registerTip").html("<div class='alert alert-warning' role='alert' style='text-align: center'>密码长度需要在6~18位!</div>");
            return false;
        }
    }
    var registerPassword2 = $("#registerPassword2").val();
    if (registerPassword2 == "") {
        $("#registerPassword2").popover("show");
        setTimeout(function () {
            $("#registerPassword2").popover("hide");
        }, 2000);
        return false;
    }else {
        //对比两次密码是否一致
        if (registerPassword2 != registerPassword) {
            $("#registerTip").html("<div class='alert alert-warning' role='alert' style='text-align: center'>两次密码不一致!</div>");
            return false;
        }
    }
    //信息无误后,密码加密
    var registerPasswordSubmit = hex_md5(registerPassword);

    //手机号
    var registerPhoneNumber = $("#registerPhoneNumber").val();
    if (registerPhoneNumber==null || registerPhoneNumber.trim() == "") {
        $("#registerPhoneNumber").popover("show");
        setTimeout(function () {
            $("#registerPhoneNumber").popover("hide");
        }, 2000);
        return false;
    }else {
        //检验手机号码是否为11位
        if (!/^\d{11}$/.test(registerPhoneNumber)) {
            $("#registerTip").html("<div class='alert alert-warning' role='alert' style='text-align: center'>请检查手机号码格式是否正确!</div>");
            return false;
        }
    }

    //邮箱
    var registerEmail = $("#registerEmail").val();
    var emailReg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
    if(!(registerEmail==null||registerEmail.trim()=="") && !emailReg.test(registerEmail)){
        //检查邮箱格式
        $("#registerTip").html("<div class='alert alert-warning' role='alert' style='text-align: center'>邮箱格式不正确!</div>");
        return false;
    }

    var registerQQ = $("#registerQQ").val();
    var registerWechat = $("#registerWechat").val();



    $.ajax({
        //请求地址
        url: "/java/registerInfo",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {
                "registerUserName": registerUserName,
                "registerPassword": registerPasswordSubmit,
                "registerPhoneNumber": registerPhoneNumber,
                "registerEmail": registerEmail,
                "registerQQ": registerQQ,
                "registerWechat": registerWechat
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == "success") {
                $("#registerTip").html("<div class='alert alert-success' role='alert' style='text-align: center'>注册成功!</div>");
                //停顿1s,关闭注册页面
                setTimeout(function () {
                    $("#register").modal("hide");
                }, 1000);
            } else {
                $("#registerTip").html("<div class='alert alert-danger' role='alert' style='text-align: center'>"+result+"</div>");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}
