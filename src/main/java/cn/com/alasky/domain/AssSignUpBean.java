package cn.com.alasky.domain;

import lombok.Data;

/**
 * Author: Alaskyed
 * Time: 4/7/2020 11:31 AM
 * Package: cn.com.alasky.domain
 * Description: 报名社团的Bean
 */
@Data
public class AssSignUpBean {
    private String userUuid;
    private String assUuid;
    private String assSignUpName;
    private String assSignUpGender;
    private String assSignUpDepUuid;
    private String assSignUpIntroduce;
    private String assSignUpReason;
}
