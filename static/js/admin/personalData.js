//# sourceURL=personalData.js
$(function () {
    getPersonalDataInfo();

    /* 编个人信息*/
    //学校
    $("#personalDataUniversityChange").click(function () {
        //使input可编辑
        $("#personalDataUniversity").removeAttr("readonly").focus();
        $("#personalDataUniversityChange").css("display", "none");
        $("#personalDataUniversityChangeSubmit").css("display", "block");
    });
    $("#personalDataUniversityChangeSubmit").click(function () {
        //提交修改
        personalDataUniversityChange();
        return false;
    });

    //学院
    // $("#personalDataAcademyChange").click(function () {
    //     //使input可编辑
    //     $("#personalDataAcademy").removeAttr("readonly").focus();
    //     $("#personalDataAcademyChange").css("display", "none");
    //     $("#personalDataAcademyChangeSubmit").css("display", "block").click(function () {
    //         //提交修改
    //         return false;
    //     });
    // });

    //专业
    $("#personalDataMajorChange").click(function () {
        //使input可编辑
        $("#personalDataMajor").removeAttr("readonly").focus();
        $("#personalDataMajorChange").css("display", "none");
        $("#personalDataMajorChangeSubmit").css("display", "block");
    });
    $("#personalDataMajorChangeSubmit").click(function () {
        //提交修改
        personalDataMajorChange();
        return false;
    });

    //姓名
    $("#personalDataNameChange").click(function () {
        //使input可编辑
        $("#personalDataName").removeAttr("readonly").focus();
        $("#personalDataNameChange").css("display", "none");
        $("#personalDataNameChangeSubmit").css("display", "block");
    });
    $("#personalDataNameChangeSubmit").click(function () {
        //提交修改
        personalDataNameChange();
        return false;
    });

    //学号
    $("#personalDataStuIdChange").click(function () {
        //使input可编辑
        $("#personalDataStuId").removeAttr("readonly").focus();
        $("#personalDataStuIdChange").css("display", "none");
        $("#personalDataStuIdChangeSubmit").css("display", "block");
    });
    $("#personalDataStuIdChangeSubmit").click(function () {
        //提交修改
        personalDataStuIdChange();
        return false;
    });

    //年级
    $("#personalDataGradeChange").click(function () {
        //使input可编辑
        $("#personalDataGrade").removeAttr("readonly").focus();
        $("#personalDataGradeChange").css("display", "none");
        $("#personalDataChangeGradeSubmit").css("display", "block");
    });
    $("#personalDataChangeGradeSubmit").click(function () {
        //提交修改
        personalDataGradeChange();
        return false;
    });

});


/**
 * 获取个人信息
 */
