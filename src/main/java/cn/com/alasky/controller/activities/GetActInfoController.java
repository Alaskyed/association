package cn.com.alasky.controller.activities;

import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.service.activites.GetActInfoService;
import cn.com.alasky.utils.RequestInfoUtils;
import cn.com.alasky.utils.UserSessionUtils;
import cn.com.alasky.vo.activities.ActInfoVo;
import cn.com.alasky.pojo.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/16/2020 10:20 AM
 * Package: cn.com.alasky.controller.activities
 * Description:
 */
@RestController
@Slf4j
public class GetActInfoController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private GetActInfoService getActInfoService;

    /**
     * index页面热门活动展示,获取几个热门活动的信息
     *
     * @return
     */
    @RequestMapping("/indexGetActInfo")
    public List<ActInfoVo> getHighHotActInfos() {
        UserSession user = UserSessionUtils.checkLogin(request.getSession());
        //如果用户没有登录, 则展示所有活动中的热门活动
        if (user == null) {
            try {
                List<ActInfoVo> highHotActInfoVos = getActInfoService.getHighHotActInfos();
                return highHotActInfoVos;
            } catch (Exception e) {
                log.error("获取热门社团信息出错: " + String.valueOf(e));
                return new LinkedList<>();
            }
        } else {
            //如果用户登录, 则展示本校的热门活动
            try {
                List<ActInfoVo> highHotActInfoVos = getActInfoService.getUniversityHighHotActInfos(user.getUserStuUuid());
                return highHotActInfoVos;
            } catch (Exception e) {
                log.error("获取本校热门社团信息出错: " + String.valueOf(e));
                return new LinkedList<>();
            }

        }
    }

    /**
     * 所有活动主页,获取所有的活动(部分)
     *
     * @param index
     * @return
     */
    @RequestMapping("/allActGetActInfo")
    public List<ActInfoVo> getActInfos(Integer index) {
        try {
            List<ActInfoVo> actInfoVos = getActInfoService.getActInfosByIndex((int) index);
            log.info("查看全部社团活动: " + RequestInfoUtils.getIPAndDeviceInfo(request));
            return actInfoVos;
        } catch (Exception e) {
            log.error("获取社团活动出错: " + String.valueOf(e));
            return new LinkedList<>();
        }
    }


    /**
     * 根据关键字查找活动
     * @param searchKey
     * @return
     */
    @RequestMapping(value = "actSearch")
    public List<ActInfoVo> actSearch(String searchKey) {
        try {
            List<ActInfoVo> actInfoVos = getActInfoService.actSearch(searchKey);

            log.info("根据关键字查找活动(" + searchKey + "):" + RequestInfoUtils.getIPAndDeviceInfo(request));

            return actInfoVos;
        } catch (Exception e) {
            log.error("获取关键字社团活动出错: " + String.valueOf(e));
            return new LinkedList<>();
        }
    }



    /**
     * 当用户点击查看活动详情时,先存储该活动ID
     *
     * @param actId
     * @return
     */
    @RequestMapping("/sentActId")
    public String updateActDetailId(int actId) {
        try {
            //已登录,存储查看活动详细信息的session信息
            request.getSession().setAttribute("viewActDetailsId", actId);
            return ReturnValue.SUCCESS.value();
        } catch (Exception e) {
            log.error("活动详细信息存储id失败: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }

    }

    /**
     * 查看活动的详细信息
     *
     * @return
     */
    @RequestMapping(value = "/getActDetail")
    public ActInfoVo getActDetail() {
        try {
            //获取刚刚添加到session的actId
            int actId = (int) request.getSession().getAttribute("viewActDetailsId");
            //查询活动
            ActInfoVo actInfoVo = getActInfoService.getActDetail(actId);
            //活动hot值+1
            getActInfoService.actHotIncreament(actId);
            log.info("查看活动详细信息: " + RequestInfoUtils.getIPAndDeviceInfo(request));
            return actInfoVo;
        } catch (Exception e) {
            log.error("查询活动详细信息出错: " + String.valueOf(e));
            return new ActInfoVo();
        }

    }


}
