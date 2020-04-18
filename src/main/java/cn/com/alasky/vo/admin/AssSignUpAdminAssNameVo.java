package cn.com.alasky.vo.admin;

import lombok.Data;

/**
 * Author: Alaskyed
 * Time: 4/7/2020 5:31 PM
 * Package: cn.com.alasky.vo.admin
 * Description: 控制台查看社团报名信息时返回的社团列表
 */
@Data
public class AssSignUpAdminAssNameVo {
    private String assUuid;
    private String assName;
    private String universityName;
}
