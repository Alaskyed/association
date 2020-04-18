package cn.com.alasky.service;

import cn.com.alasky.domain.UserBean;
import cn.com.alasky.mapper.master.RegisterMapper;
import cn.com.alasky.domain.RegisterBean;
import cn.com.alasky.returnandexception.ReturnValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param userUuid
     * @param registerBean
     * @return
     */
    @Transactional
    public String addUser(String userUuid, RegisterBean registerBean) {
        //使用userBean对象接收registerBean
        UserBean userBean = regiserToUser(registerBean);
        //添加stu_uuid
        String stuUuid = UUID.randomUUID().toString();
        userBean.setStuUuid(stuUuid);
        //写入数据库
        //1. 查询学校名称是否存在
        String universityCode = checkUniversityName(registerBean.getRegisterUniversity());
        //学校名存在
        if (!(universityCode.equals(ReturnValue.DATA_REPEAT_ERROR.value()) || universityCode.equals(ReturnValue.EXECUTION_ERROR.value()))) {
            //1. 在user表中插入数据
            registerMapper.insertNewUser(userUuid, userBean);
            //2. 在student信息表中插入数据(uuid)
            registerMapper.insertUUIDIntoStuInfo(stuUuid);
            //3. 在student信息表中插入学校标识码
            registerMapper.updateUniversityCodeIntoStudentInfo(universityCode, userBean.getPhoneNumber());

            return ReturnValue.SUCCESS.value();
        } else {
            return ReturnValue.DATA_REPEAT_ERROR.value();
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
     */
    public String checkUniversityName(String universityName) {
        //查血数据库中的内容
        try {
            List<String> universityCodes = registerMapper.queryUniversityCodeByName(universityName);
            if (universityCodes.size() > 0) {
                //查询成功,返回学校代码
                return universityCodes.get(0);
            } else {
                //查询失败,返回错误代码
                return ReturnValue.DATA_REPEAT_ERROR.value();
            }
        } catch (Exception e) {
            log.info("查询学校标识码出错: " + String.valueOf(e));
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }


}
