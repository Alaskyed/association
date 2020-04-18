package cn.com.alasky.service.admin;

import cn.com.alasky.mapper.master.admin.ActSignUpAdminMapper;
import cn.com.alasky.vo.activities.ActAdminNameVo;
import cn.com.alasky.vo.activities.ActSignUpInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/29/2020 9:44 PM
 * Package: cn.com.alasky.service.admin
 * Description:
 */
@Service
public class ActSignUpAdminService {
    @Autowired
    private ActSignUpAdminMapper actSignUpAdminMapper;

    /**
     * 获取活动的基本信息(部长和会长)
     *
     * @param userStuUuid
     * @return
     */
    public List<ActAdminNameVo> getActSignUpAdminActNames(String userStuUuid) {
        List<ActAdminNameVo> actAdminNameVos = actSignUpAdminMapper.queryActNameByStuId(userStuUuid);
        return actAdminNameVos;

    }

    /**
     * 获取执行活动的报名人数
     *
     * @param actId
     * @return
     */
    public List<ActSignUpInfoVo> getActSignUpAdminSignUpInfos(int actId) {
        List<ActSignUpInfoVo> actSignUpInfoVos = actSignUpAdminMapper.queryActSignUpInfosByActId(actId);
        return actSignUpInfoVos;
    }
}
