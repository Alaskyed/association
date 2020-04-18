package cn.com.alasky.mapper.master.admin;

import cn.com.alasky.vo.activities.ActAdminNameVo;
import cn.com.alasky.vo.activities.ActSignUpInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/29/2020 9:45 PM
 * Package: cn.com.alasky.mapper.master.admin
 * Description:
 */
public interface ActSignUpAdminMapper {

    /**
     * 根据stu_uuid查询担任部长或会长的社团的活动名称
     * @param userStuUuid
     * @return
     */
    @Select("SELECT a.act_name, a.act_id, un.university_name, ai.association_name AS ass_name\n" +
            "FROM stu_ass sa \n" +
            "INNER JOIN activites a \n" +
            "ON sa.ass_uuid=a.ass_uuid\n" +
            "INNER JOIN association_info ai\n" +
            "ON a.ass_uuid=ai.ass_uuid\n" +
            "INNER JOIN university un\n" +
            "ON ai.university_code=un.id_code " +
            "WHERE sa.stu_uuid=#{userStuUuid} " +
            "AND sa.position IN(1,2) " +
            "AND UNIX_TIMESTAMP(a.end_time)>UNIX_TIMESTAMP(NOW())")
    List<ActAdminNameVo> queryActNameByStuId(String userStuUuid);


    /**
     * 根据活动id获取活动报名信息
     * @param actId
     * @return
     */
    @Select("SELECT asu.user_uuid, asu.act_sign_up_name, asu.act_sign_up_time, asu.act_sign_up_remarks, un.university_name " +
            "FROM act_sign_up asu " +
            "INNER JOIN `user` u " +
            "ON asu.user_uuid=u.user_uuid " +
            "INNER JOIN student_info si " +
            "ON u.stu_uuid=si.stu_uuid " +
            "INNER JOIN university un " +
            "ON si.university_code=un.id_code " +
            "WHERE act_id=#{actId}")
    List<ActSignUpInfoVo> queryActSignUpInfosByActId(int actId);
}
