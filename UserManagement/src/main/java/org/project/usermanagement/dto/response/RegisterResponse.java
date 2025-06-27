package org.project.usermanagement.dto.response;

import lombok.Data;

@Data
public class RegisterResponse {
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
}
