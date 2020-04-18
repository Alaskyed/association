package cn.com.alasky.vo.activities;

import lombok.Data;

/**
 * Author: Alaskyed
 * Time: 3/16/2020 10:22 AM
 * Package: cn.com.alasky.vo
 * Description:
 */
@Data
public class ActInfoVo {
    private int actId;
    private String assUuid;
    private String assName;
    private String universityName;
    private String actName;
    private String actContent;
    private String contactInfo;
    private String startTime;
    private String endTime;
    private String releaseTime;
    private String deadlineTime;
    private String address;
    private String remarks;
    private String isOpen;
    private String actEnclosureName;
    private String stuUuid;
    private String actPicName;
    private String hot;
    private String signUpNum;
}
