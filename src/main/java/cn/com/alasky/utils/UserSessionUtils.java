package cn.com.alasky.utils;

import cn.com.alasky.pojo.UserSession;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;

/**
 * 用户工具路
 * 检查用户是否登录
 */
@Slf4j
public class UserSessionUtils {

    /**
     * 检查用户是否登录
     *
     * @param session
     * @return 登录的用户信息(手机号, 用户名), 如果没有用户信息就返回null
     */
    public static UserSession checkLogin(HttpSession session) {
        //获取session信息
        UserSession user = (UserSession) session.getAttribute("user");
        if (user != null) {
            //先更新一下session,主要是为了重置过期时间
            session.setAttribute("user", user);
            //返回用户的session值
            return user;
        } else {
            return null;
        }
    }

    /**
     * 更新手机号之后,在session中更新用户信息
     *
     * @param newPhoneNumber
     * @param session
     * @return
     */
    public static boolean updateUserSesionPhoneNumber(String newPhoneNumber, HttpSession session) {
        //先检查用户是否登录
        try {
            UserSession user = checkLogin(session);
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
     *
     * @param newUserName
     * @param session
     * @return
     */
    public static boolean updateUserSesionUserName(String newUserName, HttpSession session) {
        //先检查用户是否登录
        try {
            UserSession user = checkLogin(session);
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
