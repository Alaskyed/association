package cn.com.alasky.vo.admin;

import lombok.Data;

/**
 * Author: Alaskyed
 * Time: 4/7/2020 6:12 PM
 * Package: cn.com.alasky.vo.admin
 * Description: 获取某个社团报名信息时返回的bean, 获取某个人的信息报名信息时返回的bean
 */

@Data
public class AssSignUpDetailVo {
    private long id;
    private String userUuid;
    private String assSignUpName;
    private String assSignUpGender;
    private String assSignUpDepName;
    private String assSignUpIntroduce;
    private String assSignUpReason;
    private String majorName;
    private String status;
}
