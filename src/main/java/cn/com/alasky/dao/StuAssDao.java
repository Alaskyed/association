package cn.com.alasky.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: Alaskyed
 * Time: 2/28/2020 5:54 PM
 * Package: cn.com.alasky.dao
 * Description:
 */
@ToString
public class StuAssDao {
    @Getter
    @Setter
    private String stuUuid;
    @Getter
    @Setter
    private String assUuid;
    @Getter
    @Setter
    private String depUuid;
    @Getter
    @Setter
    private String position;
    @Getter
    @Setter
    private String status;

}
