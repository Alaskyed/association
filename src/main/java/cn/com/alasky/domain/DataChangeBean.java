package cn.com.alasky.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@ToString
public class DataChangeBean {
    @Getter
    @Setter
    private String userPhoneNumber;

    @Getter
    @Setter
    private String newValue;
}
