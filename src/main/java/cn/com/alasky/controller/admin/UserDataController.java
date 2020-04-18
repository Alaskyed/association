package cn.com.alasky.controller.admin;

import cn.com.alasky.domain.DataChangeBean;
import cn.com.alasky.domain.UserBean;
import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.service.admin.UserDataService;
import cn.com.alasky.utils.RequestInfoUtils;
import cn.com.alasky.utils.UserSessionUtils;
import cn.com.alasky.pojo.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class UserDataController {
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private DataChangeBean dataChangeBean;

    @Autowired
    private HttpServletRequest request;


    /**
     * 查询用户信息,返回查询结果
     *
     * @return
     */
    @RequestMapping(value = "/getUserData")
    public UserBean getUserData() {
        //创建UserBean对象
        UserBean userBean = null;

        try {
            //检查用户是否登录
            UserSession user = UserSessionUtils.checkLogin(request.getSession());
            if (user == null) {
                return new UserBean();
            }

            //已登录, 记录登录信息
            log.info("查看用户信息: " + user.getUserUuid());
            //获取成功,继续执行下一步
            userBean = userDataService.getUserData(user.getUserPhoneNumber());
            return userBean;
        } catch (Exception e) {
            log.error("查看用户信息错误: " + String.valueOf(e));
            return new UserBean();
        }
    }


    /**
     * 修改用户信息
     *
     * @param dataType
     * @param newData
     * @param request
     * @return 结果代码:
     * 0 : 执行成功
     * -1 : session中没有用户信息
     * -2 : 执行失败
     * -3 : 请求名不匹配
     * -4 : 更新信息出错,但是数据库写入成功
     */
    @RequestMapping(value = "/userDataChange/{dataType}", method = RequestMethod.POST)
    public String userDataChange(@PathVariable String dataType, String newData, HttpServletRequest request) {
        //获取session中user信息,并判断用户是否存在
        UserSession user = UserSessionUtils.checkLogin(request.getSession());
        if (user == null) {
            return ReturnValue.USER_INFO_ERROR.value();
        }

        log.info("修改用户信息("+dataType+"): " + user.getUserUuid());
        //判断用户修改的信息类型
        if (dataType.equals("userName")) {
            //修改用户名
            dataChangeBean.setUserPhoneNumber(user.getUserPhoneNumber());
            dataChangeBean.setNewValue(newData);
            //执行service中的方法
            boolean result = userDataService.changeUserName(dataChangeBean);
            //返回执行结果
            if (result) {
                //更新session中的用户名
                boolean updateResult = UserSessionUtils.updateUserSesionUserName(dataChangeBean.getNewValue(), request.getSession());
                if (updateResult) {
                    return ReturnValue.SUCCESS.value();
                } else {
                    return "-4";
                }
            } else {
                return ReturnValue.EXECUTION_ERROR.value();
            }
        } else if (dataType.equals("phoneNumber")) {
            //修改手机号
            dataChangeBean.setUserPhoneNumber(user.getUserPhoneNumber());
            dataChangeBean.setNewValue(newData);
            //执行service中的方法
            boolean result = userDataService.changeUserPhoneNumber(dataChangeBean);
            //返回执行结果
            if (result) {
                //更新session中的手机号
                boolean updateResult = UserSessionUtils.updateUserSesionPhoneNumber(dataChangeBean.getNewValue(), request.getSession());
                if (updateResult) {
                    return ReturnValue.SUCCESS.value();
                } else {
                    return "-4";
                }
            } else {
                return ReturnValue.EXECUTION_ERROR.value();
            }
        } else if (dataType.equals("email")) {
            //修改邮箱
            //先获取session中的数据
            dataChangeBean.setUserPhoneNumber(user.getUserPhoneNumber());
            dataChangeBean.setNewValue(newData);
            //执行service中的方法
            boolean result = userDataService.changeUserEmail(dataChangeBean);
            //返回执行结果
            if (result) {
                return ReturnValue.SUCCESS.value();
            } else {
                return ReturnValue.EXECUTION_ERROR.value();
            }
        } else if (dataType.equals("qq")) {
            //修改QQ
            //先获取session中的数据
            dataChangeBean.setUserPhoneNumber(user.getUserPhoneNumber());
            dataChangeBean.setNewValue(newData);
            //执行service中的方法
            boolean result = userDataService.changeUserQq(dataChangeBean);
            //返回执行结果
            if (result) {
                return ReturnValue.SUCCESS.value();
            } else {
                return ReturnValue.EXECUTION_ERROR.value();
            }
        } else if (dataType.equals("wechat")) {
            //修改微信
            //先获取session中的数据
            dataChangeBean.setUserPhoneNumber(user.getUserPhoneNumber());
            dataChangeBean.setNewValue(newData);
            //执行service中的方法
            boolean result = userDataService.changeUserWechat(dataChangeBean);
            //返回执行结果
            if (result) {
                return ReturnValue.SUCCESS.value();
            } else {
                return ReturnValue.EXECUTION_ERROR.value();
            }
        } else {
            //没有匹配请求类型,返账错误值
            return "-3";
        }

    }

}
