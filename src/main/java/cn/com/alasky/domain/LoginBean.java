package cn.com.alasky.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * cn.com.alasky.pojo
 * 2020/2/1 下午12:28
 * author: Alasky
 * description: 登录信息的实体类
 */

@ToString
public class LoginBean {
    @Getter
    @Setter
    private String loginUserName;
    @Getter
    @Setter
    private String loginPassword;


}
