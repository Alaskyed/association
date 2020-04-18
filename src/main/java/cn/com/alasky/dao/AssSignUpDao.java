package cn.com.alasky.dao;

import lombok.Data;

/**
 * Author: Alaskyed
 * Time: 4/13/2020 9:21 PM
 * Package: cn.com.alasky.dao
 * Description:
 */
@Data
public class AssSignUpDao {
    private String id;
    private String assUuid;
    private String assSignUpName;
    private String assSignUpGender;
    private String assSignUpDepUuid;
    private String assSignUpIntroduce;
    private String assSignUpReason;
    private String status;
}
