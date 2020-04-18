$(function () {
    //配置索引
    actIndex = 0;

    //获取更多
    getUniversityAss();

});



/**
 * 全部社团的页面
 */
function getUniversityAss() {
    $.ajax({
        //请求地址
        url: "/java/getUniversityAss",
        //请求类型
        type: "POST",
        //发送的数据
        data: {
            "index": actIndex
        },
        //接受响应的数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (asses) {
            // console.log(asses);
            if (asses.length == 0) {
                alert("请检查是否登录, 如果已登录, 说明您所在的学校没有注册任何社团信息!");
            }

            $.each(asses, function (index, ass) {
                var assUuid = '"' + ass.assUuid + '"';
                $("#asses").append("" +
                    "<div class='row align-items-center bg-gradient act-name'>" +
                    "        <div class='assName col font-weight-bold'>"+ass.assName+"</div>" +
                    "        <div class='text-white col-md-4'><small>"+ass.universityName+"</small></div>" +
                    "        <div class='col-md-2'><a onclick='sentAssUuid("+assUuid+")'>>>>详细信息<<< </a></div>" +
                    "</div>");
            });
        },
        //响应失败执行的方法
        error: function () {
            console.log("服务器响应失败!")
        }
    });
}


/**
 * 用户查看社团详细信息时, 先发该社团id到session中
 * @param assUuid
 */
function sentAssUuid(assUuid) {
    $.ajax({
        //请求地址
        url: "/java/sentAssUuid",
        //请求类型
        type: "GET",
        data: {
                "assUuid": assUuid
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == "0") {
                redirect = true;
                window.location = "/association/html/assDetail.html";
            } else {
                return false;
            }
        },
        //响应失败执行的方法
        error: function () {
            console.log("服务器响应失败!")
        }
    });
}
