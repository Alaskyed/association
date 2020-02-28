package cn.com.alasky.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: Alaskyed
 * Time: 2/26/2020 10:57 AM
 * Package: cn.com.alasky.vo
 * Description:
 */
@ToString
public class AssDataVo {
    @Getter
    @Setter
    private String assName;

    @Getter
    @Setter
    private String universityName;

    @Getter
    @Setter
    private String departmentName;

    @Getter
    @Setter
    private String position;

    @Getter
    @Setter
    private String status;

}
