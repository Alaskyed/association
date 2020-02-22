package cn.com.alasky.utils;

import cn.com.alasky.vo.LoginSessionVo;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户工具路
 * 检查用户是否登录
 */
@Slf4j
public class UserUtils {

    /**
     * 检查用户是否登录
     *
     * @param session
     * @return
     */
    public static LoginSessionVo checkLogin(HttpSession session) {
        //获取session信息
        LoginSessionVo user = (LoginSessionVo) session.getAttribute("user");
        if (user != null) {
            LoginSessionVo loginSessionVo = new LoginSessionVo();
            loginSessionVo.setUserName(user.getUserName());
            loginSessionVo.setUserPhoneNumber(user.getUserPhoneNumber());
            return loginSessionVo;
        } else {
            return null;
        }
    }

    /**
     * 更新手机号之后,在session中更新用户信息
     * @param newPhoneNumber
     * @param session
     * @return
     */
    public static boolean updateUserSesionPhoneNumber(String newPhoneNumber, HttpSession session) {
        //先检查用户是否登录
        try {
            LoginSessionVo user = checkLogin(session);
            if (user != null) {
                user.setUserPhoneNumber(newPhoneNumber);
                session.setAttribute("user", user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("更新用户session信息出错!");
            log.error(String.valueOf(e));
            return false;
        }
    }

    /**
     * 更新用户名成功之后,更新session中的用户名
     * @param newUserName
     * @param session
     * @return
     */
    public static boolean updateUserSesionUserName(String newUserName, HttpSession session) {
        //先检查用户是否登录
        try {
            LoginSessionVo user = checkLogin(session);
            if (user != null) {
                user.setUserName(newUserName);
                session.setAttribute("user", user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("更新用户session信息出错!");
            log.error(String.valueOf(e));
            return false;
        }
    }
}
