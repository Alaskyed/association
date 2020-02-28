//# sourceURL=adminNavication.js
$(function () {
    // Sidebar 点击侧边导航栏伸缩
    $('.sidebar-toggler').on('click', function () {
        $('.sidebar').toggleClass('shrink show');
    });

    //随机变换颜色
    randomOolor();


    //检查登录状态
    checkLogin();

    //退出登录按钮
    $("#signOut").click(function () {
        signOut();
    });

    //显示第一个内容
    $("#firstContext").addClass("active");
    $("#userData").load("/admin/html/userData.html");

    //点击切换内容
    $(".navs").click(function () {
        $(this).addClass("active");
        $(".navs").not($(this)).removeClass("active");

        //获取点击的href属性,即获取要加载的id
        var idx = $(this).attr("href");

        //根据获取的id,显示相应的内容
        $(".tab-pane").not($(idx)).css("display", "none");
        $(idx).css("display", "block");

        //加载内容
        loadContect(idx);
    });

});

/**
 * 获取id之后加载对应的内容
 * @param idx
 */
function loadContect(idx) {
    //获取到指定id的对象
    var content = $(idx);
    //创建该html的路径
    var idName = idx.substring(1);
    var htmlPath = "html/" + idName + ".html";

    //检测到该id对象,先清空内容,然后重新加载
    $(idx).empty();
    $(idx).load(htmlPath);
}

/**
 * 检查用户是否登录
 */
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
                $("#userInfo").html("未登录");
                $("#signOut").css("display", "none");
                $("#signIn").css("display", "block");

            } else {

                $("#userInfo").html("我的");
                $("#signIn").css("display", "none");
                $("#signOut").css("display", "block");
                $("#userInfo").html("<a href='#'>欢迎! " + user.userName + "</a>");
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
function signOut() {
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
            if (result == "success") {
                //注销成功,刷新当前页面
                window.location = "/index.html";
            } else {
                location.reload();
            }
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}

/**
 * 随机变换主题色
 */
function randomOolor() {
    //随机使用一种颜色
    var colors = new Array("default","blue","red","green","violet","sea","pink");
    var randomColor = Math.floor(Math.random()*7);
    $('#theme-color').attr("href", "css/style." + colors[randomColor] + ".css");
}
