package cn.com.alasky.controller;

import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.domain.LoginBean;
import cn.com.alasky.utils.JedisUtils;
import cn.com.alasky.utils.RequestInfoUtils;
import cn.com.alasky.pojo.UserSession;
import cn.com.alasky.service.LoginService;
import cn.com.alasky.utils.UserSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * cn.com.alasky.controller
 * 2020/2/1 下午12:14
 * author: Alasky
 * description:
 */

@Controller
@Slf4j
public class LoginController {
    @Autowired
    LoginService loginService;
    /**
     * 获取用户的登录session部分值的对象
     */
    @Autowired
    HttpServletRequest request;

    /**
     * 用户登录
     *
     * @param loginBean
     * @param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String getLoginInfo(LoginBean loginBean) {
        try {
            //先检查一遍用户是否登录, 如果已经存在session信息, 直接返回true即可
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user != null) {
                return ReturnValue.SUCCESS.value();
            }
            //不存在session信息, 执行登录逻辑
            String result = loginService.checkLoginInfo(loginBean, request.getSession());
            //记录登录信息
            log.info("新的登录(" + RequestInfoUtils.getIPAdrress(request) + "): " + loginBean.getLoginUserName());
            return result;
        } catch (Exception e) {
            log.error("查询用户出错: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }


    /**
     * 检查用户登录
     *
     * @return
     */
    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    @ResponseBody
    public UserSession checkLogin() {
        //浏览量+1
        JedisUtils.pageviewsIncrease();
        //获取登录设备信息
        UserSession user = UserSessionUtils.checkLogin(request.getSession());
        //判断session中是否存在该用户
        if (user == null) {
            //用户不存在,返回空的user
            return new UserSession();
        } else {
            //用户存在, 返回用户信息
            return user;
        }
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody()
    public String logout() {
        try {
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user != null) {
                //检测到用户信息, 移除用户session
                request.getSession().removeAttribute("user");
                log.info(user.getUserPhoneNumber() + " 退出登录");
                return ReturnValue.SUCCESS.value();
            } else {
                //未检测到用户信息, 直接返回成功
                return ReturnValue.SUCCESS.value();
            }
        } catch (Exception e) {
            log.error("退出登录时异常: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }
}
