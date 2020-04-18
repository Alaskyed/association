package cn.com.alasky.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@ToString
public class UserSession {
    @Getter
    @Setter
    private String userUuid;

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
