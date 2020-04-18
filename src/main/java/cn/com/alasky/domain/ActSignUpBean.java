package cn.com.alasky.domain;

import lombok.Data;

/**
 * Author: Alaskyed
 * Time: 3/29/2020 7:50 PM
 * Package: cn.com.alasky.domain
 * Description:
 */
@Data
public class ActSignUpBean {
    private long id;
    private String userUuid;
    private String actSignUpName;
    private int actId;
    private String actSignUpRemarks;
    private String actSignUpTime;
}
