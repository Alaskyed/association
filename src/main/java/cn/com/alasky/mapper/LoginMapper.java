package cn.com.alasky.mapper;

import cn.com.alasky.domain.LoginBean;
import cn.com.alasky.domain.UserBean;
import cn.com.alasky.vo.LoginSessionVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * cn.com.alasky.mapper
 * 2020/2/1 下午12:14
 * author: Alasky
 * description:
 */

public interface LoginMapper {
    @Select("SELECT u.user_name AS user_name, u.phone_number AS user_phone_number, s.stu_uuid AS user_stu_uuid\n" +
            "FROM `user` u\n" +
            "INNER JOIN student_info s\n" +
            "ON u.stu_uuid=s.stu_uuid\n" +
            "WHERE u.phone_number= #{u.loginUserName}\n" +
            "AND u.`password`= #{u.loginPassword}")
    List<LoginSessionVo> queryUser(@Param("u") LoginBean loginBean);
}
