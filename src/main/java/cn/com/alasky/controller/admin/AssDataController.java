package cn.com.alasky.controller.admin;

import cn.com.alasky.domain.NewAssBean;
import cn.com.alasky.service.admin.AssDataService;
import cn.com.alasky.utils.UserSessionUtils;
import cn.com.alasky.vo.AssDataVo;
import cn.com.alasky.vo.LoginSessionVo;
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
 * Description:
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
            LoginSessionVo user = (LoginSessionVo) request.getSession().getAttribute("user");
            log.info("获取社团信息: "+user.getUserPhoneNumber());
            List<AssDataVo> assDataVos = assDataService.getAssData(user.getUserPhoneNumber());

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
     * -1: 用户未登录
     * -2: 操作失败
     * -3: 学校名称错误
     */
    @RequestMapping(value = "/createNewAss", method = RequestMethod.POST)
    public String createNewAss(@RequestBody NewAssBean newAssBean) {
        try {
            //检查用户是否登录
            LoginSessionVo user = UserSessionUtils.checkLogin(request.getSession());
            if (user != null) {
                String result=assDataService.createNewAss(newAssBean,user);
                return result;
            } else {
                //没有用户信息,返回用户没有登录代码
                return "-1";
            }
        } catch (Exception e) {
            log.error("创建新的社团出错: " + String.valueOf(e));
            return "-2";
        }

    }


}
