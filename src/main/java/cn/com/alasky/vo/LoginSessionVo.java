package cn.com.alasky.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@ToString
public class LoginSessionVo {
    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String userPhoneNumber;

    @Getter
    @Setter
    private String userStuUuid;
}
