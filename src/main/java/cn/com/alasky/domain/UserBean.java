package cn.com.alasky.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserBean {
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String phoneNumber;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String qq;
    @Getter
    @Setter
    private String wechat;
    @Getter
    @Setter
    private String stuUuid;

}
