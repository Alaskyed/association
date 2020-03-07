package cn.com.alasky.service.admin;

import cn.com.alasky.dao.StuAssDao;
import cn.com.alasky.domain.DepartmentBean;
import cn.com.alasky.domain.NewAssBean;
import cn.com.alasky.mapper.admin.AssDataMapper;
import cn.com.alasky.vo.AssDataVo;
import cn.com.alasky.vo.LoginSessionVo;
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
     * @param userPhoneNumber
     * @return
     */
    @Transactional
    public List<AssDataVo> getAssData(String userPhoneNumber) {
        List<AssDataVo> assDataVos = assDataMapper.queryAssDataFromAssInfo(userPhoneNumber);
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
    public String createNewAss(NewAssBean newAssBean, LoginSessionVo user) {
        //首先检验学校名称是否正确
        List<String> universityCodes = assDataMapper.queryUniversityCodeByName(newAssBean.getUniversityName());
        if (universityCodes.size() == 0) {
            //没有查询到学校结果,返回错误信息
            return "-3";
        }

        //检查该学校中是否存在该社团名称
        List<String> existAss = assDataMapper.queryAssNameFromAssociationInfo(newAssBean);
        if (existAss.size() > 0) {
            return "-4";
        }


        //1. 在社团信息表中添加信息
        String assUuid = UUID.randomUUID().toString();
        int result = assDataMapper.insertInfoIntoAssociationInfo(assUuid, newAssBean);
        if (result == 0) {
            return "-2";
        }
        //2. 在部门表中添加信息
        List<DepartmentBean> departmentBeans = newAssBean.getDepartments();
        for (DepartmentBean departmentBean : departmentBeans) {
            departmentBean.setDepartmentUuid(UUID.randomUUID().toString());
            int result2 = assDataMapper.insertInfoIntoDepartments(assUuid, departmentBean);
            if (result == 0) {
                return "-2";
            }
        }
        //添加会长团
        DepartmentBean adminDepartment = new DepartmentBean();
        adminDepartment.setDepartmentName("会长团");
        adminDepartment.setDepartmentUuid(UUID.randomUUID().toString());
        int result2 = assDataMapper.insertInfoIntoDepartments(assUuid, adminDepartment);
        if (result2 == 0) {
            return "-2";
        }

        //3. 在stu_ass中添加信息
        List<String> stuUuids = assDataMapper.queryStuUuidByPhoneNumber(user.getUserPhoneNumber());
        StuAssDao stuAssDao = new StuAssDao();
        stuAssDao.setStuUuid(stuUuids.get(0));
        stuAssDao.setAssUuid(assUuid);
        stuAssDao.setDepUuid(adminDepartment.getDepartmentUuid());
        stuAssDao.setPosition("1");
        stuAssDao.setStatus("0");

        int result3 = assDataMapper.insertInfoIntoStuAss(stuAssDao);
        if (result3 == 0) {
            return "-2";
        } else {
            return "0";
        }
    }
}
