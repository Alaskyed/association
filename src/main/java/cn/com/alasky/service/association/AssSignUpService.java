package cn.com.alasky.service.association;

import cn.com.alasky.domain.AssSignUpBean;
import cn.com.alasky.mapper.master.association.AssSignUpMapper;
import cn.com.alasky.returnandexception.ReturnValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 4/7/2020 11:34 AM
 * Package: cn.com.alasky.service.association
 * Description:
 */
@Service
public class AssSignUpService {
    @Autowired
    private AssSignUpMapper assSignUpMapper;

    /**
     * 添加新的报名信息
     *
     * @param assSignUpBean
     * @param userStuUuid
     * @return
     */
    @Transactional
    public String addSignUpInfo(AssSignUpBean assSignUpBean, String userStuUuid) {
        //先检查自己是否已经是这个社团的人员
        List<String> members = assSignUpMapper.checkIsThisAssMember(userStuUuid, assSignUpBean.getAssUuid());
        if (members.size() > 0) {
            //说明已经是当前社团成员, 无需提交报名表
            return ReturnValue.STAFF_NUMBER_ERROR.value();
        }

        //报名重复检测, 检测是否已经报名, 如果重复报名,就覆盖
        List<Long> signUpId = assSignUpMapper.checkIsAlreadySignUp(assSignUpBean);
        if (signUpId.size() > 0) {
            //删除之前的报名表,然后再执行添加逻辑
            assSignUpMapper.delSignUp(signUpId.get(0));
        }

        //执行添加逻辑
        int result = assSignUpMapper.insertSignUpInfoIntoAssSignUp(assSignUpBean);
        if (result > 0) {
            return ReturnValue.SUCCESS.value();
        } else {
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }
}
