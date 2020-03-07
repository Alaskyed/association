package cn.com.alasky.mapper.admin;

import cn.com.alasky.dao.StuAssDao;
import cn.com.alasky.domain.DepartmentBean;
import cn.com.alasky.domain.NewAssBean;
import cn.com.alasky.vo.AssDataVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 2/26/2020 10:55 AM
 * Package: cn.com.alasky.mapper.admin
 * Description:
 */
public interface AssDataMapper {
    /**
     * 从视图ass_info中查询社团信息
     * @param userPhoneNumber
     * @return
     */
    @Select("SELECT * FROM ass_info " +
            "WHERE phone_number= #{userPhoneNumber}")
    List<AssDataVo> queryAssDataFromAssInfo(String userPhoneNumber);

    /**
     * 在社团信息表中插入社团uuid,学校和社团名称
     * @param assUuid
     * @param newAssBean
     * @return
     */
    @Insert("INSERT INTO association_info(ass_uuid,university_name,association_name) " +
            "VALUES(#{a},#{n.universityName},#{n.associationName})")
    int insertInfoIntoAssociationInfo(@Param("a") String assUuid,@Param("n") NewAssBean newAssBean);

    /**
     * 在部门表中插入部门信息
     * @param assUuid
     * @param departmentBean
     * @return
     */
    @Insert("INSERT INTO departments(ass_uuid,dep_uuid,department_name,department_description) " +
            "VALUES(#{assUuid},#{d.departmentUuid},#{d.departmentName},#{d.departmentDescription})")
    int insertInfoIntoDepartments(String assUuid,@Param("d") DepartmentBean departmentBean);

    /**
     * 用手机号查询stu_uuid
     * @param userPhoneNumber
     * @return
     */
    @Select("SELECT stu_uuid " +
            "FROM `user` " +
            "WHERE phone_number=#{userPhoneNumber}")
    List<String> queryStuUuidByPhoneNumber(String userPhoneNumber);

    /**
     * 插入stu_ass信息
     * @param stuAssDao
     * @return
     */
    @Insert("INSERT INTO stu_ass(`stu_uuid`,`ass_uuid`,`dep_uuid`,`position`,`status`) " +
            "values(#{sa.stuUuid},#{sa.assUuid},#{sa.depUuid},#{sa.position},#{sa.status})")
    int insertInfoIntoStuAss(@Param("sa") StuAssDao stuAssDao);


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
     * 查询某学校的社团名称是否存在,防止社团重复
     * @param newAssBean
     * @return
     */
    @Select("SELECT association_name " +
            "FROM association_info " +
            "WHERE university_name=#{n.universityName} AND association_name=#{n.associationName} ")
    List<String> queryAssNameFromAssociationInfo(@Param("n") NewAssBean newAssBean);
}
