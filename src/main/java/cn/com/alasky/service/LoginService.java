package cn.com.alasky.service;

import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.mapper.master.LoginMapper;
import cn.com.alasky.domain.LoginBean;
import cn.com.alasky.pojo.UserSession;
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

    /**
     * 检查账号用户名和密码是否正确
     *
     * @param loginBean
     * @param session
     * @return
     */
    @Transactional
    public String checkLoginInfo(LoginBean loginBean, HttpSession session) {
        List<UserSession> resultList = loginMapper.queryUser(loginBean);
        if (resultList.size() < 1) {
            return ReturnValue.DATA_MATCHING_ERROR.value();
        } else {
            session.setAttribute("user", resultList.get(0));
            return ReturnValue.SUCCESS.value();
        }
    }
}
