package cn.com.alasky.controller.activities;

import cn.com.alasky.domain.ActSignUpBean;
import cn.com.alasky.pojo.UserSession;
import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.service.activites.ActSignUpService;
import cn.com.alasky.utils.UserSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Alaskyed
 * Time: 3/29/2020 7:27 PM
 * Package: cn.com.alasky.controller.activities
 * Description:
 */
@RestController
@Slf4j
public class ActSignUpController {
    @Autowired
    private ActSignUpService actSignUpService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 新的活动报名
     *
     * @return
     */
    @RequestMapping(value = "/sendActSignUpInfo")
    public String getActSignUpInfo(ActSignUpBean actSignUpBean) {
        try {
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user == null) {
                return ReturnValue.USER_INFO_ERROR.value();
            }
            //获取用户uuid
            actSignUpBean.setUserUuid(user.getUserUuid());
            //执行添加逻辑
            String result = actSignUpService.addNewActSignUp(user.getUserUuid(),actSignUpBean);

            log.info("添加新的活动报名信息: " + actSignUpBean.getActId());
            return result;
        } catch (Exception e) {
            log.error("添加新的活动报名信息失败: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }
}
