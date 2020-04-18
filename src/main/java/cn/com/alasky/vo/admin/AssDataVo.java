package cn.com.alasky.vo.admin;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: Alaskyed
 * Time: 2/26/2020 10:57 AM
 * Package: cn.com.alasky.vo
 * Description:
 */
@Data
public class AssDataVo {
    private String assUuid;
    private String assName;
    private String universityName;
    private String departmentName;
    private String position;
    private String status;

}
