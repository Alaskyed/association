package cn.com.alasky.controller.admin;

import cn.com.alasky.domain.NewAssBean;
import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.service.admin.AssDataService;
import cn.com.alasky.utils.UserSessionUtils;
import cn.com.alasky.vo.admin.AssDataVo;
import cn.com.alasky.pojo.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Alaskyed
 * Time: 2/26/2020 10:55 AM
 * Package: cn.com.alasky.controller.admin
 * Description: 管理台社团信息
 */
@RestController
@Slf4j
public class AssDataController {
    @Autowired
    private AssDataService assDataService;

    @Autowired
    private HttpServletRequest request;


    /**
     * 获取社团的信息
     *
     * @return
     */
    @RequestMapping(value = "/getAssData", method = RequestMethod.POST)
    public List<AssDataVo> getAssData() {
        try {
            UserSession user = (UserSession) request.getSession().getAttribute("user");
            log.info("获取社团信息: " + user.getUserUuid());
            List<AssDataVo> assDataVos = assDataService.getAssData(user.getUserStuUuid());

            return assDataVos;
        } catch (Exception e) {
            log.info("获取社团信息出错");
            log.error(String.valueOf(e));
            return new ArrayList<>();
        }

    }


    /**
     * 创建新的社团
     *
     * @param
     * @param
     * @return 0 : 成功
     */
    @RequestMapping(value = "/createNewAss", method = RequestMethod.POST)
    public String createNewAss(@RequestBody NewAssBean newAssBean) {
        try {
            //检查用户是否登录
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user != null) {
                log.info("创建新的社团: " + user.getUserUuid());
                String result = assDataService.createNewAss(newAssBean, user);
                return result;
            } else {
                //没有用户信息,返回用户没有登录代码
                return ReturnValue.USER_INFO_ERROR.value();
            }
        } catch (Exception e) {
            log.error("创建新的社团出错: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }

    }


    /**
     * 退出社团
     */
    @RequestMapping(value = "/assDataQuit")
    public String assDataQuit(String assUuid) {
        try {
            //检查登录
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user == null) {
                return ReturnValue.USER_INFO_ERROR.value();
            }

            //已登录, 执行退出社团
            String result = assDataService.quitAss(user.getUserStuUuid(), assUuid);
            log.info("退出社团(" + assUuid + "): " + user.getUserUuid());
            return result;


        } catch (Exception e) {
            log.error("退出社团错误: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }

}
