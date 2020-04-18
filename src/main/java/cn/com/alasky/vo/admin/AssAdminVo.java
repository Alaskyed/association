package cn.com.alasky.vo.admin;

import cn.com.alasky.domain.DepartmentBean;
import lombok.Data;
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
@Data
public class AssAdminVo {
    private String assUuid;
    private String assUniversity;
    private String assName;
    private String customUrl;
    //社团总人数
    private String assMemberCount;
    //社团部门信息
    private List<DepartmentBean> departments;

}
