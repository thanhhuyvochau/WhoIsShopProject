package org.project.emailservice.user.dto;

import lombok.Data;
import org.project.emailservice.user.constant.EmailType;

@Data
public class EmailInfo {
    private String recipient;
    private String subject;
    private String attachment;
    private EmailType type;
    private Object data;
}
