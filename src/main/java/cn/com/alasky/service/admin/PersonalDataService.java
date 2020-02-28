package cn.com.alasky.service.admin;

import cn.com.alasky.domain.DataChangeBean;
import cn.com.alasky.domain.MajorBean;
import cn.com.alasky.mapper.admin.PersonalDataMapper;
import cn.com.alasky.vo.PerosnalDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PersonalDataService {
    @Autowired
    private PersonalDataMapper personalDataMapper;

    /**
     * 查询个人资料
     *
     * @param userPhoneNumber
     * @return
     */
    @Transactional
    public PerosnalDataVo getPersonalData(String userPhoneNumber) {
        //查询个人信息
        List<PerosnalDataVo> perosnalDataVos = personalDataMapper.queryPersonalData(userPhoneNumber);
        if (perosnalDataVos.size() > 0) {
            return perosnalDataVos.get(0);
        } else {
            return new PerosnalDataVo();
        }
    }

    /**
     * 修改学校名称
     *
     * @param dataChangeBean
     * @return 0 : 成功
     * -5 : 查不到学校名
     */
    @Transactional
    public String changePersonalUniversity(DataChangeBean dataChangeBean) {
        //首先判断学校名是否正确
        List<String> universityCodes = personalDataMapper.queryUniversityCodeByName(dataChangeBean.getNewValue());
        if (universityCodes.size() > 0) {
            //学校名正确,将学校名称换成学校标识符,修改学生信息表中的学校标识符
            dataChangeBean.setNewValue(universityCodes.get(0));
            int result = personalDataMapper.updateUniversityInStudentInfo(dataChangeBean);
            if (result > 0) {
                //返回执行成功字符
                return "0";
            } else {
                //返回执行失败字符
                return "-2";
            }
        } else {
            //查询失败,返回查询失败代码
            return "-5";
        }

    }

    /**
     * 修改专业名称
     *
     * @param dataChangeBean
     * @return
     */
    @Transactional
    public String changePersonalMajor(DataChangeBean dataChangeBean) {
        //获取新的专业名
        String newMajorName = dataChangeBean.getNewValue();
        //检查数据库中是否存在相同的学校专业
        //1. 把其学校和专业读出来
        List<String> universityCode = personalDataMapper.queryUniversityCodeByPhoneNumber(dataChangeBean.getUserPhoneNumber());
        //2. 查询
        List<String> majorUuids = personalDataMapper.queryMajorUUIDInMajorInfo(newMajorName, universityCode.get(0));
        if (majorUuids.size() > 0) {
            //如果存在,直接更换专业uuid
            dataChangeBean.setNewValue(majorUuids.get(0));
            int result = personalDataMapper.updateMajorUuidInStudentInfo(dataChangeBean);

            //返账执行成功代码
            return "0";
        } else {
            //如果不存在,新建专业,并更换新的专业uuid
            //1. 创建一个major对象
            MajorBean majorBean = new MajorBean();
            //2. 添加新的值
            String majorUuid = UUID.randomUUID().toString();
            majorBean.setMajorUuid(majorUuid);
            majorBean.setUniversityCode(universityCode.get(0));
            majorBean.setMajorName(newMajorName);
            //3. 更新到数据库中
            int result = personalDataMapper.insertNewMajorIntoMajorInfo(majorBean);
            if (result > 0) {
                //4. 更新学生的major uuid
                dataChangeBean.setNewValue(majorUuid);
                int result2 = personalDataMapper.updateMajorUuidInStudentInfo(dataChangeBean);
                if (result2 > 0) {
                    return "0";
                } else {
                    return "-2";
                }
            } else {
                return "-2";
            }
        }

    }

    /**
     * 修改姓名
     *
     * @param dataChangeBean
     * @return
     */
    @Transactional
    public String changePersonalName(DataChangeBean dataChangeBean) {
        //直接修改姓名即可
        int result = personalDataMapper.updateNameInStudentInfo(dataChangeBean);
        if (result > 0) {
            //返回执行成功指令
            return "0";
        } else {
            return "-2";
        }
    }

    /**
     * 修改学号
     *
     * @param dataChangeBean
     * @return
     */
    @Transactional
    public String changePersonalStuId(DataChangeBean dataChangeBean) {
        //修改学号
        int result = personalDataMapper.updateStuIdInStudentInfo(dataChangeBean);
        if (result > 0) {
            return "0";
        } else {
            return "-2";
        }

    }

    /**
     * 修改年级
     *
     * @param dataChangeBean
     * @return
     */
    @Transactional
    public String changePersonalGrade(DataChangeBean dataChangeBean) {
        //修改年级
        int result = personalDataMapper.updateGradeInStudentInfo(dataChangeBean);
        if (result > 0) {
            return "0";
        } else {
            return "-2";
        }

    }

    /**
     * 获取用户加入的社团名称
     * @return
     * @param userPhoneNumber
     */
    public List<String> getAssNames(String userPhoneNumber) {
        List<String> assNames = personalDataMapper.queryAssNames(userPhoneNumber);
        return assNames;
    }


    /**
     * 修改学院
     * @param dataChangeBean
     * @return
     */
//    public String changePersonalAcademic(DataChangeBean dataChangeBean) {
//        try{
//            int result = personalDataMapper.updateAcademyInStudentInfo(dataChangeBean);
//
//        }
//    }


}
