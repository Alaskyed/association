package cn.com.alasky.service;

import cn.com.alasky.mapper.RegisterMapper;
import cn.com.alasky.domain.RegisterBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    RegisterMapper loginMapper;

    /**
     * 添加新的用户
     * @param registerBean
     * @return
     */
    @Transactional
    public String addUser(RegisterBean registerBean) {
        try {
            loginMapper.insertNewUser(registerBean);
            return "success";
        }catch (DuplicateKeyException e) {
            //主键重复异常,说明该手机号已注册
            return "当前手机号已注册!";
        }catch (Exception e) {
            //其他错误
            log.info("插入新用户出错");
            log.error(String.valueOf(e));
            return "注册失败";
        }
    }

}
