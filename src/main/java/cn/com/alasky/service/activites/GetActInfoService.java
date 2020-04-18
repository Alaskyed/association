package cn.com.alasky.service.activites;

import cn.com.alasky.mapper.master.activites.GetActInfoMapper;
import cn.com.alasky.vo.activities.ActInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/16/2020 10:27 AM
 * Package: cn.com.alasky.service.activites
 * Description:
 */
@Service
public class GetActInfoService {
    @Autowired
    private GetActInfoMapper getActInfoMapper;


    /**
     * 获取热门活动
     * @return
     */
    public List<ActInfoVo> getHighHotActInfos() {
        List<ActInfoVo> highHotActInfos = getActInfoMapper.getHighHotActInfo();
        return highHotActInfos;
    }


    /**
     * 获取本校的热门活动
     * @return
     */
    public List<ActInfoVo> getUniversityHighHotActInfos(String stuUuid) {
        List<ActInfoVo> highHotActInfos = getActInfoMapper.getUniversityHighHotActInfo(stuUuid);
        return highHotActInfos;
    }



    /**
     * 所有活动页面获取社团信息
     * 每次根据index返回20个
     * @return
     */
    public List<ActInfoVo> getActInfosByIndex(int index) {
        List<ActInfoVo> actInfoVos=getActInfoMapper.getLimitActs(index);
        return actInfoVos;
    }


    /**
     * 根据关键字查询活动
     * @param keySearch
     * @return
     */
    public List<ActInfoVo> actSearch(String keySearch) {
        List<ActInfoVo> actInfoVos=getActInfoMapper.getActSearchReault(keySearch);
        return actInfoVos;
    }



    /**
     * 获取某个社团的详细信息
     * @param actId
     * @return
     */
    public ActInfoVo getActDetail(int actId) {
        ActInfoVo actInfoVo=getActInfoMapper.queryActDetailByActId(actId);
        return actInfoVo;
    }

    /**
     * 浏览活动详细信息, 活动热门值+1
     * @param actId
     */
    @Transactional
    public void actHotIncreament(int actId) {
        getActInfoMapper.actHotIncreament(actId);

    }


}
