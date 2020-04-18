package cn.com.alasky.mapper.master.activites;

import cn.com.alasky.domain.ActSignUpBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/29/2020 7:27 PM
 * Package: cn.com.alasky.mapper.master.activites
 * Description:
 */
public interface ActSignUpMapper {

    /**
     * 插入新的活动报名信息
     * @return
     */
    @Insert("INSERT INTO act_sign_up(user_uuid, `act_sign_up_name`,act_id, act_sign_up_remarks, act_sign_up_time) " +
            "VALUES(#{a.userUuid},#{a.actSignUpName},#{a.actId},#{a.actSignUpRemarks},now())")
    public int InserNewActSignUp(@Param("a") ActSignUpBean actSignUpBean);

    /**
     * 报名人数+1
     */
    @Update("UPDATE activites " +
            "SET sign_up_num=sign_up_num+1 " +
            "WHERE act_id=#{actId}")
    void signUpNumIncreaement(int actId);


    /**
     * 查询是否已经报名
     * @param userUuid
     * @param actSignUpBean
     * @return
     */
    @Select("select id\n" +
            "from act_sign_up \n" +
            "where user_uuid = #{userUuid} \n" +
            "and act_id = #{actId} ")
    List<String> checkActSignUp(String userUuid, int actId);
}
