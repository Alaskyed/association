package cn.com.alasky.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: Alaskyed
 * Time: 3/5/2020 6:22 PM
 * Package: cn.com.alasky.dao
 * Description:
 */
@ToString
public class AssMemberDao {
    @Getter
    @Setter
    private String stuUuid;
    @Getter
    @Setter
    private String stuName;
    @Getter
    @Setter
    private String position;
}
