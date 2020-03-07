package cn.com.alasky.domain;

import cn.com.alasky.dao.AssMemberDao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 2/28/2020 11:07 AM
 * Package: cn.com.alasky.domain
 * Description:
 */
@ToString
public class DepartmentBean {
    //uuid
    @Getter
    @Setter
    private String departmentUuid;

    @Getter
    @Setter
    private String departmentName;

    @Getter
    @Setter
    private String departmentDescription;

    //部门人数
    @Getter
    @Setter
    private String departmentMemberCount;

    @Getter
    @Setter
    private List<AssMemberDao> members;
}
