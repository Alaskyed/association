package cn.com.alasky.vo.association;

import lombok.Data;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/30/2020 4:55 PM
 * Package: cn.com.alasky.vo.ass
 * Description: 查看所有社团信息(报名)时
 */
@Data
public class AssInfoVo {
    private String assUuid;
    private String universityName;
    private String assName;
    private String customUrl;

    private List<DepInfoVo> depsInfo;
}
