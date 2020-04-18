package cn.com.alasky.service.admin;

import cn.com.alasky.mapper.master.admin.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 2/28/2020 8:18 PM
 * Package: cn.com.alasky.service.admin
 * Description:
 */
@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 获取用户的职位
     * @return
     */
    @Transactional
    public String getUserPosition(String userStuUuid) {
        List<String> positions = adminMapper.queryPositionFromStuAss(userStuUuid);
        String position="";
        for (String po : positions) {
            position+=po;
        }

        return position;
    }
}
