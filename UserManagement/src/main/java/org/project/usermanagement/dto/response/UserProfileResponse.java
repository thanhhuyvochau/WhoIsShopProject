package org.project.usermanagement.dto.response;

import lombok.Data;
import org.project.usermanagement.constant.Role;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
public class UserProfileResponse {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Boolean emailVerified = false;
    private Instant emailVerifiedAt;
    private Boolean phoneVerified = false;
    private Instant phoneVerifiedAt;
    private Boolean enabled = false;
    private Boolean locked = false;
    private Boolean isShopOwner = false;
    private Role role;
    Set<AddressResponse> addressResponses = Collections.EMPTY_SET;
}
