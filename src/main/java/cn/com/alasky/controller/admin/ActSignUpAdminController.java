package cn.com.alasky.controller.admin;

import cn.com.alasky.pojo.UserSession;
import cn.com.alasky.service.admin.ActSignUpAdminService;
import cn.com.alasky.utils.UserSessionUtils;
import cn.com.alasky.vo.activities.ActAdminNameVo;
import cn.com.alasky.vo.activities.ActSignUpInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/29/2020 9:41 PM
 * Package: cn.com.alasky.controller.admin
 * Description:
 */
@RestController
@Slf4j
public class ActSignUpAdminController {
    @Autowired
    private ActSignUpAdminService actSignUpAdminService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 获取担任部长或者会长的社团名称
     * @return
     */
    @RequestMapping(value = "/getActSignUpAdminActNames")
    public List<ActAdminNameVo> getActSignUpAdminActNames() {
        try {
            //检测登录
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user == null) {
                return new LinkedList<>();
            }
            List<ActAdminNameVo> actAdminNameVo = actSignUpAdminService.getActSignUpAdminActNames(user.getUserStuUuid());
            log.info("控制台获取活动名称: " + user.getUserUuid());
            return actAdminNameVo;
        } catch (Exception e) {
            log.error("控制台获取活动名称出错: " + String.valueOf(e));
            return new LinkedList<>();
        }
    }

    @RequestMapping(value = "/getActSignUpAdminSignUpInfo")
    public List<ActSignUpInfoVo> getActSignUpAdminSignUpInfos(int actId) {
        try {
            //检测登录信息
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user == null) {
                return new LinkedList<>();
            }

            //已登录, 执行逻辑
            List<ActSignUpInfoVo> actSignUpInfoVos = actSignUpAdminService.getActSignUpAdminSignUpInfos(actId);
            log.info("获取活动报名信息(" + actId + "): " + actId);
            return actSignUpInfoVos;
        } catch (Exception e) {
            log.error("获取活动报名信息出错: " + String.valueOf(e));
            return new LinkedList<>();
        }
    }
}
