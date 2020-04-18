package cn.com.alasky.controller.admin;

import cn.com.alasky.service.admin.AdminService;
import cn.com.alasky.pojo.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Alaskyed
 * Time: 2/28/2020 8:17 PM
 * Package: cn.com.alasky.controller.admin
 * Description:
 */
@RestController
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 获取用户在的职位
     * 1: 会长
     * 2: 部长
     * 4: 干事
     *
     * @return error: 错误
     * position: 每个社团职位的拼接(字符串)
     * 注意: 这里不能返回-1,因为前台需要做字符串是否存在,"-1"中存在"1",所以会将管理社团页面全部显示
     */
    @RequestMapping(value = "/getUserPosition")
    public String getUserPosition() {
        try {
            UserSession user = (UserSession) request.getSession().getAttribute("user");
            log.info("查看控制台: " + user.getUserUuid());
            String position = adminService.getUserPosition(user.getUserStuUuid());
            return position;
        } catch (Exception e) {
            log.error("获取用户职位出错: " + String.valueOf(e));
            //这里不能使用ReturnValue, 理由在方法介绍中
            return "error";
        }
    }
}
