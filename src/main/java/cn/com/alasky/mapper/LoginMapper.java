package cn.com.alasky.mapper;

import cn.com.alasky.domain.LoginBean;
import cn.com.alasky.domain.UserBean;
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
    @Select("select * from user " +
            "where phone_number=#{u.loginUserName} and password=#{u.loginPassword}")
    List<UserBean> queryUser(@Param("u") LoginBean loginBean);
}
