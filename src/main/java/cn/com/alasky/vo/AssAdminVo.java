package cn.com.alasky.vo;

import cn.com.alasky.domain.DepartmentBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/1/2020 11:36 AM
 * Package: cn.com.alasky.vo
 * Description:
 */
@ToString
public class AssAdminVo {
    @Getter
    @Setter
    private String assUuid;

    @Getter
    @Setter
    private String assUniversity;

    @Getter
    @Setter
    private String assName;

    //社团总人数
    @Getter
    @Setter
    private String assMemberCount;

    //社团部门信息
    @Getter
    @Setter
    private List<DepartmentBean> departments;

}
