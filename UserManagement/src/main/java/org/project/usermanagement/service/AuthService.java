package org.project.usermanagement.service;

import org.project.usermanagement.dto.request.JwtResponse;
import org.project.usermanagement.dto.request.LoginRequest;
import org.project.usermanagement.dto.request.RegisterRequest;
import org.project.usermanagement.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse createNewUser(RegisterRequest registerRequest);

    void verifyEmail(String token);

    JwtResponse login(LoginRequest loginRequest);
}
