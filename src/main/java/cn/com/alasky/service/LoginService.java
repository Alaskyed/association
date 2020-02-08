package cn.com.alasky.service;

import cn.com.alasky.domain.UserBean;
import cn.com.alasky.mapper.LoginMapper;
import cn.com.alasky.domain.LoginBean;
import cn.com.alasky.vo.LoginSessionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * cn.com.alasky.service
 * 2020/2/1 下午12:15
 * author: Alasky
 * description: 登录Services
 */

@Service
@Slf4j
public class LoginService {
    @Autowired
    LoginMapper loginMapper;
    @Autowired
    LoginSessionVo loginSessionBean;

    /**
     * 检查账号用户名和密码是否正确
     *
     * @param loginBean
     * @param session
     * @return
     */
    @Transactional
    public boolean checkUser(LoginBean loginBean, HttpSession session) {
        try {
            List<UserBean> resultList = loginMapper.queryUser(loginBean);
            if (resultList.size() < 1) {
                return false;
            } else {
                loginSessionBean.setUserPhoneNumber(resultList.get(0).getPhoneNumber());
                loginSessionBean.setUserName(resultList.get(0).getUserName());
                session.setAttribute("user",loginSessionBean);
                return true;
            }
        } catch (Exception e) {
            log.info("查询用户出错");
            log.error(String.valueOf(e));
            return false;
        }
    }
}
