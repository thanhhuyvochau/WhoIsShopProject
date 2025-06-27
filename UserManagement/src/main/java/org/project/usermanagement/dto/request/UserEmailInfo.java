package org.project.usermanagement.dto.request;

import lombok.Data;

@Data
public class UserEmailInfo {
    private int id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String verifyURL;
}
