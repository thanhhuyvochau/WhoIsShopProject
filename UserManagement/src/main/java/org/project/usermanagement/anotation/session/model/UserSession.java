package org.project.usermanagement.anotation.session.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.project.usermanagement.constant.Role;

@Data
@AllArgsConstructor
public class UserSession {
    private int id;
    private String email;
    private Role role;
    private String username;
}
