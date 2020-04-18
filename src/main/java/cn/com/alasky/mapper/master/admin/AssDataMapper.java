package cn.com.alasky.mapper.master.admin;

import cn.com.alasky.dao.StuAssDao;
import cn.com.alasky.domain.DepartmentBean;
import cn.com.alasky.domain.NewAssBean;
import cn.com.alasky.vo.admin.AssDataVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 2/26/2020 10:55 AM
 * Package: cn.com.alasky.mapper.master.admin
 * Description:
 */
public interface AssDataMapper {
    /**
     * 查询加入的社团信息
     * @return
     */
    @Select("SELECT ai.ass_uuid, ai.association_name AS ass_name, un.university_name, d.department_name, sa.position, sa.`status`\n" +
            "FROM stu_ass sa\n" +
            "INNER JOIN association_info ai\n" +
            "ON sa.ass_uuid=ai.ass_uuid\n" +
            "INNER JOIN university un\n" +
            "ON ai.university_code=un.id_code\n" +
            "INNER JOIN departments d\n" +
            "ON sa.dep_uuid=d.dep_uuid\n" +
            "WHERE stu_uuid=#{userStuUuid}")
    List<AssDataVo> queryAssDataFromAssInfo(String userStuUuid);

    /**
     * 在社团信息表中插入社团uuid,学校和社团名称
     * @param assUuid
     * @param newAssBean
     * @return
     */
    @Insert("INSERT INTO association_info(ass_uuid,university_code,association_name, custom_url) " +
            "VALUES(#{a},#{n.universityCode},#{n.associationName}, #{n.customUrl})")
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
            "WHERE university_code=#{n.universityCode} AND association_name=#{n.associationName} ")
    List<String> queryAssNameFromAssociationInfo(@Param("n") NewAssBean newAssBean);

    /**
     * 退出社团时查看在该社团的职位
     * @param userStuUuid
     * @param assUuid
     * @return
     */
    @Select("select position \n" +
            "from stu_ass \n" +
            "where stu_uuid = #{userStuUuid} \n" +
            "and ass_uuid = #{assUuid} ")
    int queryPosition(String userStuUuid, String assUuid);


    /**
     * 查询会长还剩几人
     * @param assUuid
     * @return
     */
    @Select("select COUNT(*)\n" +
            "from stu_ass  \n" +
            "where ass_uuid = #{assUuid} ")
    int queryNum1(String assUuid);

    /**
     * 查询所在部门还剩几个管理员
     * @param userStuUuid
     * @param assUuid
     * @return
     */
    @Select("select COUNT(*)\n" +
            "from stu_ass\n" +
            "where position = 2\n" +
            "and dep_uuid = (\n" +
            "\tselect dep_uuid \n" +
            "\tfrom stu_ass \n" +
            "\twhere stu_uuid = #{userStuUuid}\n" +
            "\tand ass_uuid =#{assUuid})")
    int queryNum2(String userStuUuid, String assUuid);

    /**
     * 退出社团
     * @param userStuUuid
     * @param assUuid
     */
    @Delete("delete from stu_ass \n" +
            "where stu_uuid =#{userStuUuid} \n" +
            "and ass_uuid = #{assUuid}")
    int quitAss(String userStuUuid, String assUuid);
}
