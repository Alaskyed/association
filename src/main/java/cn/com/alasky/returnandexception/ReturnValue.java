package cn.com.alasky.returnandexception;

/**
 * Author: Alaskyed
 * Time: 3/22/2020 6:24 PM
 * Package: cn.com.alasky
 * Description:
 */
public enum ReturnValue {
    //成功
    SUCCESS("0"),
    //用户信息错误, 例如用户没有登录
    USER_INFO_ERROR("-1"),
    //执行错误, 例如数据库写入错误, 服务端逻辑错误
    EXECUTION_ERROR("-2"),
    //用户权限错误, 例如部长干了会长的事
    USER_PERMISSION_ERROR("-3"),
    //数据匹配错误, 例如大学名称错误
    DATA_MATCHING_ERROR("-4"),
    //人员数量错误, 例如删除人员时, 该部门只剩下一个部长
    STAFF_NUMBER_ERROR("-5"),
    //数据重复错误
    DATA_REPEAT_ERROR("-6");

    private final String value;

    // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
    ReturnValue(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
