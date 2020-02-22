package cn.com.alasky.service;

import cn.com.alasky.domain.UserBean;
import cn.com.alasky.mapper.RegisterMapper;
import cn.com.alasky.domain.RegisterBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * cn.com.alasky.service
 * 2020/1/29 下午8:08
 * author: Alasky
 * description:
 */

@Service
@Slf4j
public class RegisterService {
    @Autowired
    RegisterMapper registerMapper;

    /**
     * 添加新的用户
     *
     * @param registerBean
     * @return
     */
    @Transactional
    public String addUser(RegisterBean registerBean) {
        //使用userBean对象接收registerBean
        UserBean userBean = regiserToUser(registerBean);
        //添加uuid
        String stuUuis = UUID.randomUUID().toString();
        userBean.setStuUuid(stuUuis);
        //写入数据库
        try {
            //查询学校名称是否存在
            String universityCode = checkUniversityName(registerBean.getRegisterUniversity());
            if (universityCode != "noone") {
                //在user表中插入数据
                registerMapper.insertNewUser(userBean);
                //在student信息表中插入数据(uuid)
                registerMapper.insertUUIDIntoStuInfo(stuUuis);
                //在student信息表中插入学校标识码
                registerMapper.insertUniversityCodeIntoStudentInfo(universityCode, userBean.getPhoneNumber());

                return "success";
            }else{
                //返回noone
                return "noone";
            }
        } catch (DuplicateKeyException e) {
            //主键重复异常,说明该手机号已注册
            log.info("注册手机号重复");
            log.error(String.valueOf(e));
            return "当前手机号已注册!";
        } catch (Exception e) {
            //其他错误
            log.info("插入新用户出错");
            log.error(String.valueOf(e));
            return "注册失败";
        }
    }

    /**
     * 使用userBean接收registerBean
     *
     * @param registerBean
     * @return
     */
    public UserBean regiserToUser(RegisterBean registerBean) {
        UserBean userBean = new UserBean();

        userBean.setUserName(registerBean.getRegisterUserName());
        userBean.setPassword(registerBean.getRegisterPassword());
        userBean.setPhoneNumber(registerBean.getRegisterPhoneNumber());
        userBean.setEmail(registerBean.getRegisterEmail());
        userBean.setQq(registerBean.getRegisterQQ());
        userBean.setWechat(registerBean.getRegisterWechat());

        return userBean;
    }

    /**
     * 查询学校名称是否存在
     *
     * @return 如果存在, 返回学校的标识码
     * 如果不存在,返回 "noone"
     */
    @Transactional
    public String checkUniversityName(String universityName) {
        //查血数据库中的内容
        try {
            List<String> universityCodes = registerMapper.queryUniversityCodeByName(universityName);
            if (universityCodes.size() > 0) {
                //查询成功,返回学校代码
                return universityCodes.get(0);
            } else {
                //查询失败,返回 noone
                return "noone";
            }
        } catch (Exception e) {
            log.info("查询学校标识码出错!");
            log.error(String.valueOf(e));
            return "error";
        }
    }


}
