$(function () {
    //配置索引
    actIndex = 0;

    //获取更多
    allActGetActInfo();
    $("#getMoreAct").click(function () {
        allActGetActInfo();
    });

    //绑定查找按钮
    $("#allActSearchSubmit").click(function () {
        allActSearch();
    });
});

/**
 * 活动页面
 */
function allActGetActInfo() {
    $.ajax({
        //请求地址
        url: "/java/allActGetActInfo",
        //请求类型
        type: "POST",
        //发送的数据
        data: {
            "index": actIndex
        },
        //接受响应的数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (acts) {
            // console.log(acts);
            $.each(acts, function (index, act) {
                if (act.actPicName == "" || act.actPicName == null) {
                    //添加热门活动
                    $("#waterfall").append("" +
                        "<div class='box'>" +
                        "                <div class='card'>" +
                        "                    <div style='display: none;' class='indexActId'>" + act.actId + "</div>" +
                        "                    <a href='#' id='actImg'>" +
                        "                        <img class='card-img-top img-raised actDetail' src='/imgages/default.png' alt='活动图片' onclick='showActDetial($(this))'>" +
                        "                    </a>" +
                        "                    <div class='card-body'>" +
                        "                        <h5 class='card-title mb-2 actDetail'>" + act.actName + "</h5>" +
                        "                        <p class='text-muted small-xl mb-2'>" + act.releaseTime + "</p>" +
                        "                        <p class='text-muted small-xl mb-2'>" + act.universityName + "</p>" +
                        "                        <p class='card-text'>" + act.remarks + "</p>" +
                        "                    </div>" +
                        "                </div>" +
                        "            </div>");
                } else {
                    //添加热门活动
                    $("#waterfall").append("" +
                        "<div class='box'>" +
                        "                <div class='card'>" +
                        "                    <div style='display: none;' class='indexActId'>" + act.actId + "</div>" +
                        "                    <a href='#' id='actImg'>" +
                        "                        <img class='card-img-top img-raised actDetail' src='/imgages/" + act.actId + "/" + act.actPicName + "' alt='活动图片' onclick='showActDetial($(this))'>" +
                        "                    </a>" +
                        "                    <div class='card-body'>" +
                        "                        <h5 class='card-title mb-2 actDetail'>" + act.actName + "</h5>" +
                        "                        <p class='text-muted small-xl mb-2'>" + act.releaseTime + "</p>" +
                        "                        <p class='text-muted small-xl mb-2'>" + act.universityName + "</p>" +
                        "                        <p class='card-text'>" + act.remarks + "</p>" +
                        "                    </div>" +
                        "                </div>" +
                        "            </div>");
                }
            });

            //更新index的值
            if (acts.length < 20) {
                $("#getMoreAct").css("display", "none");
            } else {
                actIndex += acts.length;
            }


        },
        //响应失败执行的方法
        error: function () {
            console.log("服务器响应失败!")
        }
    });
}

/**
 * 查看详情,先发送ActId到服务端session
 */
function showActDetial(thisObj) {
    //获取该活动的id
    var actId = thisObj.parent().siblings(":first").html();
    //创建一个是否跳转标识
    var redirect = false;


    $.ajax({
        //请求地址
        url: "/java/sentActId",
        //请求类型
        type: "GET",
        //发送的数据
        data:
            {
                "actId": actId
            },
        //数据格式
        dataType: "text",
        //响应成功执行的方法,参数为相应结果
        success: function (result) {
            if (result == "0") {
                redirect = true;
                window.location = "/activities/html/actDetail.html";
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

/**
 * 获取关键字查找的结果
 */
function allActSearch() {
    var searchKey = $("#allActSearch").val();

    $.ajax({
        //请求地址
        url: "/java/actSearch",
        //请求类型
        type: "GET",
        //发送的数据
        data:
            {
                "searchKey": searchKey
            },
        //数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (acts) {
            // console.log(acts);
            $("#waterfall").empty();
            $.each(acts, function (index, act) {
                if (act.actPicName == "" || act.actPicName == null) {
                    //添加热门活动
                    $("#waterfall").append("" +
                        "<div class='box'>" +
                        "                <div class='card'>" +
                        "                    <div style='display: none;' class='indexActId'>" + act.actId + "</div>" +
                        "                    <a href='#' id='actImg'>" +
                        "                        <img class='card-img-top img-raised actDetail' src='/imgages/default.png' alt='活动图片' onclick='showActDetial($(this))'>" +
                        "                    </a>" +
                        "                    <div class='card-body'>" +
                        "                        <h5 class='card-title mb-2 actDetail'>" + act.actName + "</h5>" +
                        "                        <p class='text-muted small-xl mb-2'>" + act.releaseTime + "</p>" +
                        "                        <p class='text-muted small-xl mb-2'>" + act.universityName + "</p>" +
                        "                        <p class='card-text'>" + act.remarks + "</p>" +
                        "                    </div>" +
                        "                </div>" +
                        "            </div>");
                } else {
                    //添加热门活动
                    $("#waterfall").append("" +
                        "<div class='box'>" +
                        "                <div class='card'>" +
                        "                    <div style='display: none;' class='indexActId'>" + act.actId + "</div>" +
                        "                    <a href='#' id='actImg'>" +
                        "                        <img class='card-img-top img-raised actDetail' src='/imgages/" + act.actId + "/" + act.actPicName + "' alt='活动图片' onclick='showActDetial($(this))'>" +
                        "                    </a>" +
                        "                    <div class='card-body'>" +
                        "                        <h5 class='card-title mb-2 actDetail'>" + act.actName + "</h5>" +
                        "                        <p class='text-muted small-xl mb-2'>" + act.releaseTime + "</p>" +
                        "                        <p class='text-muted small-xl mb-2'>" + act.universityName + "</p>" +
                        "                        <p class='card-text'>" + act.remarks + "</p>" +
                        "                    </div>" +
                        "                </div>" +
                        "            </div>");
                }
            });

        },
        //响应失败执行的方法
        error: function () {
            console.log("服务器响应失败!")
        }
    });
}