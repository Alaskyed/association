package cn.com.alasky.service.admin;

import cn.com.alasky.domain.DepartmentBean;
import cn.com.alasky.mapper.admin.AssAdminMapper;
import cn.com.alasky.vo.AssAdminVo;
import cn.com.alasky.vo.LoginSessionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Author: Alaskyed
 * Time: 3/1/2020 11:35 AM
 * Package: cn.com.alasky.service.admin
 * Description:
 */
@Service
public class AssAdminService {
    @Autowired
    private AssAdminMapper assAdminMapper;
    @Autowired
    private HttpSession session;

    /**
     * 根据ass_uuid获取社团管理的信息
     *
     * @param user
     * @return
     */
    public List<AssAdminVo> getAssAdminAssInfo(LoginSessionVo user) {
        List<AssAdminVo> assAdminVos = new LinkedList<>();
        //1. 查出该学生任会长的社团uuid
        List<String> assUuids = assAdminMapper.queryAssUuidsFromAssUuid(user.getUserStuUuid());
        //2. 根据社团uuid查询每个社团的信息
        for (String assUuid : assUuids) {
            //获取社团名,学校,社团人数
            AssAdminVo assAdminVo = assAdminMapper.queryAssInfosByAssUuid(assUuid);
            //创建一个部门列表
            List<DepartmentBean> departmentBeans = assAdminMapper.queryDepInfoByAssUuid(assUuid);
            //将部门添加到社团对象中
            assAdminVo.setDepartments(departmentBeans);
            //添加社团对象到列表中
            assAdminVos.add(assAdminVo);
        }

        return assAdminVos;
    }


    /**
     * 管理台修改社团名称
     *
     * @param assUuid
     * @param newData
     * @param user
     * @return
     */
    @Transactional
    public String assAdminNameChange(String assUuid, String newData, LoginSessionVo user) {
        //检测用户是否具有权限
        String position = assAdminMapper.queryUserPosition(assUuid, user.getUserStuUuid());
        if (position.equals("1")) {
            //职位符合,执行逻辑
            int result = assAdminMapper.updateAssNameInAssociationInfo(assUuid, newData);
            if (result > 0) {
                //执行成功
                return "0";
            } else {
                //执行失败
                return "-2";
            }
        } else {
            //职位不付
            return "-4";
        }
    }

    /**
     * 管理台修改社团学校
     *
     * @param assUuid
     * @param newData
     * @param user
     * @return
     */
    @Transactional
    public String assAdminUniveisityChange(String assUuid, String newData, LoginSessionVo user) {
        //检测用户是否具有权限
        String position = assAdminMapper.queryUserPosition(assUuid, user.getUserStuUuid());
        //检查学校名称是否存在
        List<String> universityCode = assAdminMapper.queryUniversityCodeByName(newData);
        if (position != null && position.equals("1") && universityCode.size() > 0) {
            //职位符合,执行逻辑
            int result = assAdminMapper.updateAssUniversityInAssociationInfo(assUuid, newData);
            if (result > 0) {
                //执行成功
                return "0";
            } else {
                //执行失败
                return "-2";
            }
        } else {
            //职位不付
            return "-4";
        }
    }

    /**
     * 修改部门信息
     *
     * @param departmentBean
     * @param user
     * @return
     */
    @Transactional
    public String assAdminDepChange(DepartmentBean departmentBean, LoginSessionVo user) {
        //检测用户是否具有权限
        String position = assAdminMapper.queryUserPositionByDepUuid(departmentBean.getDepartmentUuid(), user.getUserStuUuid());
        if (position != null && position.equals("1")) {
            int result = assAdminMapper.updateDepInfo(departmentBean);
            if (result > 0) {
                return "0";
            } else {
                return "-2";
            }
        } else {
            return "-4";
        }

    }

    /**
     * 删除部门和部门中的人员信息
     *
     * @param
     * @param user
     * @return
     */
    @Transactional
    public String deleteDep(String departmentUuid, LoginSessionVo user) {
        //先查询是不是会长团,会长团不允许删除
        String position2 = assAdminMapper.queryPresident(departmentUuid, user.getUserStuUuid());
        if (position2 != null && position2.equals("1")) {
            return "-5";
        }

        //检测用户是否具有权限
        String position1 = assAdminMapper.queryUserPositionByDepUuid(departmentUuid, user.getUserStuUuid());
        if (position1 != null && position1.equals("1")) {
            //删除部门
            int result = assAdminMapper.deleteDepByDepUuid(departmentUuid);
            //删除部门中的所有人
            int result2 = assAdminMapper.deleteDepMemberByDepUuid(departmentUuid);
            return "0";
        } else {
            return "-4";
        }
    }

    /**
     * 添加社团
     *
     * @param assUuid
     * @param departmentName
     * @param departmentDescription
     * @param user
     * @return
     */
    public String addDep(String assUuid, String departmentName, String departmentDescription, LoginSessionVo user) {
        //检测用户是否具有权限
        String position = assAdminMapper.queryUserPosition(assUuid, user.getUserStuUuid());
        if (position != null && position.equals("1")) {
            //给部门对象赋值
            DepartmentBean newDep = new DepartmentBean();
            newDep.setDepartmentUuid(UUID.randomUUID().toString());
            newDep.setDepartmentName(departmentName);
            newDep.setDepartmentDescription(departmentDescription);

            //插入数据库
            int result = assAdminMapper.insertDepIntoDepartments(assUuid, newDep);
            if (result > 0) {
                return "0";
            } else {
                return "-2";
            }
        } else {
            return "-4";
        }

    }
}
