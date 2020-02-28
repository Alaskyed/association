package cn.com.alasky.mapper.admin;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 2/28/2020 8:17 PM
 * Package: cn.com.alasky.mapper.admin
 * Description:
 */
public interface AdminMapper {
    /**
     * 获取用户的职位
     *
     * @param userPhoneNumber
     * @return
     */
    @Select("SELECT position " +
            "FROM stu_ass " +
            "WHERE stu_uuid=( " +
            "SELECT stu_uuid " +
            "FROM `user` " +
            "WHERE phone_number=#{userPhoneNumber} " +
            ")")
    List<String> queryPositionFromStuAss(String userPhoneNumber);
}
