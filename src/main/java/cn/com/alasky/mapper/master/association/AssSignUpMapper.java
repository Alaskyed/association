package cn.com.alasky.mapper.master.association;

import cn.com.alasky.domain.AssSignUpBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 4/7/2020 11:35 AM
 * Package: cn.com.alasky.mapper.master.association
 * Description:
 */
public interface AssSignUpMapper {
    /**
     * 插入新的社团报名信息
     * @param assSignUpBean
     * @return
     */
    @Insert("insert into ass_sign_up(user_uuid, ass_uuid, ass_sign_up_name, ass_sign_up_gender, ass_sign_up_dep_uuid, " +
            "ass_sign_up_introduce, ass_sign_up_reason) \n" +
            "values(#{a.userUuid},#{a.assUuid}, #{a.assSignUpName}, #{a.assSignUpGender}, #{a.assSignUpDepUuid}, " +
            "#{a.assSignUpIntroduce}, #{a.assSignUpReason})")
    int insertSignUpInfoIntoAssSignUp(@Param("a") AssSignUpBean assSignUpBean);


    /**
     * 获取到报名表, 首先检查自己是不是这个社团的人员
     * @param userStuUuid
     * @param assUuid
     * @return
     */
    @Select("select stu_uuid \n" +
            "from stu_ass sa \n" +
            "where stu_uuid = #{userStuUuid} \n" +
            "and ass_uuid = #{assUuid}")
    List<String> checkIsThisAssMember(String userStuUuid, String assUuid);


    /**
     * 检查是否已经提交了该社团的报名表
     * @return 返回该报名表的id
     */
    @Select("select id\n" +
            "from ass_sign_up asu \n" +
            "where user_uuid = #{asu.userUuid} \n" +
            "and ass_uuid =#{asu.assUuid} ")
    List<Long> checkIsAlreadySignUp(@Param("asu") AssSignUpBean assSignUpBean);

    /**
     * 检测到重复报名, 删除之前的报名表
     * @param signUpId
     */
    @Delete("delete from ass_sign_up where id = #{signUpId}")
    void delSignUp(Long signUpId);
}
