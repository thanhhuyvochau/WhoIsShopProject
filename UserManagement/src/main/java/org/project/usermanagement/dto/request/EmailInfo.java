package org.project.usermanagement.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailInfo {
    private String recipient;
    private String subject;
    private String attachment;
    private String type;
    private Object data;
}
