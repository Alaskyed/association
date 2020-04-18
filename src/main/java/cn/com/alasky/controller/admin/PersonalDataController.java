package cn.com.alasky.controller.admin;

import cn.com.alasky.domain.DataChangeBean;
import cn.com.alasky.service.admin.PersonalDataService;
import cn.com.alasky.utils.UserSessionUtils;
import cn.com.alasky.pojo.UserSession;
import cn.com.alasky.vo.admin.PerosnalDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */

@RestController
@Slf4j
public class PersonalDataController {

    @Autowired
    PersonalDataService personalDataService;
    @Autowired
    private DataChangeBean dataChangeBean;

    /**
     * 获取个人信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPersonalData")
    public PerosnalDataVo getPersonalData(HttpServletRequest request) {
        //检查是否登录
        UserSession user = UserSessionUtils.checkLogin(request.getSession());
        if (user == null) {
            return new PerosnalDataVo();
        }

        //已登录, 返回个个人信息
        try {
            //获取成功继续执行下一步
            PerosnalDataVo perosnalDataVo = personalDataService.getPersonalData(user.getUserPhoneNumber());
            //记录登录信息
            log.info("查看个人信息: " + user.getUserUuid());
            return perosnalDataVo;
        } catch (Exception e) {
            log.error("获取个人信息失败: " + String.valueOf(e));
            return new PerosnalDataVo();
        }
    }

    /**
     * 获取用户加入的社团名称
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAssName", method = RequestMethod.POST)
    public List<String> getAssNames(HttpServletRequest request) {
        //获取session中user信息,并判断用户是否存在
        UserSession user = UserSessionUtils.checkLogin(request.getSession());
        if (user == null) {
            return new ArrayList<>();
        }
        try {
            List<String> assNames = personalDataService.getAssNames(user.getUserStuUuid());

            return assNames;
        } catch (Exception e) {
            log.error("获取社团名称失败: " + String.valueOf(e));
            return new ArrayList<>();
        }
    }


    /**
     * 修改个人信息
     *
     * @param dataType
     * @param newData
     * @param request
     * @return
     */
    @RequestMapping(value = "/personalDataChange/{dataType}", method = RequestMethod.POST)
    public String personalDataChange(@PathVariable String dataType, String newData, HttpServletRequest request) {
        //获取session中user信息,并判断用户是否存在
        UserSession user = UserSessionUtils.checkLogin(request.getSession());
        if (user == null) {
            return "-1";
        }

        //判断修改的个人信息类型
        if (dataType.equals("university")) {
            // 修改学校名称
            dataChangeBean.setUserPhoneNumber(user.getUserPhoneNumber());
            dataChangeBean.setNewValue(newData);
            try {
                //执行service中的方法
                String result = personalDataService.changePersonalUniversity(dataChangeBean);
                //返回执行结果代码
                return result;
            } catch (Exception e) {
                log.info("修改学校名称出错!");
                log.error(String.valueOf(e));
                return "-2";
            }
        } else if (dataType.equals("major")) {
            //修改专业名称
            dataChangeBean.setUserPhoneNumber(user.getUserPhoneNumber());
            dataChangeBean.setNewValue(newData);
            try {
                //执行service中方法
                String result = personalDataService.changePersonalMajor(dataChangeBean);
                //返回执行结果代码
                return result;
            } catch (Exception e) {
                log.info("修改专业出错!");
                log.error(String.valueOf(e));
                return "-2";
            }
        } else if (dataType.equals("name")) {
            //修改手机号
            dataChangeBean.setUserPhoneNumber(user.getUserPhoneNumber());
            dataChangeBean.setNewValue(newData);
            //执行service中方法
            try {
                String result = personalDataService.changePersonalName(dataChangeBean);
                //返回执行结果代码
                return result;
            } catch (Exception e) {
                log.info("修改手机号出错!");
                log.error(String.valueOf(e));
                return "-2";
            }
        } else if (dataType.equals("stuId")) {
            //修改学号
            dataChangeBean.setUserPhoneNumber(user.getUserPhoneNumber());
            dataChangeBean.setNewValue(newData);
            //执行service中方法
            try {
                String result = personalDataService.changePersonalStuId(dataChangeBean);
                //返回执行结果代码
                return result;
            } catch (Exception e) {
                log.info("修改学号出错!");
                log.error(String.valueOf(e));
                return "-2";
            }

        } else if (dataType.equals("grade")) {
            //修改年级
            dataChangeBean.setUserPhoneNumber(user.getUserPhoneNumber());
            dataChangeBean.setNewValue(newData);
            //执行service中方法
            try {
                String result = personalDataService.changePersonalGrade(dataChangeBean);
                //返回执行结果代码
                return result;
            } catch (Exception e) {
                log.info("修改年级出错!");
                log.error(String.valueOf(e));
                return "-2";
            }

        } else {
            return "-3";
        }
    }
}
