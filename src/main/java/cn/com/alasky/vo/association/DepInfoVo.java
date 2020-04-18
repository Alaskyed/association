package cn.com.alasky.vo.association;

import lombok.Data;

/**
 * Author: Alaskyed
 * Time: 3/30/2020 8:07 PM
 * Package: cn.com.alasky.vo.ass
 * Description: 用户报名新社团时查看社团详细信息的部门信息
 */
@Data
public class DepInfoVo {
    private String depUuid;
    private String departmentDescription;
    private String departmentName;
}
