package cn.com.alasky.vo.admin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: Alaskyed
 * Time: 2/19/2020
 * Package: cn.com.alasky.vo
 * Description: 个人资料的vo类
 */

@ToString
public class PerosnalDataVo {
    @Getter
    @Setter
    private String university;
    @Getter
    @Setter
    private String academy;
    @Getter
    @Setter
    private String major;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String stuId;
    @Getter
    @Setter
    private String grade;


}
