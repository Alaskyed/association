package cn.com.alasky.mapper.admin;

import cn.com.alasky.dao.AssMemberDao;
import cn.com.alasky.domain.DepartmentBean;
import cn.com.alasky.vo.DepAdminVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/5/2020 5:51 PM
 * Package: cn.com.alasky.mapper.admin
 * Description:
 */
public interface DepAdminMapper {
    /**
     * 根据stu_uuid查询该用户是否有当会长或部长的社团,并返回基本信息
     *
     * @param userStuUuid
     * @return
     */
    @Select("SELECT DISTINCT sa.ass_uuid AS ass_uuid, a.association_name AS ass_name, a.university_name AS ass_university, sa.position AS position " +
            "FROM stu_ass sa " +
            "INNER JOIN association_info a " +
            "ON sa.ass_uuid=a.ass_uuid "+
            "WHERE stu_uuid= #{userStuUuid} AND sa.position IN(\"1\",\"2\") ")
    List<DepAdminVo> queryPositionByStuUuid(String userStuUuid);

    /**
     * 根据ass_uuid查部门信息
     *
     * @param assUuid
     * @return
     */
    @Select("SELECT dep_uuid AS department_uuid, department_name AS department_name " +
            "FROM departments " +
            "WHERE ass_uuid=#{assUuid} ")
    List<DepartmentBean> queryDepInfoByAssUuid(String assUuid);

    /**
     * 根据部门uuid查询部门中的所有人
     *
     * @param departmentUuid
     * @return
     */
    @Select("SELECT s.stu_uuid AS stu_uuid, s.stu_name AS stu_name, sa.position AS position " +
            "FROM stu_ass sa " +
            "LEFT JOIN student_info s " +
            "ON sa.stu_uuid=s.stu_uuid " +
            "WHERE sa.dep_uuid=#{departmentUuid} ")
    List<AssMemberDao> queryDepMemberByDepUuid(String departmentUuid);


    /**
     * 根据stu_uuid查部长部门(部长)
     * @param stuUuid
     * @return
     */
    @Select("SELECT d.dep_uuid AS department_uuid, d.department_name AS department_name " +
            "FROM departments d " +
            "INNER JOIN stu_ass sa " +
            "ON d.dep_uuid=sa.dep_uuid " +
            "WHERE sa.stu_uuid= #{stuUuid} AND sa.position=\"2\"")
    List<DepartmentBean> queryDepInfoByStuUuid(String stuUuid);

    /**
     * 删除部门中的干事,stu_ass中删除相关信息
     * @param depUuid
     * @param stuUuid
     * @return
     */
    @Delete("DELETE FROM stu_ass " +
            "WHERE dep_uuid=#{depUuid} AND stu_uuid=#{stuUuid}")
    int deleteStuInDep(String depUuid, String stuUuid);

    /**
     * 查询是否是该部门的部长
     * @param stuUuid
     * @return
     */
    @Select("SELECT COUNT(*) " +
            "FROM stu_ass " +
            "WHERE stu_uuid=#{stuUuid} " +
            "AND dep_uuid=#{depUuid}")
    int queryIsAdminInDep(String stuUuid, String depUuid);


    /**
     * 查询这个部门还有几个部长
     * @return
     */
    @Select("SELECT COUNT(*) " +
            "FROM stu_ass " +
            "WHERE dep_uuid=#{depUuid} " +
            "AND position IN('1','2')")
    int queryNumInDep(String depUuid);


}
