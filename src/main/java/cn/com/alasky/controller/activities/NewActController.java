package cn.com.alasky.controller.activities;

import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.domain.NewActBean;
import cn.com.alasky.service.activites.NewActService;
import cn.com.alasky.vo.activities.AssNameVo;
import cn.com.alasky.pojo.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/15/2020 10:14 AM
 * Package: cn.com.alasky.controller.activities
 * Description:
 */
@RestController
@Slf4j
public class NewActController {
    @Autowired
    private NewActService newActService;
    @Autowired
    HttpServletRequest request;

    /**
     * 查询当会长的社团名称和assUuid
     *
     * @return
     */
    @RequestMapping("/newActivitesGetInfo")
    public List<AssNameVo> getAdminAssName() {
        try {
            UserSession user = (UserSession) request.getSession().getAttribute("user");
            if (user == null) {
                return new ArrayList<>();
            }
            List<AssNameVo> names = newActService.getAdminAssName(user.getUserStuUuid());
            return names;
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return new ArrayList<>();
        }
    }

    /**
     * 添加新的社团活动,存储活动信息
     * 注意这里接收到的请求是静态服务器发过来的,没有用户session信息,
     * 如果想要用户的session信息,可以在静态服务器里添加相应的属性
     *
     * @param newAct
     * @return
     */
    @RequestMapping("/newActInfoSave")
    public String newActInfoSave(NewActBean newAct) {
        try {
            log.info("新的社团活动: " + newAct.getNewActName());
            int result = newActService.addNewAct(newAct);
            //这里返回的是id值, 不能使用ReturnValue
            return String.valueOf(result);
        } catch (Exception e) {
            log.error("添加新活动出错: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }


    /**
     * 添加新活动的图片
     *
     * @param newActId
     * @param newActPicName
     * @return
     */
    @RequestMapping("/newActPicName")
    public String newActPicName(String newActId, String newActPicName) {
        try {
            newActService.updatePicName(newActId, newActPicName);
            return ReturnValue.SUCCESS.value();
        } catch (Exception e) {
            log.error("更新活动图片名称失败: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }

    /**
     * 添加活动的附件名称
     *
     * @return
     */
    @RequestMapping("/newActEnclosureName")
    public String newActEnclosureName(int newActId, String newActEnclosureName) {
        try {
            newActService.updateEnclosureName(newActId, newActEnclosureName);
            return ReturnValue.SUCCESS.value();
        } catch (Exception e) {
            log.error("更新活动附件名称失败: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }

    }

}