function getPersonalDataInfo() {
    //发送请求个人信息的数据
    $.ajax({
        //请求地址
        url: "/java/getPersonalData",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {},
        //数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (personalData) {
            $("#personalDataUniversity").attr("value",personalData.university);
            $("#personalDataUniversityShow").html(personalData.university);
            // $("#personalDataAcademy").attr("value",personalData.academy);
            $("#personalDataMajor").attr("value",personalData.major);
            $("#personalDataName").attr("value",personalData.name);
            $("#personalDataNameShow").html(personalData.name);
            $("#personalDataStuId").attr("value",personalData.stuId);
            $("#personalDataGrade").attr("value",personalData.grade);
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}


/**
 * 修改学校名称`
 */
function personalDataUniversityChange() {
    //获取学校名称值
    var personalDataUniversity = $("#personalDataUniversity").val();
    if (personalDataUniversity == "") {
        $("#personalDataUniversityTip").html("学校名称不能为空!");
        return false;
    }
    $.ajax({
        //请求地址
        url: "/java/personalDataChange/university",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {
                "newData": personalDataUniversity
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == '0') {
                //提示成功
                $("#personalDataUniversityTip").html("学校修改成功!");
                //按钮复原
                $("#personalDataUniversity").attr("readonly", "readonly");
                $("#personalDataUniversityChangeSubmit").css("display", "none");
                $("#personalDataUniversityChange").css("display", "block");
                //延迟1s,取消提示
                setTimeout(function () {
                    $("#personalDataUniversityTip").html("");
                }, 2000);
            }else if(result=='-5'){
                $("#personalDataUniversityTip").html("学校修改失败,请检查学校全称是否正确,如果系统仍旧无法识别您的学校名称,请填写'其他'! ");
            } else {
                $("#personalDataUniversityTip").html("学校修改失败,请检查是否登录或用户名是否合法!");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}


/**
 * 修改学院名称
 */
// function personalDataAcademyChange() {
//     //获取用户名
//     var personalDataAcademy = $("#personalDataAcademy").val();
//     if (personalDataAcademy == "") {
//         $("#personalDataAcademyTip").html("学院名称不能为空!");
//         return false;
//     }
//     $.ajax({
//         //请求地址
//         url: "/java/personalDataChange/academy",
//         //请求类型
//         type: "POST",
//         //发送的数据
//         data:
//             {
//                 "newData": personalDataAcademy
//             },
//         //数据格式
//         dataType: "text",
//         //响应成功执行的方法,参数为相应结果
//         success: function (result) {
//             if (result == '0') {
//                 //提示成功
//                 $("#personalDataAcademyTip").html("学院修改成功!");
//                 //按钮复原
//                 $("#personalDataAcademy").attr("readonly", "readonly");
//                 $("#personalDataAcademyChange").css("display", "block");
//                 //延迟1s,取消提示
//                 setTimeout(function () {
//                     $("#personalDataAcademyTip").html("");
//                 }, 1000);
//             } else {
//                 $("#personalDataAcademyTip").html("学院修改失败,请检查是否登录或学院名称是否合法!");
//             }
//         },
//         //响应失败执行的方法
//         error: function () {
//             alert("服务器响应失败!")
//         }
//     });
// }



/**
 * 修改专业名称
 */
function personalDataMajorChange() {
    //获取用户名
    var personalDataMajor = $("#personalDataMajor").val();
    if (personalDataMajor == "") {
        $("#personalDataMajorTip").html("专业名称不能为空!");
        return false;
    }
    $.ajax({
        //请求地址
        url: "/java/personalDataChange/major",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {
                "newData": personalDataMajor
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == '0') {
                //提示成功
                $("#personalDataMajorTip").html("专业修改成功!");
                //按钮复原
                $("#personalDataMajor").attr("readonly", "readonly");
                $("#personalDataMajorChangeSubmit").css("display", "none");
                $("#personalDataMajorChange").css("display", "block");
                //延迟1s,取消提示
                setTimeout(function () {
                    $("#personalDataMajorTip").html("");
                }, 2000);
            } else {
                $("#personalDataMajorTip").html("专业修改失败,请检查是否登录或专业名称是否合法!");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}



/**
 * 修改姓名
 */
function personalDataNameChange() {
    //获取用户名
    var personalDataName = $("#personalDataName").val();
    if (personalDataName == "") {
        $("#personalDataNameTip").html("姓名不能为空!");
        return false;
    }
    $.ajax({
        //请求地址
        url: "/java/personalDataChange/name",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {
                "newData": personalDataName
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == '0') {
                //提示成功
                $("#personalDataNameTip").html("姓名修改成功!");
                //按钮复原
                $("#personalDataName").attr("readonly", "readonly");
                $("#personalDataNameChangeSubmit").css("display", "none");
                $("#personalDataNameChange").css("display", "block");
                //延迟1s,取消提示
                setTimeout(function () {
                    $("#personalDataNameTip").html("");
                },2000);
            } else {
                $("#personalDataNameTip").html("姓名修改失败,请检查是否登录或用户名是否合法!");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}



/**
 * 修改学号
 */
function personalDataStuIdChange() {
    //获取学号
    var personalDataStuId = $("#personalDataStuId").val();
    if (personalDataStuId == "") {
        $("#personalDataStuIdTip").html("学号不能为空!");
        return false;
    }
    $.ajax({
        //请求地址
        url: "/java/personalDataChange/stuId",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {
                "newData": personalDataStuId
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == '0') {
                //提示成功
                $("#personalDataStuIdTip").html("学号修改成功!");
                //按钮复原
                $("#personalDataStuId").attr("readonly", "readonly");
                $("#personalDataStuIdChangeSubmit").css("display", "none");
                $("#personalDataStuIdChange").css("display", "block");
                //延迟1s,取消提示
                setTimeout(function () {
                    $("#personalDataStuIdTip").html("");
                }, 1000);
            } else {
                $("#personalDataStuIdTip").html("学号修改失败,请检查是否登录或学号是否合法!");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}



/**
 * 修改年级
 */
function personalDataGradeChange() {
    //获取年级
    var personalDataGrade = $("#personalDataGrade").val();
    if (personalDataGrade == "") {
        $("#personalDataGradeTip").html("年级不能为空!");
        return false;
    }
    $.ajax({
        //请求地址
        url: "/java/personalDataChange/grade",
        //请求类型
        type: "POST",
        //发送的数据
        data:
            {
                "newData": personalDataGrade
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == '0') {
                //提示成功
                $("#personalDataGradeTip").html("年级修改成功!");
                //按钮复原
                $("#personalDataGrade").attr("readonly", "readonly");
                $("#personalDataChangeGradeSubmit").css("display", "none");
                $("#personalDataGradeChange").css("display", "block");
                setTimeout(function () {
                    $("#personalDataGradeTip").html("");
                }, 2000);
            } else {
                $("#personalDataGradeTip").html("年级修改失败,请检查是否登录或用户名是否合法!");
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}
