package cn.com.alasky.mapper.admin;

import cn.com.alasky.domain.DepartmentBean;
import cn.com.alasky.vo.AssAdminVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/1/2020 11:34 AM
 * Package: cn.com.alasky.mapper.admin
 * Description:
 */
public interface AssAdminMapper {
    /**
     * 查出该学生任会长的社团uuid
     * @param userStuUuid
     * @return
     */
    @Select("SELECT ass_uuid  " +
            "FROM stu_ass " +
            "WHERE stu_uuid = #{stuUuid} " +
            "AND position = '1' ")
    List<String> queryAssUuidsFromAssUuid(@Param("stuUuid") String userStuUuid);

    /**
     * 根据ass_uuid查询社团名
     * @param assUuid
     * @return
     */
    @Select("SELECT a.ass_uuid AS ass_uuid, a.university_name AS ass_university, a.association_name AS ass_name,COUNT(*) AS ass_member_count " +
            "FROM association_info a " +
            "INNER JOIN stu_ass sa " +
            "ON a.ass_uuid=sa.ass_uuid " +
            "WHERE a.ass_uuid = #{assUuid} ")
    AssAdminVo queryAssInfosByAssUuid(String assUuid);

    /**
     * 根据ass_uuid查询该社团的部门信息
     * @param assUuid
     * @return
     */
    @Select("SELECT d.dep_uuid AS department_uuid, d.department_name AS department_name,d.department_description AS department_description, COUNT(sa.stu_uuid) AS department_member_count " +
            "FROM departments d " +
            "LEFT JOIN stu_ass sa " +
            "ON d.dep_uuid=sa.dep_uuid " +
            "WHERE d.ass_uuid= #{assUuid} " +
            "GROUP BY d.dep_uuid,d.department_name,d.department_description")
    List<DepartmentBean> queryDepInfoByAssUuid(String assUuid);



    /**
     * 获取用户在该社团的职位
     * @param assUuid
     * @param userStuUuid
     * @return
     */
    @Select("SELECT position " +
            "FROM stu_ass " +
            "WHERE stu_uuid= #{userStuUuid} " +
            "AND ass_uuid= #{assUuid}")
    String queryUserPosition(String assUuid, String userStuUuid);

    /**
     * 更新社团名称
     * @return
     * @param assUuid
     * @param newData
     */
    @Update("UPDATE association_info " +
            "SET association_name=#{newData} " +
            "WHERE ass_uuid=#{assUuid}")
    int updateAssNameInAssociationInfo(String assUuid, String newData);

    /**
     * 更新社团的学校
     * @param assUuid
     * @param newData
     * @return
     */
    @Update("UPDATE association_info " +
            "SET university_name=#{newData} " +
            "WHERE ass_uuid=#{assUuid}")
    int updateAssUniversityInAssociationInfo(String assUuid, String newData);

    /**
     * 根据学校名称查询学校标识符
     *
     * @param universityName
     * @return
     */
    @Select("SELECT id_code " +
            "FROM university " +
            "WHERE university_name=#{universityName}")
    List<String> queryUniversityCodeByName(String universityName);

    /**
     * 根据部门uuid查询职位
     * @param departmentUuid
     * @param userStuUuid
     * @return
     */
    @Select("SELECT position " +
            "FROM stu_ass " +
            "WHERE stu_uuid= #{userStuUuid} " +
            "AND ass_uuid=( " +
            "SELECT ass_uuid " +
            "FROM departments " +
            "WHERE dep_uuid= #{departmentUuid}) " )
    String queryUserPositionByDepUuid(String departmentUuid, String userStuUuid);

    /**
     * 更新部门信息
     * @param departmentBean
     * @return
     */
    @Update("UPDATE departments " +
            "SET department_name=#{d.departmentName},department_description=#{d.departmentDescription} " +
            "WHERE dep_uuid=#{d.departmentUuid}")
    int updateDepInfo(@Param("d") DepartmentBean departmentBean);

    /**
     * 根据部门uuid删除部门
     * @param departmentUuid
     * @return
     */
    @Delete("DELETE FROM departments WHERE dep_uuid=#{departmentUuid}")
    int deleteDepByDepUuid(String departmentUuid);

    /**
     * 根据部门信息删除部门中所有的成员
     * @param departmentUuid
     * @return
     */
    @Delete("DELETE FROM stu_ass WHERE dep_uuid=#{departmentUuid}")
    int deleteDepMemberByDepUuid(String departmentUuid);

    /**
     * 检查部门uuid和学生uuid是不是在一个部门,如果是,说明是会长团
     * @param departmentUuid
     * @param userStuUuid
     * @return
     */
    @Select("SELECT position " +
            "FROM stu_ass " +
            "WHERE stu_uuid='67023fcb-c31d-4c05-b41f-14ce055e01aa' " +
            "AND dep_uuid= #{departmentUuid} ")
    String queryPresident(String departmentUuid, String userStuUuid);

    /**
     * 在部门表中插入部门信息
     * @param assUuid
     * @return
     */
    @Insert("INSERT INTO departments(ass_uuid,dep_uuid,department_name,department_description) " +
            "VALUES(#{assUuid},#{d.departmentUuid},#{d.departmentName},#{d.departmentDescription})")
    int insertDepIntoDepartments(String assUuid, @Param("d") DepartmentBean newDep);
}
