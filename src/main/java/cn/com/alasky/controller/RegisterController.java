package cn.com.alasky.controller;

import cn.com.alasky.domain.RegisterBean;
import cn.com.alasky.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * cn.com.alasky.controller
 * 2020/1/29 下午8:07
 * author: Alasky
 * description: 登录
 */

@Controller
@Slf4j
public class RegisterController {
    @Autowired
    RegisterService loginService;

    /**
     * 添加新的用户
     *
     * @param registerBean
     * @param httpServletRequest
     * @return Services返回的结果
     */
    @RequestMapping(value = "/registerInfo", method = RequestMethod.POST)
    @ResponseBody
    public String getRegisterInfo(RegisterBean registerBean, HttpServletRequest httpServletRequest) {
        log.info("新的注册: " + registerBean.getRegisterUserName() + " " + registerBean.getRegisterPhoneNumber());
        String result = loginService.addUser(registerBean);
        return result;
    }
}
