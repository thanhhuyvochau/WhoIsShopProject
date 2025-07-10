package org.project.usermanagement.mapper;

import org.mapstruct.Mapper;
import org.project.usermanagement.dto.request.ModifyAddressRequest;
import org.project.usermanagement.dto.request.UserEmailInfo;
import org.project.usermanagement.dto.response.AddressResponse;
import org.project.usermanagement.dto.response.RegisterResponse;
import org.project.usermanagement.dto.response.UserProfileResponse;
import org.project.usermanagement.entity.Address;
import org.project.usermanagement.entity.User;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponse toAddressResponse(Address address);

    Address toEntity(ModifyAddressRequest request);
}
