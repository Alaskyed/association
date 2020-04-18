package cn.com.alasky.controller.association;

import cn.com.alasky.pojo.UserSession;
import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.service.association.AssService;
import cn.com.alasky.utils.UserSessionUtils;
import cn.com.alasky.vo.association.AssInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/30/2020 4:42 PM
 * Package: cn.com.alasky.controller.ass
 * Description:
 */
@RestController
@Slf4j
public class AssIGetInfoController {
    @Autowired
    private AssService assService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 获取学校的社团名单
     *
     * @return
     */
    @RequestMapping(value = "/getUniversityAss")
    public List<AssInfoVo> getUniversityAss() {
        try {
            //检测登录
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user == null) {
                return new LinkedList<>();
            }

            //先从数据库获取学校code
            String stuUuid = user.getUserStuUuid();
            String universityCode = assService.getUniversityCodeByStuUuid(stuUuid);

            //再根据university_code查询所有社团
            List<AssInfoVo> assInfoVos = assService.getUniversityAsses(universityCode);
            log.info("查看学校社团: " + user.getUserUuid());

            return assInfoVos;
        } catch (Exception e) {
            log.error("获取学校社团名单出错: " + String.valueOf(e));
            return new LinkedList<>();
        }
    }

    /**
     * 用户要浏览社团详细信息, 将该社团的uuid存储到session
     *
     * @return
     */
    @RequestMapping(value = "/sentAssUuid")
    public String getAssUuid(String assUuid) {
        try {
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user == null) {
                return ReturnValue.USER_INFO_ERROR.value();
            }

            //已登录,将assUuid存储到session
            request.getSession().setAttribute("viewAssDetailsUuid", assUuid);
            return ReturnValue.SUCCESS.value();
        } catch (Exception e) {
            log.error("社团详细信息存储uuid失败: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }


    /**
     * 获取社团的详细信息
     *
     * @return
     */
    @RequestMapping(value = "/getAssSignUpDetail")
    public AssInfoVo getAssDetail() {
        try {
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user == null) {
                return new AssInfoVo();
            }
            //已登录查询社团详细信息
            String assUuid = (String) request.getSession().getAttribute("viewAssDetailsUuid");
            AssInfoVo assInfoVo = assService.getAssDetail(assUuid);

            log.info("获取社团详细信息: " + user.getUserUuid());

            return assInfoVo;

        } catch (Exception e) {
            log.error("获取社团详细信息出错: " + String.valueOf(e));
            return new AssInfoVo();
        }
    }
}
