package cn.com.alasky.mapper.master.admin;

import cn.com.alasky.domain.DataChangeBean;
import cn.com.alasky.domain.UserBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserDataMapper {


    /**
     * 查询基本的用户资料(不包括密码和stu_uuid)
     * 从视图 user_data中查询
     */
    @Select("select * " +
            "from user_data " +
            "where phone_number=#{userPhoneNumber}")
    List<UserBean> queryUserData(String userPhoneNumber);

    /**
     * 修改姓名
     * @param dataChangeBean
     * @return
     */
    @Update("UPDATE user " +
            "SET user_name=#{u.newValue} " +
            "WHERE phone_number=#{u.userPhoneNumber}")
    int updateUserName(@Param("u") DataChangeBean dataChangeBean);

    /**
     * 修改手机号
     * @param dataChangeBean
     * @return
     */
    @Update("UPDATE user " +
            "SET phone_number=#{u.newValue} " +
            "WHERE phone_number=#{u.userPhoneNumber}")
    int updatePhoneNumber(@Param("u") DataChangeBean dataChangeBean);

    /**
     * 修改邮箱
     * @param dataChangeBean
     * @return
     */
    @Update("UPDATE user " +
            "SET email=#{u.newValue} " +
            "WHERE phone_number=#{u.userPhoneNumber}")
    int updateEmail(@Param("u") DataChangeBean dataChangeBean);

    /**
     * 修改QQ
     * @param dataChangeBean
     * @return
     */
    @Update("UPDATE user " +
            "SET qq=#{u.newValue} " +
            "WHERE phone_number=#{u.userPhoneNumber}")
    int updateQq(@Param("u") DataChangeBean dataChangeBean);

    /**
     * 修改微信
     * @param dataChangeBean
     * @return
     */
    @Update("UPDATE user " +
            "SET wechat=#{u.newValue} " +
            "WHERE phone_number=#{u.userPhoneNumber}")
    int updateWechat(@Param("u") DataChangeBean dataChangeBean);
}
