package cn.com.alasky.mapper;

import cn.com.alasky.domain.RegisterBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * cn.com.alasky.mapper
 * 2020/1/30 下午8:43
 * author: Alasky
 * description:
 */

public interface RegisterMapper {

    @Insert("insert into user values(#{r.registerUserName},#{r.registerPassword},#{r.registerPhoneNumber},#{r.registerEmail},null)")
    void insertNewUser(@Param("r") RegisterBean registerBean);


}

