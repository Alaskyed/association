package cn.com.alasky.controller;

import cn.com.alasky.domain.LoginBean;
import cn.com.alasky.utils.RequestInfoUtils;
import cn.com.alasky.vo.LoginSessionVo;
import cn.com.alasky.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    LoginSessionVo user;
    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/loginInfo", method = RequestMethod.POST)
    @ResponseBody
    public String getLoginInfo(LoginBean loginBean, HttpServletRequest httpServletRequest) {
//        System.out.println(RequestInfoUtils.getIPAndDeviceInfo(httpServletRequest));
//        System.out.println(RequestInfoUtils.getDeviceInfo(httpServletRequest));

        boolean result = loginService.checkUser(loginBean, httpServletRequest.getSession());
        if (result) {
            //记录登录信息
            log.info("新的登录(" + RequestInfoUtils.getIPAdrress(httpServletRequest) + "): " + loginBean.getLoginUserName());
            return "success";
        } else {
            log.info("登录失败: " + loginBean.getLoginUserName());
            return "fail";
        }
    }


    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    @ResponseBody
    public LoginSessionVo checkLogin(HttpSession session) {
        //获取登录设备信息
        log.info(RequestInfoUtils.getIPAndDeviceInfo(request));
        //判断session中是否存在该用户
        if (session.getAttribute("user") == null) {
            //用户不存在,返回空的user
            user.setUserPhoneNumber(null);
            user.setUserName(null);
            return user;
        } else {
            //用户存在,更新session,返回user信息
            user = (LoginSessionVo) session.getAttribute("user");
            session.setAttribute("user", user);
            LoginSessionVo loginUser = (LoginSessionVo) session.getAttribute("user");
            return user;
        }
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody()
    public String logout(HttpSession session) {
        try {
            user = (LoginSessionVo) session.getAttribute("user");
            log.info(user.getUserPhoneNumber() + " 退出登录");
            session.removeAttribute("user");
            return "success";
        } catch (Exception e) {
            log.error("退出登录时异常");
            log.error(String.valueOf(e));
            return "fail";
        }
    }
}
