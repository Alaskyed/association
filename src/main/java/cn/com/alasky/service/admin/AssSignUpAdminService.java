package cn.com.alasky.service.admin;

import cn.com.alasky.dao.AssSignUpDao;
import cn.com.alasky.mapper.master.admin.AssSignUpAdminMapper;
import cn.com.alasky.pojo.UserSession;
import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.vo.admin.AssSignUpAdminAssNameVo;
import cn.com.alasky.vo.admin.AssSignUpDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 4/7/2020 5:07 PM
 * Package: cn.com.alasky.service.admin
 * Description:
 */
@Service
public class AssSignUpAdminService {
    @Autowired
    private AssSignUpAdminMapper assSignUpAdminMapper;

    /**
     * 获取任职部长会长的社团基本信息
     *
     * @param userStuUuid
     * @return
     */
    public List<AssSignUpAdminAssNameVo> getAssSignUpAdminAssNames(String userStuUuid) {
        List<AssSignUpAdminAssNameVo> assSignUpAdminAssNameVos = assSignUpAdminMapper.queryAssNames(userStuUuid);

        return assSignUpAdminAssNameVos;
    }


    /**
     * 查询社团的报名信息列表
     *
     * @param assUuid
     * @return
     */
    public List<AssSignUpDetailVo> getAssSignUpInfoLIst(String assUuid) {
        List<AssSignUpDetailVo> assSignUpDetailVos = assSignUpAdminMapper.queryAssSignUpList(assUuid);
        return assSignUpDetailVos;
    }

    /**
     * 获取某个社团报名信息详情
     *
     * @param id
     * @return
     */
    public AssSignUpDetailVo getAssSignUpDetail(long id) {
        //获取信息
        AssSignUpDetailVo assSignUpDetailVo = assSignUpAdminMapper.queryAssSignUpDetail(id);
        //将添加该报名id
        assSignUpDetailVo.setId(id);
        return assSignUpDetailVo;
    }

    /**
     * 同意加入社团
     *
     * @param user
     * @param id
     * @return
     */
    @Transactional
    public String agree(UserSession user, long id) {
        //先获取报名表中的信息
        AssSignUpDao assSignUpDao = assSignUpAdminMapper.queryAssSignUpDaoById(id);

        //在stu_ass表中加入此人
        int result = assSignUpAdminMapper.insertNewMember(user.getUserStuUuid(), assSignUpDao.getAssUuid(), assSignUpDao.getAssSignUpDepUuid());
        if (result == 0) {
            return ReturnValue.EXECUTION_ERROR.value();
        }

        //更新报名表的状态
        result = assSignUpAdminMapper.updateStatusInAssSignUp(id, 1);
        if (result > 0) {
            return ReturnValue.SUCCESS.value();
        } else {
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }

    /**
     * 拒绝加入社团
     *
     * @param user
     * @param id
     * @return
     */
    @Transactional
    public String refuse(UserSession user, long id) {
        //更新报名报状态为拒绝(2)
        int result = assSignUpAdminMapper.updateStatusInAssSignUp(id, 2);
        if (result > 0) {
            return ReturnValue.SUCCESS.value();
        } else {
            return ReturnValue.EXECUTION_ERROR.value();
        }
    }
}
