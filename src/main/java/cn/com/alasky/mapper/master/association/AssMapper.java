package cn.com.alasky.mapper.master.association;

import cn.com.alasky.vo.association.AssInfoVo;
import cn.com.alasky.vo.association.DepInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/30/2020 4:43 PM
 * Package: cn.com.alasky.mapper.master.ass
 * Description:
 */
public interface AssMapper {
    /**
     * 根据学生uuid查询学校code
     *
     * @param stuUuid
     * @return
     */
    @Select("SELECT un.id_code\n" +
            "FROM student_info si\n" +
            "INNER JOIN university un\n" +
            "ON si.university_code=un.id_code\n" +
            "WHERE si.stu_uuid = #{stuUuid}")
    String queryUniversityCodeByStuUuid(String stuUuid);


    /**
     * 根据学校代码查询所有的社团基本信息
     *
     * @param universityCode
     * @return
     */
    @Select("SELECT ai.ass_uuid, un.university_name, ai.association_name AS ass_name\n" +
            "FROM association_info ai\n" +
            "INNER JOIN university un\n" +
            "ON ai.university_code=un.id_code\n" +
            "WHERE ai.university_code =#{universityCode} ")
    List<AssInfoVo> queryUniversityAsses(String universityCode);


    /**
     * 根据assUuid活动社团的名称和学校
     * @param assUuid
     * @return
     */
    @Select("SELECT ai.ass_uuid, un.university_name, ai.association_name AS ass_name, ai.custom_url\n" +
            "FROM association_info ai\n" +
            "INNER JOIN university un\n" +
            "ON ai.university_code=un.id_code\n" +
            "WHERE ai.ass_uuid=#{assUuid}")
    AssInfoVo queryAssInfoByAssUuid(String assUuid);


    /**
     * 根据assUuid查询部门的基本信息
     * @param assUuid
     * @return
     */
    @Select("SELECT dep_uuid, department_name, department_description\n" +
            "FROM departments\n" +
            "WHERE ass_uuid=#{assUuid}")
    List<DepInfoVo> queryDepsInfoByAssUuid(String assUuid);






}
