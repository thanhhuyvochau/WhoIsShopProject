package org.project.usermanagement.constant;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("Admin"),
    CUSTOMER("Customer");

    Role(String roleName) {
        this.roleName = roleName;
    }

    private final String roleName;

}
