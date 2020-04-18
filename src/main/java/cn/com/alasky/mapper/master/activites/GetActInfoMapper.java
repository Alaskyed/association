package cn.com.alasky.mapper.master.activites;

import cn.com.alasky.vo.activities.ActInfoVo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/16/2020 10:26 AM
 * Package: cn.com.alasky.mapper.master.activites
 * Description:
 */
public interface GetActInfoMapper {
    /**
     * 获取热度较高的活动
     * @return
     */
    @Select("SELECT a.act_id, a.act_name, a.release_time, a.remarks,a.act_pic_name, u.university_name\n" +
            "FROM activites a\n" +
            "LEFT JOIN association_info ai\n" +
            "ON a.ass_uuid=ai.ass_uuid\n" +
            "INNER JOIN university u\n" +
            "ON ai.university_code=u.id_code\n" +
            "WHERE a.is_open=1 AND UNIX_TIMESTAMP(a.end_time)>UNIX_TIMESTAMP(NOW())\n" +
            "ORDER BY a.hot DESC\n" +
            "LIMIT 0,4")
    List<ActInfoVo> getHighHotActInfo();

    /**
     * 根据索引获取后面20个活动
     * @param index
     * @return
     */
    @Select("SELECT a.*, un.university_name, ai.association_name AS ass_name\n" +
            "FROM activites a\n" +
            "LEFT JOIN association_info ai\n" +
            "ON a.ass_uuid=ai.ass_uuid\n" +
            "INNER JOIN university un\n" +
            "ON ai.university_code=un.id_code\n" +
            "WHERE a.is_open=1 AND UNIX_TIMESTAMP(a.end_time)>UNIX_TIMESTAMP(NOW())\n" +
            "ORDER BY a.release_time DESC\n" +
            "LIMIT 0,20 ")
    List<ActInfoVo> getLimitActs(int index);


    /**
     * 根据关键字获取活动
     * @param keySearch
     * @return
     */
    @Select("SELECT a.*, un.university_name, ai.association_name AS ass_name\n" +
            "FROM activites a\n" +
            "LEFT JOIN association_info ai\n" +
            "ON a.ass_uuid=ai.ass_uuid\n" +
            "INNER JOIN university un\n" +
            "ON ai.university_code=un.id_code\n" +
            "WHERE a.is_open=1 AND UNIX_TIMESTAMP(a.end_time)>UNIX_TIMESTAMP(NOW()) AND a.act_name LIKE '%${searchKey}%'\n" +
            "ORDER BY a.release_time DESC")
    List<ActInfoVo> getActSearchReault(String keySearch);


    /**
     * 根据id查询社团活动的详细信息
     * @param actId
     * @return
     */
    @Select("SELECT a.act_id, a.act_name, a.act_content, a.contact_info, a.release_time, a.start_time, a.deadline_time, a.end_time, a.address, a. remarks, a.hot, a.sign_up_num,a.act_enclosure_name, un.university_name, ai.association_name AS ass_name\n" +
            "FROM activites a\n" +
            "LEFT JOIN association_info ai\n" +
            "ON a.ass_uuid=ai.ass_uuid\n" +
            "INNER JOIN university un\n" +
            "ON ai.university_code=un.id_code " +
            "WHERE act_id = #{actId}")
    ActInfoVo queryActDetailByActId(int actId);

    /**
     * 活动hot+1
     * @param actId
     */
    @Update("UPDATE activites " +
            "SET hot=hot+1 " +
            "WHERE act_id = #{actId}")
    void actHotIncreament(int actId);

    /**
     * 获取本校的热门活动
     * @param stuUuid
     * @return
     */
    @Select("SELECT a.*, ai.university_code, ai.association_name AS ass_name\n" +
            "FROM activites a\n" +
            "LEFT JOIN association_info ai\n" +
            "ON a.ass_uuid=ai.ass_uuid\n" +
            "WHERE UNIX_TIMESTAMP(a.end_time)>UNIX_TIMESTAMP(NOW()) AND ai.university_code=(\n" +
            "\tSELECT u.id_code\n" +
            "\tFROM student_info si\n" +
            "\tINNER JOIN university u\n" +
            "\tON si.university_code=u.id_code\n" +
            "\tWHERE si.stu_uuid=#{stuUuid}\n" +
            ")\n" +
            "ORDER BY a.hot DESC\n" +
            "LIMIT 0,4")
    List<ActInfoVo> getUniversityHighHotActInfo(String stuUuid);




}
