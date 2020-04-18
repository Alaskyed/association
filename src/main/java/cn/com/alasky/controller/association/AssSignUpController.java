package cn.com.alasky.controller.association;

import cn.com.alasky.domain.AssSignUpBean;
import cn.com.alasky.pojo.UserSession;
import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.service.association.AssSignUpService;
import cn.com.alasky.utils.UserSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Alaskyed
 * Time: 4/7/2020 11:29 AM
 * Package: cn.com.alasky.controller.association
 * Description:
 */
@RestController
@Slf4j
public class AssSignUpController {
    @Autowired
    private AssSignUpService assSignUpService;
    @Autowired
    private HttpServletRequest request;


    /**
     * 获取提交的社团报名信息
     *
     * @param assSignUpBean
     * @return
     */
    @RequestMapping(value = "/assSignUp")
    public String getAssSignUp(AssSignUpBean assSignUpBean) {
        try {
            //检查是否登录
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user == null) {
                return ReturnValue.USER_INFO_ERROR.value();
            }

            //已登录, 执行添加报名逻辑
            //先添加用户uuid
            String userUuid = user.getUserUuid();
            assSignUpBean.setUserUuid(userUuid);
            //执行添加逻辑
            String result = assSignUpService.addSignUpInfo(assSignUpBean,user.getUserStuUuid());
            log.info("新的社团报名: " + assSignUpBean.getAssUuid());
            return result;
        } catch (Exception e) {
            log.error("社团报名出错: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();

        }
    }

}
