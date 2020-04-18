package cn.com.alasky.mapper.master.activites;

import cn.com.alasky.domain.NewActBean;
import cn.com.alasky.vo.activities.AssNameVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/15/2020 10:23 AM
 * Package: cn.com.alasky.mapper.master.activites
 * Description:
 */
public interface NewActMapper {

    /**
     * 根据学生uuid获取当会长的社团名称
     * @param userStuUuid
     * @return
     */
    @Select("SELECT sa.ass_uuid AS ass_uuid, a.association_name AS ass_name " +
            "FROM stu_ass sa " +
            "INNER JOIN association_info a " +
            "ON sa.ass_uuid=a.ass_uuid " +
            "WHERE sa.stu_uuid=#{userStuUuid}" +
            "AND sa.position='1' ")
    public List<AssNameVo> queryAssNamesByAdminStuUuid(String userStuUuid);

    /**
     * 插入新的活动信息
     * @param newAct
     * @return
     */
    @Insert("INSERT INTO activites(act_name,act_content,ass_uuid,stu_uuid,contact_info,release_time,start_time,end_time," +
            "deadline_time,address,remarks,is_open) " +
            "VALUES(#{n.newActName},#{n.newActContent},#{n.newActAssUuid},#{n.newActStuUuid},#{n.newActContact}," +
            "now(),str_to_date(#{n.newActStart},'%Y-%m-%d %H:%i:%s'),str_to_date(#{n.newActEnd},'%Y-%m-%d %H:%i:%s'),str_to_date(#{n.newActDeadLine},'%Y-%m-%d %H:%i:%s'),#{n.newActAddress},#{n.newActRemarks},#{n.newActIsOpen})")
    @Options(useGeneratedKeys = true, keyProperty = "newActId", keyColumn = "act_id")
    int insertNewActInfo(@Param("n") NewActBean newAct);

    /**
     * 更新活动图片的名称
     * @param newActId
     * @param newActPicName
     */
    @Update("UPDATE activites " +
            "SET act_pic_name=#{newActPicName} " +
            "WHERE act_id=#{newActId}")
    void updatePIcName(String newActId, String newActPicName);

    /**
     * 更新活动附件名称
     * @param newActId
     * @param newActEnclosureName
     */
    @Update("UPDATE activites " +
            "SET act_enclosure_name=#{newActEnclosureName} " +
            "WHERE act_id=#{newActId}")
    void updateEnclosureName(int newActId, String newActEnclosureName);
}
