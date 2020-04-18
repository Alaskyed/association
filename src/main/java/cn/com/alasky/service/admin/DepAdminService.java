package cn.com.alasky.service.admin;

import cn.com.alasky.dao.AssMemberDao;
import cn.com.alasky.domain.DepartmentBean;
import cn.com.alasky.mapper.master.admin.DepAdminMapper;
import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.vo.admin.DepAdminVo;
import cn.com.alasky.pojo.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/5/2020 5:52 PM
 * Package: cn.com.alasky.service.admin
 * Description:
 */
@Service
public class DepAdminService {
    @Autowired
    private DepAdminMapper depAdminMapper;

    /**
     * 获取部门管理的所有信息
     *
     * @param user
     */
    public List<DepAdminVo> getDepAdminAssInfo(UserSession user) {
        //先将是会长或者部长的社团基本信息加到vo列表里
        List<DepAdminVo> depAdminVos = depAdminMapper.queryPositionByStuUuid(user.getUserStuUuid());
        for (DepAdminVo depAdminVo : depAdminVos) {
            // 先判断是不是会长,如果会长直接加入所有部门
            if (depAdminVo.getPosition().equals("1")) {
                //是会长,把所有部门都加入
                List<DepartmentBean> departments = depAdminMapper.queryDepInfoByAssUuid(depAdminVo.getAssUuid());
                for (DepartmentBean departmentBean : departments) {
                    //把每个部门的人员加入部门
                    List<AssMemberDao> assMemberDaos = depAdminMapper.queryDepMemberByDepUuid(departmentBean.getDepartmentUuid());
                    //加入对应的部门对象
                    departmentBean.setMembers(assMemberDaos);
                }
                //加入社团对象
                depAdminVo.setDepartments(departments);
            } else if (depAdminVo.getPosition().equals("2")) {
                //不是会长,是部长,获取部门uuid,只加入这个部门的人员,注意这里有可能在多个部门当部长
                //查出在本社团担任部长的部门uuid和名称
                List<DepartmentBean> departments = depAdminMapper.queryDepInfoByStuUuid(user.getUserStuUuid());
                for (DepartmentBean departmentBean : departments) {
                    //把每个部门的人员加入部门
                    List<AssMemberDao> assMemberDaos = depAdminMapper.queryDepMemberByDepUuid(departmentBean.getDepartmentUuid());
                    //加入对应的部门对象
                    departmentBean.setMembers(assMemberDaos);
                }
                //加入社团对象
                depAdminVo.setDepartments(departments);
            }
        }
        return depAdminVos;
    }

    /**
     * 删除部门干事
     *
     * @param depUuid
     * @param stuUuid
     * @return
     */
    @Transactional
    public String deleteStu(String depUuid, String stuUuid) {
        //检查是否是部门部长
        int isAdm = depAdminMapper.queryIsAdminInDep(stuUuid, depUuid);
        if (isAdm > 0) {
            //检查该部门剩余领导数目,如果<2,则不允许删除
            int admSum =depAdminMapper.queryNumInDep(depUuid);
            if (admSum < 2) {
                return "-5";
            }
        }
        //执行删除
        int result = depAdminMapper.deleteStuInDep(depUuid, stuUuid);
        if (result > 0) {
            int i = 1 / 0;
            return ReturnValue.SUCCESS.value();
        } else {
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }
}
