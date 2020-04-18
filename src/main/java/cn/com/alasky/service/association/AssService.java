package cn.com.alasky.service.association;

import cn.com.alasky.mapper.master.association.AssMapper;
import cn.com.alasky.vo.association.AssInfoVo;
import cn.com.alasky.vo.association.DepInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/30/2020 4:42 PM
 * Package: cn.com.alasky.service.ass
 * Description:
 */
@Service
public class AssService {
    @Autowired
    private AssMapper assMapper;

    /**
     * 根据学生信息获取学校code
     *
     * @param stuUuid
     * @return
     */
    public String getUniversityCodeByStuUuid(String stuUuid) {
        String universityCode = assMapper.queryUniversityCodeByStuUuid(stuUuid);
        return universityCode;
    }

    /**
     * 根据学校代码查询该学校的所有社团
     *
     * @param universityCode
     * @return
     */
    public List<AssInfoVo> getUniversityAsses(String universityCode) {
        List<AssInfoVo> assInfoVos = assMapper.queryUniversityAsses(universityCode);
        return assInfoVos;
    }

    /**
     * 根据assUuid获取社团的详细信息
     *
     * @param assUuid
     * @return
     */
    public AssInfoVo getAssDetail(String assUuid) {
        //获取社团基本信息
        AssInfoVo assInfoVo = assMapper.queryAssInfoByAssUuid(assUuid);
        //获取部门基本信息
        List<DepInfoVo> depInfoVos=assMapper.queryDepsInfoByAssUuid(assUuid);
        //将部门列表添加到社团信息中
        assInfoVo.setDepsInfo(depInfoVos);

        return assInfoVo;
    }
}
