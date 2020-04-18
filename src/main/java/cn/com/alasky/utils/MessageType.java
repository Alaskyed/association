package cn.com.alasky.utils;

/**
 * Author: Alaskyed
 * Time: 3/30/2020 11:38 AM
 * Package: cn.com.alasky.utils
 * Description: 消息的类型
 */
public enum MessageType {
    SYSTEMMESSAGE(1),
    NEWMEMBERMESSAGE(2),
    ACTSIGNUPMESSAGE(3),
    REPLY(4);


    private final int value;

    // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
    MessageType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
