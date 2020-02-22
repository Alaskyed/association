package cn.com.alasky.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * cn.com.alasky.pojo
 * 2020/1/29 下午10:39
 * author: Alasky
 * description: 注册信息的实体类
 */

@ToString
public class RegisterBean {
    @Getter
    @Setter
    private String registerUserName;
    @Getter
    @Setter
    private String registerPhoneNumber;
    @Getter
    @Setter
    private String registerUniversity;
    @Getter
    @Setter
    private String registerPassword;
    @Getter
    @Setter
    private String registerEmail;
    @Getter
    @Setter
    private String registerQQ;
    @Getter
    @Setter
    private String registerWechat;

}
