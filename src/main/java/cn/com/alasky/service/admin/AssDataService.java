package cn.com.alasky.service.admin;

import cn.com.alasky.dao.StuAssDao;
import cn.com.alasky.domain.DepartmentBean;
import cn.com.alasky.domain.NewAssBean;
import cn.com.alasky.mapper.master.admin.AssDataMapper;
import cn.com.alasky.returnandexception.ReturnValue;
import cn.com.alasky.vo.admin.AssDataVo;
import cn.com.alasky.pojo.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Author: Alaskyed
 * Time: 2/26/2020 10:56 AM
 * Package: cn.com.alasky.service.admin
 * Description:
 */
@Service
@Slf4j
public class AssDataService {
    @Autowired
    private AssDataMapper assDataMapper;

    /**
     * 获取用户加入的社团信息
     *
     * @return
     */
    @Transactional
    public List<AssDataVo> getAssData(String userStuUuid) {
        List<AssDataVo> assDataVos = assDataMapper.queryAssDataFromAssInfo(userStuUuid);
        return assDataVos;
    }

    /**
     * 添加新的社团
     *
     * @param newAssBean
     * @param user
     * @return
     */
    @Transactional
    public String createNewAss(NewAssBean newAssBean, UserSession user) {
        //首先检验学校名称是否正确
        List<String> universityCodes = assDataMapper.queryUniversityCodeByName(newAssBean.getUniversityName());
        if (universityCodes.size() == 0) {
            //没有查询到学校结果,返回错误信息
            return ReturnValue.DATA_MATCHING_ERROR.value();
        } else {
            //查询成功, 将学校代码添加到bean中
            newAssBean.setUniversityCode(universityCodes.get(0));
        }

        //检查该学校中是否存在该社团名称
        List<String> existAss = assDataMapper.queryAssNameFromAssociationInfo(newAssBean);
        if (existAss.size() > 0) {
            return ReturnValue.DATA_REPEAT_ERROR.value();
        }


        //1. 在社团信息表中添加信息
        String assUuid = UUID.randomUUID().toString();
        int result = assDataMapper.insertInfoIntoAssociationInfo(assUuid, newAssBean);
        if (result == 0) {
            return ReturnValue.EXECUTION_ERROR.value();
        }
        //2. 在部门表中添加信息
        List<DepartmentBean> departmentBeans = newAssBean.getDepartments();
        for (DepartmentBean departmentBean : departmentBeans) {
            departmentBean.setDepartmentUuid(UUID.randomUUID().toString());
            int result2 = assDataMapper.insertInfoIntoDepartments(assUuid, departmentBean);
            if (result == 0) {
                return ReturnValue.EXECUTION_ERROR.value();
            }
        }
        //添加会长团
        DepartmentBean adminDepartment = new DepartmentBean();
        adminDepartment.setDepartmentName("会长团");
        adminDepartment.setDepartmentUuid(UUID.randomUUID().toString());
        int result2 = assDataMapper.insertInfoIntoDepartments(assUuid, adminDepartment);
        if (result2 == 0) {
            return ReturnValue.EXECUTION_ERROR.value();
        }

        //3. 在stu_ass中添加信息
        StuAssDao stuAssDao = new StuAssDao();
        stuAssDao.setStuUuid(user.getUserStuUuid());
        stuAssDao.setAssUuid(assUuid);
        stuAssDao.setDepUuid(adminDepartment.getDepartmentUuid());
        stuAssDao.setPosition("1");
        stuAssDao.setStatus("0");

        int result3 = assDataMapper.insertInfoIntoStuAss(stuAssDao);
        if (result3 == 0) {
            return ReturnValue.EXECUTION_ERROR.value();
        } else {
            return ReturnValue.SUCCESS.value();
        }
    }


    /**
     * 退出社团
     *
     * @param userStuUuid
     * @param assUuid
     * @return
     */
    @Transactional
    public String quitAss(String userStuUuid, String assUuid) {
        //检查是否是会长或者部长
        int position = assDataMapper.queryPosition(userStuUuid, assUuid);
        if (position == 1) {
            //查询会长团的管理员还剩几人
            int memberNum = assDataMapper.queryNum1(assUuid);
            if (memberNum <= 1) {
                return ReturnValue.STAFF_NUMBER_ERROR.value();
            }
        } else if (position == 2) {
            //如果是部长, 查看所在部门还剩多少管理员
            int memberNum = assDataMapper.queryNum2(userStuUuid, assUuid);
            if (memberNum <= 1) {
                return ReturnValue.STAFF_NUMBER_ERROR.value();
            }
        }

        //没有问题, 执行退出社团逻辑
        int result = assDataMapper.quitAss(userStuUuid, assUuid);
        if (result > 0) {
            return ReturnValue.SUCCESS.value();
        } else {
            return ReturnValue.EXECUTION_ERROR.value();
        }

    }
}
