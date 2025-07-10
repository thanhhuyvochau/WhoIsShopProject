package org.project.usermanagement.service.implementation;

import org.project.usermanagement.dto.request.ModifyAddressRequest;
import org.project.usermanagement.dto.request.UpdateProfileRequest;
import org.project.usermanagement.dto.response.AddressResponse;
import org.project.usermanagement.dto.response.UserProfileResponse;
import org.project.usermanagement.entity.Address;
import org.project.usermanagement.entity.User;
import org.project.usermanagement.mapper.AddressMapper;
import org.project.usermanagement.mapper.UserMapper;
import org.project.usermanagement.repository.AddressRepository;
import org.project.usermanagement.repository.UserRepository;
import org.project.usermanagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository, UserMapper userMapper, AddressMapper addressMapper) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
    }

    @Override
    public UserProfileResponse getUserProfile(String username) {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("Username is not existed: " + username));
        UserProfileResponse userProfileResponse = userMapper.toUserProfileResponse(user);
        Set<Address> addresses = user.getAddresses();
        Set<AddressResponse> addressResponses = addresses.stream().map(addressMapper::toAddressResponse).collect(Collectors.toSet());

        userProfileResponse.setAddressResponses(addressResponses);
        return userProfileResponse;

    }

    @Override
    public Boolean addAddress(String username, ModifyAddressRequest modifyAddressRequest) {
        // TODO: Validate the address before save
        this.addressRepository.findAllByUser_UserName(username);
        Address address = addressMapper.toEntity(modifyAddressRequest);
        this.addressRepository.save(address);
        return true;
    }

    @Override
    public Boolean modifyAddress(String username, Integer addressId, ModifyAddressRequest modifyAddressRequest) {
        // TODO: Validate the address before save
        Address modifiedAddress = this.addressRepository
                .findById(addressId).orElseThrow(() -> new RuntimeException("Not found address:" + addressId));

        modifiedAddress.setCity(modifiedAddress.getCity());
        modifiedAddress.setCountryCode(modifiedAddress.getCountryCode());
        modifiedAddress.setPostalCode(modifiedAddress.getPostalCode());
        modifiedAddress.setStateProvince(modifiedAddress.getStateProvince());

        this.addressRepository.save(modifiedAddress);

        return true;
    }

    @Override
    public UserProfileResponse updateUserProfile(String username, UpdateProfileRequest request) {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("Username is not existed: " + username));
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        userRepository.save(user);

        return userMapper.toUserProfileResponse(user);
    }
}
