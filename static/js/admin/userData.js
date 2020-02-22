//# sourceURL=userData.js
$(function () {
    // 获取用户信息并填充
    getUserData();

    /* 编辑用户信息*/
    //用户名
    $("#userDataNameChange").click(function () {
        //使input可编辑
        $("#userDataName").removeAttr("readonly").focus();
        $("#userDataNameChange").css("display", "none");
        $("#userDataNameChangeSubmit").css("display", "block");
    });
    $("#userDataNameChangeSubmit").click(function () {
        //提交修改
        userDataNameChange();
        return false;
    });

    //手机号
    $("#userDataPhoneNumberChange").click(function () {
        //使input可编辑
        $("#userDataPhoneNumber").removeAttr("readonly").focus();
        $("#userDataPhoneNumberChange").css("display", "none");
        $("#userDataPhoneNumberChangeSubmit").css("display", "block");
    });
    $("#userDataPhoneNumberChangeSubmit").click(function () {
        //提交修改
        userDataPhoneNumberChange();
        return false;
    });

    //邮箱
    $("#userDataEmailChange").click(function () {
        //使input可编辑
        $("#userDataEmail").removeAttr("readonly").focus();
        $("#userDataEmailChange").css("display", "none");
        $("#userDataEmailChangeSubmit").css("display", "block");
    });
    $("#userDataEmailChangeSubmit").click(function () {
        //提交修改
        userDataEmailChange();
        return false;
    });

    //QQ
    $("#userDataQqChange").click(function () {
        //使input可编辑
        $("#userDataQq").removeAttr("readonly").focus();
        $("#userDataQqChange").css("display", "none");
        $("#userDataQqChangeSubmit").css("display", "block");
    });
    $("#userDataQqChangeSubmit").click(function () {
        //提交修改
        userDataQqChange();
        return false;
    });

    //微信
    $("#userDataWechatChange").click(function () {
        //使input可编辑
        $("#userDataWechat").removeAttr("readonly").focus();
        $("#userDataWechatChange").css("display", "none");
        $("#userDataWechatChangeSubmit").css("display", "block");
    });
    $("#userDataWechatChangeSubmit").click(function () {
        //提交修改
        userDataWechatChange();
        return false;
    });

});

/**
 * 加载/刷新页面时,获取用户信息
 */
function getUserData() {
    //发送获取用户信息请求
    $.ajax({
        //请求地址
        url: "/java/getUserData",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {},
        //数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (userData) {
            console.log(userData);
            $("#userDataName").attr("value", userData.userName);
            $("#userDataPhoneNumber").attr("value", userData.phoneNumber);
            $("#userDataEmail").attr("value", userData.email);
            $("#userDataQq").attr("value", userData.qq);
            $("#userDataWechat").attr("value", userData.wechat);
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });

}


/**
 * 修改用户名
 */
function userDataNameChange() {
    //获取用户名
    var userDataName = $("#userDataName").val();
    if (userDataName == "") {
        $("#userDataNameTip").html("姓名不能为空!");
        return false;
    }

    $.ajax({
        //请求地址
        url: "/java/userDataChange/userName",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {
                "newData": userDataName
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == '0') {
                //提示成功
                $("#userDataNameTip").html("用户名修改成功!");
                //按钮复原
                $("#userDataName").attr("readonly", "readonly");
                $("#userDataNameChange").css("display", "block");
                $("#userDataNameChangeSubmit").css("display", "none")
                //延迟1s,取消提示
                setTimeout(function () {
                    $("#userDataNameTip").html("");
                }, 1000);
            } else {
                $("#userDataNameTip").html("用户名修改失败,请检查是否登录或用户名是否合法!");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}


/**
 * 修改手机号
 */
function userDataPhoneNumberChange() {
    //获取用户名
    var userDataPhoneNumber = $("#userDataPhoneNumber").val();
    if (userDataPhoneNumber == "") {
        $("#userDataPhoneNumberTip").html("手机号不符合格式!");
        return false;
    }
    $.ajax({
        //请求地址
        url: "/java/userDataChange/phoneNumber",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {
                "newData": userDataPhoneNumber
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == '0') {
                //提示成功
                $("#userDataPhoneNumberTip").html("手机号修改成功!");
                //按钮复原
                $("#userDataPhoneNumber").attr("readonly", "readonly");
                $("#userDataPhoneNumberChange").css("display", "block");

                //延迟1s,取消提示
                setTimeout(function () {
                    $("#userDataPhoneNumberTip").html("");
                }, 1000);
            } else if (result = "-4") {
                $("#userDataPhoneNumberTip").html("当前用户缓存更新出错,请重新登录!");
            } else {
                $("#userDataPhoneNumberTip").html("手机号修改失败,请检查是否登录!");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}


/**
 * 修改邮箱
 */
function userDataEmailChange() {
    //获取用户名
    var userDataEmail = $("#userDataEmail").val();
    if (userDataEmail == "") {
        $("#userDataEmailTip").html("邮箱不能为空!");
        return false;
    }
    $.ajax({
        //请求地址
        url: "/java/userDataChange/email",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {
                "newData": userDataEmail
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == '0') {
                //提示成功
                $("#userDataEmailTip").html("邮箱修改成功!");
                //按钮复原
                $("#userDataEmail").attr("readonly", "readonly");
                $("#userDataEmailChange").css("display", "block");

                //延迟1s,取消提示
                setTimeout(function () {
                    $("#userDataEmailTip").html("");
                }, 1000);
            } else {
                $("#userDataEmailTip").html("邮箱修改失败,请检查是否登录!");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}


/**
 * 修改qq
 */
function userDataQqChange() {
    //获取用户名
    var userDataQq = $("#userDataQq").val();
    if (userDataQq == "") {
        $("#userDataQqTip").html("QQ不能为空!");
        return false;
    }
    $.ajax({
        //请求地址
        url: "/java/userDataChange/qq",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {
                "newData": userDataQq
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == '0') {
                //提示成功
                $("#userDataQqTip").html("QQ修改成功!");
                //按钮复原
                $("#userDataQq").attr("readonly", "readonly");
                $("#userDataQqChange").css("display", "block");

                //延迟1s,取消提示
                setTimeout(function () {
                    $("#userDataQqTip").html("");
                }, 1000);
            } else {
                $("#userDataQqTip").html("QQ修改失败,请检查是否登录!");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}


/**
 * 修改微信
 */
function userDataWechatChange() {
    //获取用户名
    var userDataWechat = $("#userDataWechat").val();
    if (userDataWechat == "") {
        $("#userDataWechatTip").html("微信不能为空!");
        return false;
    }
    $.ajax({
        //请求地址
        url: "/java/userDataChange/wechat",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {
                "newData": userDataWechat
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == '0') {
                //提示成功
                $("#userDataWechatTip").html("微信修改成功!");
                //按钮复原
                $("#userDataWechat").attr("readonly", "readonly");
                $("#userDataWechatChange").css("display", "block");

                //延迟1s,取消提示
                setTimeout(function () {
                    $("#userDataWechatTip").html("");
                }, 1000);
            } else {
                $("#userDataWechatTip").html("微信修改失败,请检查是否登录!");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}
