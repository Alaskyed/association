package cn.com.alasky.domain;

import lombok.Data;
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
@Data
public class NewAssBean {
    private String associationName;
    private String universityName;
    private String universityCode;
    private String customUrl;
    private List<DepartmentBean> departments;
}

