package cn.com.alasky.mapper.master.admin;

import cn.com.alasky.dao.AssSignUpDao;
import cn.com.alasky.vo.admin.AssSignUpAdminAssNameVo;
import cn.com.alasky.vo.admin.AssSignUpDetailVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Author: Alaskyed
 * Time: 4/7/2020 5:21 PM
 * Package: cn.com.alasky.mapper.master.association
 * Description:
 */
public interface AssSignUpAdminMapper {
    /**
     * 根据stuUuid查询出担任部长或会长的社团基本信息
     * @param userStuUuid
     * @return
     */
    @Select("select ai.ass_uuid, ai.association_name as ass_name, un.university_name as university_name \n" +
            "from stu_ass sa\n" +
            "inner join association_info ai\n" +
            "on sa.ass_uuid=ai.ass_uuid\n" +
            "inner join university un\n" +
            "on ai.university_code = un.id_code \n" +
            "where sa.stu_uuid = #{userStuUuid}")
    public List<AssSignUpAdminAssNameVo> queryAssNames(String userStuUuid);


    /**
     * 查询某个社团报名信息列表
     * @param assUuid
     * @return
     */
    @Select("select asu.id, asu.ass_sign_up_name, asu.ass_sign_up_gender, \n" +
            "d.department_name as ass_sign_up_dep_name, asu.ass_sign_up_introduce, \n" +
            "asu.ass_sign_up_reason, mi.major_name, \n" +
            "if(asu.status=0,\"未处理\",if(asu.status=1,\"已同意\",\"已拒绝\")) as status\n" +
            "from ass_sign_up asu \n" +
            "inner join departments d\n" +
            "on asu.ass_sign_up_dep_uuid = d.dep_uuid \n" +
            "inner join `user` u\n" +
            "on asu.user_uuid = u. user_uuid \n" +
            "inner join student_info si\n" +
            "on u.stu_uuid = si.stu_uuid \n" +
            "left join major_info mi\n" +
            "on si.major_uuid = mi.major_uuid \n" +
            "where asu.ass_uuid = #{assUuid}")
    List<AssSignUpDetailVo> queryAssSignUpList(String assUuid);


    /**
     * 查询某个报名表的详细信息
     * @param id
     * @return
     */
    @Select("select asu.ass_sign_up_name, asu.ass_sign_up_gender, \n" +
            "d.department_name as ass_sign_up_dep_name, asu.ass_sign_up_introduce, \n" +
            "asu.ass_sign_up_reason, \n" +
            "if(asu.status=0,\"未处理\",if(asu.status=1,\"已同意\",\"已拒绝\")) as status\n" +
            "from ass_sign_up asu \n" +
            "inner join departments d\n" +
            "on asu.ass_sign_up_dep_uuid = d.dep_uuid \n" +
            "where asu.id = #{id}")
    AssSignUpDetailVo queryAssSignUpDetail(long id);


    /**
     * 同意加入时, 先查询报名表的部分(assUuid, depUuid)
     *
     * @param id
     * @return
     */
    @Select("select ass_uuid, ass_sign_up_dep_uuid \n" +
            "from ass_sign_up asu \n" +
            "where id = #{id}")
    AssSignUpDao queryAssSignUpDaoById(long id);

    /**
     * 同意加入, 插入信息
     * @param userStuUuid
     * @param assUuid
     * @param assSignUpDepUuid
     * @return
     */
    @Insert("insert into stu_ass(stu_uuid, ass_uuid ,dep_uuid ,`position` ,status )\n" +
            "values(#{userStuUuid},#{assUuid}, #{assSignUpDepUuid}, 4, 0 )")
    int insertNewMember(String userStuUuid, String assUuid, String assSignUpDepUuid);


    /**
     * 更新报名表的状态(同意或拒绝)
     * @param id
     * @param i
     * @return
     */
    @Update("update ass_sign_up \n" +
            "set status = #{status} \n" +
            "where id = #{id} ")
    int updateStatusInAssSignUp(long id, int status);
}
