package cn.com.alasky.mapper.second;

import org.apache.ibatis.annotations.Insert;

/**
 * Author: Alaskyed
 * Time: 3/23/2020 12:09 PM
 * Package: cn.com.alasky.mapper.second
 * Description:
 */
public interface LoggerMapper {

    /**
     * 写入操作日志
     * @param infoType
     * @param e
     */
    @Insert("")
    void insertInfo(String infoType, Exception e);
}
