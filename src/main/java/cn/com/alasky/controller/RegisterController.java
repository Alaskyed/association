package cn.com.alasky.controller;

import cn.com.alasky.domain.RegisterBean;
import cn.com.alasky.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
        try {
            String result = loginService.addUser(registerBean);
            return result;
        } catch (
                DuplicateKeyException e) {
            //主键重复异常,说明该手机号已注册
            log.info("注册手机号重复");
            log.error(String.valueOf(e));
            return "当前手机号已注册!";
        } catch (Exception e) {
            //其他错误
            log.info("插入新用户出错");
            log.error(String.valueOf(e));
            return "注册失败";
        }
    }
}
