package cn.com.alasky.mapper.master.admin;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 2/28/2020 8:17 PM
 * Package: cn.com.alasky.mapper.master.admin
 * Description:
 */
public interface AdminMapper {
    /**
     * 获取用户的职位
     *
     * @return
     */
    @Select("SELECT position " +
            "FROM stu_ass " +
            "WHERE stu_uuid= #{userStuUuid} ")
    List<String> queryPositionFromStuAss(String userStuUuid);
}
