package cn.com.alasky.vo.association;

import lombok.Data;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/30/2020 8:00 PM
 * Package: cn.com.alasky.vo.ass
 * Description: 用户报名新社团时, 查看社团的详细信息
 */
@Data
public class AssDetailVo {
    private String assUuid;
    private String universityName;
    private String assName;
    private List<DepInfoVo> departments;
}
