package org.project.usermanagement.service;

import org.project.usermanagement.dto.request.ModifyAddressRequest;
import org.project.usermanagement.dto.request.UpdateProfileRequest;
import org.project.usermanagement.dto.response.AddressResponse;
import org.project.usermanagement.dto.response.UserProfileResponse;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserProfileResponse getUserProfile(String username);

    Boolean addAddress(String username, ModifyAddressRequest modifyAddressRequest);

    Boolean modifyAddress(String username, Integer addressId, ModifyAddressRequest modifyAddressRequest);

    UserProfileResponse updateUserProfile(String username, UpdateProfileRequest request);
}
