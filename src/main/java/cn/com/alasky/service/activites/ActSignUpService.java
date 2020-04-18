package cn.com.alasky.service.activites;

import cn.com.alasky.domain.ActSignUpBean;
import cn.com.alasky.mapper.master.activites.ActSignUpMapper;
import cn.com.alasky.returnandexception.ReturnValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/29/2020 7:28 PM
 * Package: cn.com.alasky.service.activites
 * Description:
 */
@Service
public class ActSignUpService {
    @Autowired
    private ActSignUpMapper actSignUpMapper;

    /**
     * 添加新的活动报名
     *
     * @return
     */
    @Transactional
    public String addNewActSignUp(String userUuid, ActSignUpBean actSignUpBean) {
        //检测是否已经报名
        List<String> signUp = actSignUpMapper.checkActSignUp(userUuid, actSignUpBean.getActId());
        if (signUp.size() > 0) {
            return ReturnValue.DATA_REPEAT_ERROR.value();
        }

        //添加报名信息
        int result = actSignUpMapper.InserNewActSignUp(actSignUpBean);
        if (result > 0) {
            //报名成功报名人数+1
            actSignUpMapper.signUpNumIncreaement(actSignUpBean.getActId());
            return ReturnValue.SUCCESS.value();
        } else {
            return ReturnValue.EXECUTION_ERROR.value();
        }

    }
}
