package cn.com.alasky.controller.admin;

import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.service.admin.DepAdminService;
import cn.com.alasky.vo.admin.DepAdminVo;
import cn.com.alasky.pojo.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/5/2020 5:51 PM
 * Package: cn.com.alasky.controller.admin
 * Description:
 */
@RestController
@Slf4j
public class DepAdminController {
    @Autowired
    private DepAdminService depAdminService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 获取部门管理的所有信息
     *
     * @return 0: 成功
     * -1: 用户没有登录
     */
    @RequestMapping(value = "getDepAdminAssInfo", method = RequestMethod.POST)
    public List<DepAdminVo> getDepAdminAssInfo() {
        try {
            //检查用户登录
            UserSession user = (UserSession) request.getSession().getAttribute("user");
            if (user == null) {
                return new LinkedList<>();
            }
            //执行逻辑
            List<DepAdminVo> depAdminVos = depAdminService.getDepAdminAssInfo(user);
            log.info("查看部门管理: " + user.getUserUuid());
            return depAdminVos;

        } catch (Exception e) {
            log.error("获取部门管理信息出错: " + String.valueOf(e));
            return new LinkedList<>();
        }

    }


    /**
     * 删除部门中的人员
     *
     * @return 0: 成功
     * -6: 部门管理员不够
     */
    @RequestMapping(value = "/depAdmin/deleteStu", method = RequestMethod.POST)
    public String deleteStu(String depUuid, String stuUuid) {
        try {
            //检查用户登录
            UserSession user = (UserSession) request.getSession().getAttribute("user");
            if (user == null) {
                return ReturnValue.USER_INFO_ERROR.value();
            }
            //执行逻辑
            String result = depAdminService.deleteStu(depUuid, stuUuid);
            log.info("删除部门人员(" + stuUuid + "): " + user.getUserUuid());
            return result;

        } catch (Exception e) {
            log.error("删除部门干事出错: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }

}
