package cn.com.alasky.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: Alaskyed
 * Time: 2/28/2020 11:07 AM
 * Package: cn.com.alasky.domain
 * Description:
 */
@ToString
public class DepartmentBean {
    @Getter
    @Setter
    private String departmentName;

    @Getter
    @Setter
    private String departmentDescription;
}
