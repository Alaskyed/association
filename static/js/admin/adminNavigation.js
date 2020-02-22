//# sourceURL=adminNavication.js
$(function () {
    //展示第一个

    $(".navs").click(function () {
        $(this).addClass("active");
        $(".navs").not($(this)).removeClass("active");

        //获取点击的href属性
        var idx = $(this).attr("href");

        //添加该属性的div
        addContect(idx);

        //根据获取的id,显示相应的内容
        $(".tab-pane").not($(idx)).css("display", "none");
        $(idx).css("display", "block");
    });

    //垂直导航栏矢焦后自动收起操作
    $("#adminNav").blur(function () {
        setTimeout(function () {
            $("#navbarSupportedContent").collapse('hide');
        }, 300);
    });
});

function addContect(idx) {
    //获取到指定id的对象
    var content = $(idx);
    //截取id名,并且获取对应html文件路径
    var idName = idx.substring(1);
    var htmlPath = "/html/admin/" + idName + ".html";

    //如果获取不到该id的对象,就添加
    if (!content.length > 0) {
        //没有该id对应的内容,创建一个div,并命名id
        $("#content").append("<div id='" + idName + "'class='tab-pane'></div>");

        //导入id对应的HTML文件(显示对应模板的内容)
        $(idx).load(htmlPath);
    } else {
        //检测到该id对象,先清空内容,然后重新加载
        $(idx).empty();
        $(idx).load(htmlPath);
    }


}
