package cn.com.alasky.controller.admin;

import cn.com.alasky.pojo.UserSession;
import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.service.admin.AssSignUpAdminService;
import cn.com.alasky.utils.UserSessionUtils;
import cn.com.alasky.vo.admin.AssSignUpAdminAssNameVo;
import cn.com.alasky.vo.admin.AssSignUpDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Alaskyed
 * Time: 4/7/2020 5:19 PM
 * Package: cn.com.alasky.controller.admin
 * Description:
 */
@RestController
@Slf4j
public class AssSignUpAdminController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AssSignUpAdminService assSignUpAdminService;


    /**
     * 获取担任会长或部长的社团列表
     *
     * @return
     */
    @RequestMapping(value = "/getAssSignUpAdminAssName")
    public List<AssSignUpAdminAssNameVo> getAssSignUpAdminAssName() {
        try {
            //检测登录
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user == null) {
                return new LinkedList<>();
            }

            //已登录, 执行查询逻辑
            List<AssSignUpAdminAssNameVo> assSignUpAdminAssNames = assSignUpAdminService.getAssSignUpAdminAssNames(user.getUserStuUuid());
            log.info("查询社团报名信息");
            return assSignUpAdminAssNames;
        } catch (Exception e) {
            log.error("查询社团报名信息社团列表出错: " + String.valueOf(e));
            return new LinkedList<>();
        }
    }


    /**
     * 获取指定社团的所有报名信息
     *
     * @param assUuid
     * @return
     */
    @RequestMapping(value = "/getAssSignUpAdminSignUpInfoList")
    public List<AssSignUpDetailVo> getAssSignUpAdminSignUpInfos(String assUuid) {
        try {
            //执行逻辑
            List<AssSignUpDetailVo> assSignUpDetailVos = assSignUpAdminService.getAssSignUpInfoLIst(assUuid);
            log.info("查看社团的报名信息列表(" + assUuid + ")");
            return assSignUpDetailVos;
        } catch (Exception e) {
            log.error("获取社团报名信息列表失败: " + String.valueOf(e));
            return new LinkedList<>();
        }
    }


    /**
     * 获取某个表名表的信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getAssSignUpInfoDetail")
    public AssSignUpDetailVo getAssSignUpDetail(long id) {
        try {
            //执行逻辑
            AssSignUpDetailVo assSignUpDetail = assSignUpAdminService.getAssSignUpDetail(id);
            log.info("查看社团的报名信息详情(" + id + ")");
            return assSignUpDetail;
        } catch (Exception e) {
            log.error("获取社团报名信息详情失败: " + String.valueOf(e));
            return new AssSignUpDetailVo();
        }

    }


    @RequestMapping(value = "/assSignUpAdminOption/{option}")
    public String assSignUpAdminAgree(@PathVariable(value = "option") String option, long id) {
        try {
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user == null) {
                return ReturnValue.USER_INFO_ERROR.value();
            }

            //确认登录, 执行操作逻辑
            String result = ReturnValue.EXECUTION_ERROR.value();
            if (option.equals("agree")) {
                result = assSignUpAdminService.agree(user, id);
                log.info("同意加入社团: " + id);
            } else if (option.equals("refuse")) {
                result = assSignUpAdminService.refuse(user, id);
                log.info("拒绝加入社团: " + id);
            } else {
                //请求不匹配, 返回错误
                return ReturnValue.DATA_MATCHING_ERROR.value();
            }

            return result;
        } catch (Exception e) {
            log.error("同意报名失败(" + id + "): " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }


}
