package cn.com.alasky.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: Alaskyed
 * Time: 2/21/2020 9:47 PM
 * Package: cn.com.alasky.domain
 * Description:
 */
@ToString
public class MajorBean {
    @Getter
    @Setter
    private String majorUuid;
    @Getter
    @Setter
    private String universityCode;
    @Getter
    @Setter
    private String majorName;

}
