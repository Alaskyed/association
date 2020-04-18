package cn.com.alasky.domain;

import lombok.Data;

/**
 * Author: Alaskyed
 * Time: 3/28/2020 5:55 PM
 * Package: cn.com.alasky.domain
 * Description:
 */
@Data
public class MessageBean {
    private Long msgId;
    private String msgSenderUuid;
    private String msgRecipientUuid;
    private String msgContent;
    private String msgSendTime;
    private int msgIsRead;
}
