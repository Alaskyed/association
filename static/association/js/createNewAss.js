//# sourceURL=createNewAss.js

$(function () {
    //检测选取的部门数量
    checkSelect();

    //提交按钮
    $("#creatNewAssSubmit").click(function () {
        creatNewAssSubmit();
    });

});

/**
 * 检测select(部门)数量的变化,从而生成相应的部门数量
 */
function checkSelect() {
    var count = 0;
    //检测select的数值,获取选中的值
    $("#departmentCount").change(function () {
        // console.log($("#departmentCount").val());
        //获取数值
        count = $("#departmentCount").val();

        //生成对应数量的部门信息
        $("#departments").empty();
        for (var i = 0; i < count; i++) {
            $("#departments").append(addDepsHtml);
        }
    });
}

/**
 * 提交
 */
function creatNewAssSubmit() {
    //获取数据
    var associationName = $("#associatinName").val();
    var universityName = $("#universityName").val();
    var customUrl= $("#customUrl").val();
    var departments=[];
    var departmentCount = $("#departments").find("tr");
    for (i = 0; i < departmentCount.length; i++) {
        //获取部门名称和部门介绍
        var depName = $("#departments").find("input").eq(i).val();
        var depDescription = $("#departments").find("textarea").eq(i).val();

        //将对象添加到数组
        departments.push({
            "departmentName":depName,
            "departmentDescription":depDescription
        });
    }
    //整合数据
    var jsonString = {
        "associationName": associationName,
        "universityName":universityName,
        "customUrl": customUrl,
        "departments": departments
    };

    // console.log(jsonString);

    $.ajax({
        //请求地址
        url: "/java/createNewAss",
        //请求类型
        type: "POST",
        //发送的数据
        data: JSON.stringify(jsonString),
        contentType: 'application/json;charset=utf-8',
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == "0") {
                $("#createNewAssTip").html("创建成功!即将自动跳转的控制台...");
                setTimeout(function () {
                    window.location = "../../admin/admin.html";
                }, 2000);
            } else if (result == "-1") {
                $("#createNewAssTip").html("创建社团出错,请检查用户是否登录");
                setTimeout(function () {
                    window.location = "../../admin/admin.html";
                }, 3000);
            }else if (result == "-2") {
                $("#createNewAssTip").html("创建社团出错!");
            }else if (result == "-4") {
                $("#createNewAssTip").html("创建社团出错!学校名称不正确!");
                $("#universityName").focus();
            }else if (result == "-6") {
                $("#createNewAssTip").html("创建社团出错!在该学校中该社团已经注册,请联系该社团的负责人删除!");
            }

        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });


}


//html模板
addDepsHtml = "<tr>" +
    "<td><input type='text' placeholder='部门名称' class='form-control'></td>" +
    "<td><textarea class='form-control'></textarea></td>" +
    "</tr>";
