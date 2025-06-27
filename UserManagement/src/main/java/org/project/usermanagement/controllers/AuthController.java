package org.project.usermanagement.controllers;

import jakarta.validation.Valid;
import org.project.usermanagement.config.RabbitConfig;
import org.project.usermanagement.dto.request.EmailInfo;
import org.project.usermanagement.dto.request.JwtResponse;
import org.project.usermanagement.dto.request.LoginRequest;
import org.project.usermanagement.dto.request.RegisterRequest;
import org.project.usermanagement.dto.response.ApiResponse;
import org.project.usermanagement.dto.response.RegisterResponse;
import org.project.usermanagement.service.AuthService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final RabbitTemplate rabbitTemplate;

    public AuthController(AuthService authService, RabbitTemplate rabbitTemplate) {
        this.authService = authService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.createNewUser(request)));
    }

    @PostMapping("/test-message")
    public void testMessage(@RequestBody EmailInfo emailInfo) {
        this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, "email.routing", emailInfo);
    }

    @GetMapping("/verify-email/{token}")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@PathVariable("token") String token) {
        try {
            this.authService.verifyEmail(token);
            return ResponseEntity.ok(ApiResponse.success("Verification is successful"));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success(this.authService.login(request)));
    }
}
