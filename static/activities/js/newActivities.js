$(function () {
    //检测是否登录
    newActivitesCheckLogin();
    //绑定提交按钮
    $("#newActSubmit").click(function () {
        newActSubmit();
    });

});

/**
 * 检查登录信息,如果
 */
function newActivitesCheckLogin() {
    console.log("检查登录状态...");
    $.ajax({
        //请求地址
        url: "/java/checkLogin",
        //请求类型
        type: "POST",
        //发送的数据
        data: {},
        //接受响应的数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (user) {
            if (user.userName == null || user.userName == "") {
                alert("请先登录");
                window.location = "/";
            } else {
                newActivitesGetInfo();
            }
        },
        //响应失败执行的方法
        error: function () {
            console.log("服务器响应失败!")
        }
    });
}

/**
 * 从服务器获取这个人的信息(身为会长的社团)
 */

function newActivitesGetInfo() {
    $.ajax({
        //请求地址
        url: "/java/newActivitesGetInfo",
        //请求类型
        type: "POST",
        //发送的数据
        data: {},
        //接受响应的数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (assName) {
            $.each(assName, function (index, ass) {
                if (index == 1) {
                    $("#newActAss").append("<option selected value='" + ass.assUuid + "'>" + ass.assName + "</option>");
                } else {
                    $("#newActAss").append("<option value='" + ass.assUuid + "'>" + ass.assName + "</option>");
                }


            });

        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}

/**
 * 确认提交新的活动
 */
function newActSubmit() {
    var newActAssUuid = $("#newActAss option:selected").val();
    var newActName = $("#newActName").val();
    if (newActName == null || newActName == "") {
        $("#newActTips").html("请添加活动名称!!");
        return false;
    }

    var newActContent = $("#newActContent").val();
    var newActContactType = $("#newActContactType option:selected").val();
    //联系方式
    var newActContactNumber = $("#newActContactNumber").val();
    if (newActContactNumber == null || newActContactNumber == "") {
        $("#newActTips").html("请添加联系方式!");
        return false;
    }
    var newActContact = newActContactType + ": " + newActContactNumber;
    //开始时间
    var newActStartDate = $("#newActStartDate").val();
    var newActStartTime = $("#newActStartTime").val();
    var newActStart = newActStartDate + " " + newActStartTime;
    var newActTimeimeReg = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}$/
    if (!newActTimeimeReg.test(newActStart)) {
        $("#newActTips").html("请输入准确的活动开始时间!!!");
        return false;
    }
    //结束时间
    var newActEndDate = $("#newActEndDate").val();
    var newActEndTime = $("#newActEndTime").val();
    var newActEnd = newActEndDate + " " + newActEndTime;
    if (!newActTimeimeReg.test(newActEnd)) {
        $("#newActTips").html("请输入准确的活动结束时间!!!");
        return false;
    }
    //报名截止时间
    var newActDeadLIneDate = $("#newActDeadLineDate").val();
    var newActDeadLineTime = $("#newActDeadLIneTime").val();
    var newActDeadLIne = newActDeadLIneDate + " " + newActDeadLineTime;
    if (!newActTimeimeReg.test(newActDeadLIne)) {
        $("#newActTips").html("请输入准确的报名截止时间!!!");
        return false;
    } else {
        var a = new Date(newActStart);
        var b = new Date(newActEnd);
        var c = new Date(newActDeadLIne);
        if (a > b) {
            $("#newActTips").html("开始时间不能大于结束时间!!!");
            return false;
        }
        if (c > b) {
            $("#newActTips").html("报名截止时间不能大于结束时间!!!");
        }
    }
    var newActAddress = $("#newActAddress").val();
    var newActRemarks = $("#newActRemarks").val();
    var newActIsOpen = $("input[name='newActIsOpen']:checked").val();
    var newActPic = $("#newActPic")[0].files[0];
    var newActEnclosure = $("#newActEnclosure")[0].files[0];

    //创建一个对象存储信息
    var newAct = new FormData();
    //插入数据
    newAct.set("newActAssUuid", newActAssUuid);
    newAct.set("newActName", newActName);
    newAct.set("newActContent", newActContent);
    newAct.set("newActContact", newActContact);
    newAct.set("newActStart", newActStart);
    newAct.set("newActEnd", newActEnd);
    newAct.set("newActDeadLine", newActDeadLIne);
    newAct.set("newActAddress", newActAddress);
    newAct.set("newActRemarks", newActRemarks);
    newAct.set("newActIsOpen", newActIsOpen);
    if (newActPic != null) {
        newAct.set("newActPic", newActPic);
        var fileName = newActPic.name;
        //获得文件扩展名
        var fileExtension = fileName.split(".").pop().toLowerCase();
        if (fileExtension != "jpg" && fileExtension != "png" && fileExtension != "bmp" && fileExtension != "jpeg") {
            $("#newActTips").html("图片格式必须是jpg|png|bmp|jpeg !!!");
            return false;
        }
    }
    if (newActEnclosure != null) {
        newAct.set("newActEnclosure", newActEnclosure);
    }

    //发送ajax数据
    $.ajax({
        url: "/static/newActSubmit",
        type: 'POST',
        data: newAct,
        contentType: false,
        processData: false,
        success: function (result) {
            if (result == "0") {
                $("#newActTips").html("添加成功!");
                setTimeout(function () {
                    window.location = "/";
                }, 1500);
            } else {
                $("#newActTips").html("添加失败,请重试!");
            }
        },
        error: function () {
            console.log("服务器请求失败");
        }
    });


}