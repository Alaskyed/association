package cn.com.alasky.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author: Alaskyed
 * Time: 3/15/2020 9:50 AM
 * Package: cn.com.alasky.domain
 * Description:
 */
@Data
public class NewActBean {
    private int newActId;
    private String newActAssUuid;
    private String newActName;
    private String newActContent;
    private String newActContact;
    private String newActStart;
    private String newActEnd;
    private String newActDeadLine;
    private String newActAddress;
    private String newActRemarks;
    private String newActIsOpen;
    private String newActEnclosureName;
    private String newActStuUuid;
    private String newActPicName;

}
