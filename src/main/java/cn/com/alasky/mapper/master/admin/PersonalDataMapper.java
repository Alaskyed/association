package cn.com.alasky.mapper.master.admin;

import cn.com.alasky.domain.DataChangeBean;
import cn.com.alasky.domain.MajorBean;
import cn.com.alasky.vo.admin.PerosnalDataVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PersonalDataMapper {
    /**
     * 查询个人资料
     * 从视图 personal_data中查询
     *
     * @param userPhoneNumber
     * @return
     */
    @Select("SELECT n.university_name AS university, m.major_name AS major, s.stu_name AS name, s.stu_id AS stu_id, s.stu_grade AS grade " +
            "FROM `user` u " +
            "INNER JOIN student_info s " +
            "ON u.stu_uuid=s.stu_uuid " +
            "LEFT JOIN major_info m " +
            "ON s.major_uuid=m.major_uuid " +
            "LEFT JOIN university n " +
            "ON s.university_code=n.id_code " +
            "WHERE u.phone_number=#{userPhoneNumber} ")
    List<PerosnalDataVo> queryPersonalData(String userPhoneNumber);

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
     * 修改学生信息表中的学校标识符
     *
     * @param dataChangeBean
     * @return
     */
    @Update("UPDATE student_info  " +
            "SET university_code = #{n.newValue} " +
            "WHERE stu_uuid = ( " +
            "SELECT stu_uuid  " +
            "FROM `user`  " +
            "WHERE phone_number = #{n.userPhoneNumber} " +
            ")")
    int updateUniversityInStudentInfo(@Param("n") DataChangeBean dataChangeBean);

    /**
     * 根据手机号码查询该学生的学校标识符
     *
     * @param userPhoneNumber
     * @return
     */
    @Select("SELECT university_code " +
            "FROM student_info " +
            "WHERE stu_uuid=( " +
            "SELECT stu_uuid " +
            "FROM `user` " +
            "WHERE phone_number=#{userPhoneNumber} " +
            ")")
    List<String> queryUniversityCodeByPhoneNumber(String userPhoneNumber);

    /**
     * 查询响应的学校专业是否存在
     *
     * @param
     * @param universityCode
     * @return
     */
    @Select("SELECT major_uuid " +
            "FROM major_info " +
            "WHERE major_name=#{majorName} AND university_code=#{universityCode}")
    List<String> queryMajorUUIDInMajorInfo(String majorName, String universityCode);

    /**
     * 在学生信息表中更新专业的uuid
     *
     * @param dataChangeBean
     * @return
     */
    @Update("UPDATE student_info " +
            "SET major_uuid=#{m.newValue} " +
            "WHERE stu_uuid=( " +
            "SELECT stu_uuid " +
            "FROM `user` " +
            "WHERE phone_number=#{m.userPhoneNumber} " +
            ")")
    int updateMajorUuidInStudentInfo(@Param("m") DataChangeBean dataChangeBean);

    /**
     * 插入新的major
     *
     * @param majorBean
     * @return
     */
    @Insert("INSERT INTO major_info " +
            "VALUES(#{m.majorUuid}, #{m.universityCode}, #{m.majorName})")
    int insertNewMajorIntoMajorInfo(@Param("m") MajorBean majorBean);

    /**
     * 在学生表中更新姓名
     *
     * @param dataChangeBean
     * @return
     */
    @Update("UPDATE student_info " +
            "SET stu_name=#{s.newValue} " +
            "WHERE stu_uuid=( " +
            "SELECT stu_uuid " +
            "FROM user " +
            "WHERE phone_number=#{s.userPhoneNumber} " +
            ")")
    int updateNameInStudentInfo(@Param("s") DataChangeBean dataChangeBean);

    /**
     * 在学生表中更新学号
     * @return
     * @param dataChangeBean
     */
    @Update("UPDATE student_info " +
            "SET stu_id=#{s.newValue} " +
            "WHERE stu_uuid=( " +
            "SELECT stu_uuid  " +
            "FROM user " +
            "WHERE phone_number=#{s.userPhoneNumber} " +
            ")")
    int updateStuIdInStudentInfo(@Param("s") DataChangeBean dataChangeBean);

    /**
     * 在学生表中更新年级
     * @param dataChangeBean
     * @return
     */
    @Update("UPDATE student_info " +
            "SET stu_grade=#{s.newValue} " +
            "WHERE stu_uuid=( " +
            "SELECT stu_uuid  " +
            "FROM user " +
            "WHERE phone_number=#{s.userPhoneNumber} " +
            ")")
    int updateGradeInStudentInfo(@Param("s") DataChangeBean dataChangeBean);

    /**
     * 查询用户加入的社团名称
     * @return
     */
    @Select("SELECT CONCAT(un.university_name,\" - \",ai.association_name)\n" +
            "FROM student_info si\n" +
            "INNER JOIN university un\n" +
            "ON si.university_code=un.id_code\n" +
            "INNER JOIN stu_ass sa\n" +
            "ON si.stu_uuid=sa.stu_uuid\n" +
            "INNER JOIN association_info ai\n" +
            "ON sa.ass_uuid=ai.ass_uuid\n" +
            "WHERE si.stu_uuid=#{userStuUuid} ")
    List<String> queryAssNames(String userStuUuid);


    /**
     * 在专业表中修改学院名称
     * @param dataChangeBean
     * @return
     */
//    @Update("")
//    int updateAcademyInStudentInfo(DataChangeBean dataChangeBean);
}
