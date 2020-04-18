package cn.com.alasky.vo.admin;

import cn.com.alasky.domain.DepartmentBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/5/2020 6:17 PM
 * Package: cn.com.alasky.vo
 * Description:
 */
@ToString
public class DepAdminVo {
    @Getter
    @Setter
    private String assUuid;
    @Getter
    @Setter
    private String assName;
    @Getter
    @Setter
    private String assUniversity;
    @Getter
    @Setter
    private String position;
    @Getter
    @Setter
    private List<DepartmentBean> departments;

}
