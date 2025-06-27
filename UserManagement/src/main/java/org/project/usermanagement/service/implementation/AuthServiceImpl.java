package org.project.usermanagement.service.implementation;

import org.project.usermanagement.config.RabbitConfig;
import org.project.usermanagement.constant.Role;
import org.project.usermanagement.dto.request.*;
import org.project.usermanagement.dto.response.RegisterResponse;
import org.project.usermanagement.entity.User;
import org.project.usermanagement.entity.Verification;
import org.project.usermanagement.exception.types.EmailDuplicateException;
import org.project.usermanagement.exception.types.PhoneDuplicateException;
import org.project.usermanagement.exception.types.UserNameDuplicateException;
import org.project.usermanagement.mapper.UserMapper;
import org.project.usermanagement.repository.UserRepository;
import org.project.usermanagement.repository.VerificationRepository;
import org.project.usermanagement.service.AuthService;
import org.project.usermanagement.service.UserCacheService;
import org.project.usermanagement.utilies.JwtUtil;
import org.project.usermanagement.utilies.PasswordUtil;
import org.project.usermanagement.utilies.UserValidator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserCacheService redisUserCacheService;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final RabbitTemplate msgTemplate;
    private final String VERIFICATION_EMAIL_SUBJECT = "Please Confirm Your Email Address";
    private final VerificationRepository verificationRepository;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, UserCacheService redisUserCacheService, UserMapper userMapper, UserValidator userValidator, RabbitTemplate rabbitTemplate, VerificationRepository verificationRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.redisUserCacheService = redisUserCacheService;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
        this.msgTemplate = rabbitTemplate;
        this.verificationRepository = verificationRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public RegisterResponse createNewUser(RegisterRequest registerRequest) {
        // Validate Duplicate
        if (userValidator.isEmailDuplicate(registerRequest.getEmail())) {
            throw new EmailDuplicateException();
        } else if (userValidator.isPhoneDuplicate(registerRequest.getPhone())) {
            throw new PhoneDuplicateException();
        } else if (userValidator.isUsernameExist(registerRequest.getUserName())) {
            throw new UserNameDuplicateException();
        }

        User createdUser = User.builder()
                .userName(registerRequest.getUserName())
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .password(PasswordUtil.encrypt(registerRequest.getPassword()))
                .enabled(false) // Change after verification
                .isShopOwner(false)
                .role(Role.CUSTOMER)
                .build();


        // Persist new user
        userRepository.save(createdUser);
        // Add user to cache
        this.redisUserCacheService.cacheUserFields(createdUser);
        // Create verification
        Verification verification = new Verification();
        verification.setVerifiedUser(createdUser);
        verification.setExpired(Instant.now().plus(1, ChronoUnit.DAYS));
        verification.setUsed(false);
        this.verificationRepository.save(verification);
        // Send Verify email
        UserEmailInfo userInfoForVerifying = userMapper.toUserInfo(createdUser);
        String verifiedURL = "http://localhost:8081/auth/verify-email/" + verification.getId();
        userInfoForVerifying.setVerifyURL(verifiedURL);

        EmailInfo verificationEmail = EmailInfo.builder().
                subject(VERIFICATION_EMAIL_SUBJECT)
                .recipient(createdUser.getEmail())
                .type("VERIFY_ACCOUNT")
                .data(userInfoForVerifying)
                .build();
        this.msgTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, "email.routing", verificationEmail);


        return userMapper.toRegisterResponse(createdUser);
    }

    @Override
    public void verifyEmail(String token) throws RuntimeException {
        Verification verification = verificationRepository
                .findById(UUID.fromString(token)).orElseThrow(() -> new RuntimeException("Token does not exist"));
        if (verification.getUsed()) {
            throw new RuntimeException("Token is used");
        }
        if (Instant.now().isAfter(verification.getExpired())) {
            throw new RuntimeException("Token is expired");
        } else {
            User verifiedUser = verification.getVerifiedUser();
            verifiedUser.setEmailVerified(true);
            verifiedUser.setEmailVerifiedAt(Instant.now());
            verifiedUser.setEnabled(true);
            verification.setUsed(true);
            this.userRepository.save(verifiedUser);
        }
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        if (!userValidator.isUsernameExist(loginRequest.getUsername())) {
            throw new RuntimeException("User name not existed");
        }
        User loginUser = userRepository
                .findByUserName(loginRequest.getUsername()).orElseThrow(() -> new RuntimeException("User is not existed"));
        if (!PasswordUtil.matches(loginRequest.getPassword(), loginUser.getPassword())) {
            throw new RuntimeException("Password is wrong");
        }
        String token = jwtUtil.generateToken(loginUser.getUserName());
        return new JwtResponse(token);
    }
}
