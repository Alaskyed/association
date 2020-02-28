package cn.com.alasky.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 2/27/2020 5:41 PM
 * Package: cn.com.alasky.domain
 * Description:
 */
@ToString
public class NewAssBean {
    @Getter
    @Setter
    private String associationName;

    @Getter
    @Setter
    private String universityName;

    @Getter
    @Setter
    private List<DepartmentBean> departments;
}

