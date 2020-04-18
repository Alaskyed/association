package cn.com.alasky.mapper.master;

import cn.com.alasky.domain.UserBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * cn.com.alasky.mapper
 * 2020/1/30 下午8:43
 * author: Alasky
 * description:
 */

public interface RegisterMapper {

    /**
     * 新用户注册
     * @param userUuid
     * @param userBean
     */
    @Insert("insert into user(user_uuid,user_name,password,phone_number,email,qq,wechat,stu_uuid) " +
            "values(#{userUuid},#{u.userName},#{u.password},#{u.phoneNumber},#{u.email},#{u.qq},#{u.wechat},#{u.stuUuid})")
    void insertNewUser(String userUuid, @Param("u") UserBean userBean);

    /**
     * 新用户注册时同步更新学生表的内容
     * @param stuUuid
     */
    @Insert("insert into student_info(stu_uuid) values(#{stuUuid})")
    void insertUUIDIntoStuInfo(String stuUuid);


    /**
     * 根据学校名称查询学校标识符
     * @param universityName
     * @return
     */
    @Select("SELECT id_code " +
            "FROM university " +
            "WHERE university_name=#{universityName}")
    List<String> queryUniversityCodeByName(String universityName);

    /**
     * 向学生信息表中插入学校的标识码
     * @param
     * @return
     */
    @Insert("UPDATE student_info " +
            "SET university_code = #{universityCode} " +
            "WHERE stu_uuid = ( " +
            "SELECT stu_uuid " +
            "FROM `user` " +
            "WHERE phone_number = #{phoneNumber}) ")
    int updateUniversityCodeIntoStudentInfo(String universityCode, String phoneNumber);
}

