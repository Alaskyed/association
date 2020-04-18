package cn.com.alasky.service.admin;

import cn.com.alasky.domain.DataChangeBean;
import cn.com.alasky.domain.UserBean;
import cn.com.alasky.mapper.master.admin.UserDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserDataService {
    @Autowired
    private UserDataMapper userDataMapper;

    /**
     * 查询个人资料
     *
     * @param userPhoneNumber
     * @return
     */
    @Transactional
    public UserBean getUserData(String userPhoneNumber) {
        //查询基本的用户资料(不包括密码和stu_uuid)
        List<UserBean> userBeans = userDataMapper.queryUserData(userPhoneNumber);
        if (userBeans.size() > 0) {
            return userBeans.get(0);
        } else {
            return new UserBean();
        }

    }

    /**
     * 修改用户名
     *
     * @param dataChangeBean
     * @return
     */
    @Transactional
    public boolean changeUserName(DataChangeBean dataChangeBean) {
        try {
            int result = userDataMapper.updateUserName(dataChangeBean);
            return true;
        } catch (Exception e) {
            log.error("修改用户名出错: " + String.valueOf(e));
            return false;
        }
    }

    /**
     * 修改手机号
     *
     * @param dataChangeBean
     * @return
     */
    @Transactional
    public boolean changeUserPhoneNumber(DataChangeBean dataChangeBean) {
        try {
            int result = userDataMapper.updatePhoneNumber(dataChangeBean);
            return true;
        } catch (Exception e) {
            log.error("修改手机号出错: " + String.valueOf(e));
            return false;
        }
    }

    /**
     * 修改邮箱
     *
     * @param dataChangeBean
     * @return
     */
    @Transactional
    public boolean changeUserEmail(DataChangeBean dataChangeBean) {
        try {
            int result = userDataMapper.updateEmail(dataChangeBean);
            return true;
        } catch (Exception e) {
            log.error("修改邮箱出错: " + String.valueOf(e));
            return false;
        }
    }

    /**
     * 修改QQ
     *
     * @param dataChangeBean
     * @return
     */
    @Transactional
    public boolean changeUserQq(DataChangeBean dataChangeBean) {
        try {
            int result = userDataMapper.updateQq(dataChangeBean);
            return true;
        } catch (Exception e) {
            log.error("修改QQ出错: " + String.valueOf(e));
            return false;
        }
    }

    /**
     * 修改微信
     *
     * @param dataChangeBean
     * @return
     */
    @Transactional
    public boolean changeUserWechat(DataChangeBean dataChangeBean) {
        try {
            int result = userDataMapper.updateWechat(dataChangeBean);
            return true;
        } catch (Exception e) {
            log.error("修改微信出错: " + String.valueOf(e));
            return false;
        }
    }
}
