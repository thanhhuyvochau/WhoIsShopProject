package org.project.usermanagement.controllers;

import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import org.project.usermanagement.anotation.session.definition.CurrentSession;
import org.project.usermanagement.anotation.session.model.UserSession;
import org.project.usermanagement.dto.request.UpdateProfileRequest;
import org.project.usermanagement.dto.response.ApiResponse;
import org.project.usermanagement.dto.response.UserProfileResponse;
import org.project.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(@Parameter(hidden = true) @CurrentSession UserSession userSession) {
        return ResponseEntity.ok(ApiResponse.success(this.userService.getUserProfile(userSession.getUsername())));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(@Parameter(hidden = true) @CurrentSession UserSession userSession, @RequestBody @Valid UpdateProfileRequest request) {
        return ResponseEntity.ok(ApiResponse.success(this.userService.updateUserProfile(userSession.getUsername(), request)));
    }
}
