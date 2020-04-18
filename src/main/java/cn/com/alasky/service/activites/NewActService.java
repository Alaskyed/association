package cn.com.alasky.service.activites;

import cn.com.alasky.domain.NewActBean;
import cn.com.alasky.mapper.master.activites.NewActMapper;
import cn.com.alasky.vo.activities.AssNameVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 3/15/2020 10:17 AM
 * Package: cn.com.alasky.service.activites
 * Description:
 */
@Service
public class NewActService {
    @Autowired
    private NewActMapper newActMapper;

    /**
     * 获取当会长的社团名
     *
     * @param userStuUuid
     * @return
     */
    public List<AssNameVo> getAdminAssName(String userStuUuid) {
        return newActMapper.queryAssNamesByAdminStuUuid(userStuUuid);
    }

    /**
     * 添加新的社团活动
     *
     * @param newAct
     * @return 返回新活动的id
     */
    public int addNewAct(NewActBean newAct) {
        newActMapper.insertNewActInfo(newAct);
        if (newAct.getNewActId()> 1) {
            //返回id值
            return newAct.getNewActId();
        }else {
            return -1;
        }
    }

    /**
     * 更新插入的社团宣传图片名称
     * @param newActId
     * @param newActPicName
     */
    public void updatePicName(String newActId, String newActPicName) {
        newActMapper.updatePIcName(newActId,newActPicName);
    }

    /**
     * 插入活动附件
     * @param newActId
     * @param newActEnclosureName
     */
    public void updateEnclosureName(int newActId, String newActEnclosureName) {
        newActMapper.updateEnclosureName(newActId, newActEnclosureName);

    }
}
