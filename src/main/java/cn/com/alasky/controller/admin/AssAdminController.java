package cn.com.alasky.controller.admin;

import cn.com.alasky.domain.DepartmentBean;
import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.service.admin.AssAdminService;
import cn.com.alasky.utils.UserSessionUtils;
import cn.com.alasky.vo.admin.AssAdminVo;
import cn.com.alasky.pojo.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/1/2020 11:33 AM
 * Package: cn.com.alasky.controller.admin
 * Description:
 */
@RestController
@Slf4j
public class AssAdminController {
    @Autowired
    private AssAdminService assAdminService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 获取管理社团的社团信息
     *
     * @return
     */
    @RequestMapping(value = "/getAssAdminAssInfo", method = RequestMethod.POST)
    public List<AssAdminVo> getAssAdminAssInfo() {
        List<AssAdminVo> assAdminVos = null;
        try {
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user != null) {
                assAdminVos = assAdminService.getAssAdminAssInfo(user);
                log.info("查看控制台社团管理: " + user.getUserUuid());
                return assAdminVos;
            } else {
                return assAdminVos;
            }
        } catch (Exception e) {
            log.error("获取社团管理信息出错: " + String.valueOf(e));
            return new ArrayList<AssAdminVo>();
        }

    }

    /**
     * 社团管理修改基本信息, 社团名和学校, 主页
     *
     * @param assUuid
     * @param newData
     * @param dataType
     */
    @RequestMapping(value = "/assAdminChange/{dataType}")
    public String assAdminChange(@RequestParam(value = "assUuid") String assUuid, @RequestParam("newData") String newData, @PathVariable("dataType") String dataType) {
        //根据assUuid更新名称和学校
        try {
            //检测用户是否登录
            UserSession user = (UserSession) request.getSession().getAttribute("user");
            //未检测到session,返回错误代码
            if (user == null) {
                return ReturnValue.USER_INFO_ERROR.value();
            }

            //已登录,执行逻辑
            log.info("修改社团" + dataType + "信息: " + user.getUserPhoneNumber());
            if (dataType.equals("assName")) {
                //社团名
                String result = assAdminService.assAdminNameChange(assUuid, newData, user);
                return result;
            } else if (dataType.equals("assUniversity")) {
                //学校
                String result = assAdminService.assAdminUniveisityChange(assUuid, newData, user);
                return result;
            } else if (dataType.equals("assCustomUrl")) {
                //主页
                String result = assAdminService.assAdminCustomUrlChange(assUuid, newData, user);
                return result;
            } else {
                return ReturnValue.DATA_MATCHING_ERROR.value();
            }
        } catch (Exception e) {
            log.error("修改社团信息出错: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }

    }

    /**
     * 社团管理修改部门基本信息
     * \@param assUuid
     */
    @RequestMapping(value = "/assAdminDepChange")
    public String assAdminDepChange(DepartmentBean departmentBean) {
        try {
            //检查用户登录
            UserSession user = (UserSession) request.getSession().getAttribute("user");
            if (user == null) {
                return ReturnValue.USER_INFO_ERROR.value();
            }
            //直接更新对应社团就可以
            //执行逻辑
            log.info("修改部门信息: " + user.getUserPhoneNumber());
            String result = assAdminService.assAdminDepChange(departmentBean, user);
            return result;
        } catch (Exception e) {
            log.error("更新部门信息失败: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }

    /**
     * 社团管理删除部门
     * \@param assUuid
     */
    @RequestMapping(value = "/assAdminDepDelete")
    public String assAdminDepDelete(String departmentUuid) {
        try {
            //检查用户登录
            UserSession user = (UserSession) request.getSession().getAttribute("user");
            if (user == null) {
                return ReturnValue.USER_INFO_ERROR.value();
            }
            //删除部门,删除部门中的人员
            String result = assAdminService.deleteDep(departmentUuid, user);
            log.info("删除社团(" + departmentUuid + ")部门: " + user.getUserPhoneNumber());
            return result;
        } catch (Exception e) {
            log.error("删除部门失败: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }

    /**
     * 社团管理添加部门
     *
     * @param assUuid
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/assAdminDepAdd")
    public String assAdminDepAdd(String assUuid, String departmentName, String departmentDescription) {
        try {
            //检查用户登录
            UserSession user = (UserSession) request.getSession().getAttribute("user");
            if (user == null) {
                return ReturnValue.USER_INFO_ERROR.value();
            }
            //添加一个部门,绑定社团
            log.info("添加社团(" + assUuid + ")部门: " + user.getUserPhoneNumber());
            String result = assAdminService.addDep(assUuid, departmentName, departmentDescription, user);
            return result;
        } catch (Exception e) {
            log.error("添加部门失败: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }


    /**
     * 删除社团
     */
    @RequestMapping(value = "/assAdminDelAss")
    public String assAdminDelAss(String assUuid) {
        try {
            //检查登录
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user == null) {
                return ReturnValue.USER_INFO_ERROR.value();
            }

            //已登录, 执行删除社团
            String result = assAdminService.delAss(user.getUserStuUuid(), assUuid);

            return result;

        } catch (Exception e) {
            log.error("删除社团失败: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }

}
