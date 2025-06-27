package org.project.usermanagement.mapper;

import org.mapstruct.Mapper;
import org.project.usermanagement.dto.request.UserEmailInfo;
import org.project.usermanagement.dto.response.RegisterResponse;
import org.project.usermanagement.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    RegisterResponse toRegisterResponse(User user);

    UserEmailInfo toUserInfo(User user);
}
