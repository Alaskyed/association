package cn.com.alasky.controller.admin;

import cn.com.alasky.domain.DepartmentBean;
import cn.com.alasky.service.admin.AssAdminService;
import cn.com.alasky.utils.UserSessionUtils;
import cn.com.alasky.vo.AssAdminVo;
import cn.com.alasky.vo.LoginSessionVo;
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
            LoginSessionVo user = UserSessionUtils.checkLogin(request.getSession());
            if (user != null) {
                assAdminVos = assAdminService.getAssAdminAssInfo(user);
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
     * 社团管理修改基本信息社团名和学校
     *
     * @param assUuid
     * @param newData
     * @param dataType
     * @return 0: 成功
     * -1: 未登录
     * -2: 执行出错
     * -3: 接受的更改类型出错
     * -4: 职位不符或者大学名称错误
     */
    @RequestMapping(value = "/assAdminChange/{dataType}")
    public String assAdminChange(@RequestParam(value = "assUuid") String assUuid, @RequestParam("newData") String newData, @PathVariable("dataType") String dataType) {
        //根据assUuid更新名称和学校
        try {
            //检测用户是否登录
            LoginSessionVo user = (LoginSessionVo) request.getSession().getAttribute("user");
            //未检测到session,返回错误代码
            if (user == null) {
                return "-1";
            }
            //已登录,执行逻辑
            if (dataType.equals("assName")) {
                String result = assAdminService.assAdminNameChange(assUuid, newData, user);
                return result;
            } else if (dataType.equals("assUniversity")) {
                String result = assAdminService.assAdminUniveisityChange(assUuid, newData, user);
                return result;
            } else {
                return "-3";
            }
        } catch (Exception e) {
            log.error("修改社团信息出错: " + String.valueOf(e));
            return "-2";
        }

    }

    /**
     * 社团管理修改部门基本信息
     * \@param assUuid
     *
     * @param
     * @param
     * @return 0: 成功
     * -1: 未登录
     * -2: 执行出错
     * -3: 接受的更改类型出错
     * -4: 职位不符或者大学名称错误
     */
    @RequestMapping(value = "/assAdminDepChange")
    public String assAdminDepChange(DepartmentBean departmentBean) {
        try {
            //检查用户登录
            LoginSessionVo user = (LoginSessionVo) request.getSession().getAttribute("user");
            if (user == null) {
                return "-1";
            }
            //直接更新对应社团就可以
            //执行逻辑
            String result = assAdminService.assAdminDepChange(departmentBean, user);
            return result;
        } catch (Exception e) {
            log.error("更新部门信息失败: " + String.valueOf(e));
            return "-2";
        }
    }

    /**
     * 社团管理删除部门
     * \@param assUuid
     *
     * @param
     * @param
     * @return -5: 会长团不允许删除
     */
    @RequestMapping(value = "/assAdminDepDelete")
    public String assAdminDepDelete(String departmentUuid) {
        try {
            //检查用户登录
            LoginSessionVo user = (LoginSessionVo) request.getSession().getAttribute("user");
            if (user == null) {
                return "-1";
            }
            //删除部门,删除部门中的人员
            String result = assAdminService.deleteDep(departmentUuid,user);
            return result;
        } catch (Exception e) {
            log.error("删除部门失败: " + String.valueOf(e));
            return "-2";
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
            LoginSessionVo user = (LoginSessionVo) request.getSession().getAttribute("user");
            if (user == null) {
                return "-1";
            }
            //添加一个部门,绑定社团
            String result= assAdminService.addDep(assUuid, departmentName, departmentDescription,user);
            return result;
        } catch (Exception e) {
            log.error("添加部门失败: " + String.valueOf(e));
            return "-2";
        }
    }


}
