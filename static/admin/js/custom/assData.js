//# sourceURL=assData.js
$(function () {
    //获取社团信息
    getAssInfo();

});


/**
 * 获取社团信息
 */
function getAssInfo() {

    $.ajax({
        //请求地址
        url: "/java/getAssData",
        //请求类型
        type: "POST",
        //发送的数据
        data: {},
        //数据格式
        dataType: "json",
        //响应成功执行的方法,参数为相应结果
        success: function (assDatas) {
            $.each(assDatas, function (index, assData) {
                $("#assNames").append(assNameHtml);
                $("#assInfos").append(assInfoHtml);
                //加载内容
                $(".assName").eq(index).html(assData.assName);
                $(".assName2").eq(index).html(assData.universityName + " - " + assData.assName);
                $(".assDataAssUuid").eq(index).html(assData.assUuid);
                $(".department").eq(index).attr("value", assData.departmentName);
                if (assData.position == "1") {
                    $(".position").eq(index).attr("value", "会长");
                } else if (assData.position == "2") {
                    $(".position").eq(index).attr("value", "部长");
                } else if (assData.position == "3") {
                    $(".position").eq(index).attr("value", "干事");
                }
                if (assData.status == '0') {
                    $(".status").eq(index).attr("value", "在职");
                } else {
                    $(".status").eq(index).attr("value", "离职");
                }
            });
            //显示第一个
            $(".assName").eq(0).addClass("active");
            $(".assInfo").eq(0).show();

            //点击不同的导航标签显示不同的社团内容
            $(".assName").click(function () {
                $(this).addClass("active");
                $(".assName").not($(this)).removeClass("active");
                var idx = $(this).index(".assName");
                $(".assInfo").eq(idx).show(500);
                $(".assInfo").not($(".assInfo").eq(idx)).hide(500);
            });
        },
        //响应失败执行的方法
        error: function () {
            alert("服务器响应失败!")
        }
    });
}


/**
 * 退出社团按钮
 */
function assDataQuit(thisObj) {

    var con = confirm("您确定退出该社团吗?");
    if (con) {
        //获取退出的社团Uuid
        var assUuid = thisObj.siblings(".assDataAssUuid").html();

        $.ajax({
            //请求地址
            url: "/java/assDataQuit",
            //请求类型
            type: "POST",
            //发送的数据
            data: {
                "assUuid": assUuid
            },
            //数据格式
            dataType: "json",
            //响应成功执行的方法,参数为相应结果
            success: function (result) {
                if (result == "0") {
                    alert("已退出社团");
                    location.reload();
                }else if (result == "-1") {
                    alert("请检查登录!")
                }else if (result == "-2") {
                    alert("退出失败, 请重试!");
                }else if(result=="-5"){
                    alert("注意! 当前部门只剩一个管理员, 不允许退出!\n可以联系社团管理员直接删除部门或者添加新的部门管理员!")
                }

            },
            //响应失败执行的方法
            error: function () {
                alert("退出社团失败!")
            }
        });

    } else {
        return false;
    }

}


//这里是append的模板
assNameHtml = "<li class='nav-item'><a class='assName nav-link' href='#'><strong>社团名</strong></a></li>";
assInfoHtml = "  " +
    "<div class='assInfo' style='margin-top: 20px; display: none'>" +
    "    <div class='col-lg-12 mb-5'>" +
    "       <div class='card'>" +
    "           <div class='row card-header'>" +
    "               <h5 class='text-uppercase col-md-10 assName2'></h5>" +
    "               <div class='assDataAssUuid' style='display: none;'></div>" +
    "               <button class='btn btn-primary col-md-2 assDataQuit' onclick='assDataQuit($(this))'>退出社团</button>" +
    "           </div>" +
    "           <div class='card-body'>" +
    "               <div class='form-group row'>" +
    "                   <label class='col-md-3 form-control-label'><strong>所在部门</strong></label>" +
    "                   <div class='col-md-9'>" +
    "                       <input type='text' class='form-control-plaintext department' readonly='readonly'>" +
    "                   </div>" +
    "               </div>" +
    "               <div class='line'></div>" +
    "               <div class='form-group row'>" +
    "                   <label class='col-md-3 form-control-label'><strong>当前职位</strong></label>" +
    "                   <div class='col-md-9'>" +
    "                       <input type='text' class='form-control-plaintext position' readonly='readonly'>" +
    "                   </div>" +
    "               </div>" +
    "<div class='line'></div>" +
    "               <div class='form-group row'>" +
    "                   <label class='col-md-3 form-control-label'><strong>在职状态</strong></label>" +
    "                   <div class='col-md-9'>" +
    "                       <input type='text' class='form-control-plaintext status' readonly='readonly'>" +
    "                   </div>" +
    "               </div>" +
    "           </div>" +
    "       </div>" +
    "    </div>" +
    "</div>";
