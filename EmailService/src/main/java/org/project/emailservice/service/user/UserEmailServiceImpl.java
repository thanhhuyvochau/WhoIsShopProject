package org.project.emailservice.service.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.emailservice.service.EmailService;
import org.project.emailservice.user.dto.EmailInfo;
import org.project.emailservice.user.dto.UserEmailInfo;

import org.project.emailservice.utils.ResourceUtil;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class UserEmailServiceImpl implements UserEmailService {
    private final EmailService emailService;
    private final ObjectMapper jsonMapper;

    public UserEmailServiceImpl(EmailService emailService, ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.jsonMapper = objectMapper;
    }

    @Override
    public void sendVerificationEmail(EmailInfo emailInfo) {
        // Sending Email
        Map<String, Object> dataMap = (Map<String, Object>) emailInfo.getData();

        UserEmailInfo userEmailInfo = jsonMapper.convertValue(dataMap, UserEmailInfo.class);
        String emailTemplate = ResourceUtil.emailTemplateLoader("verify-account.html");
        String msg = emailTemplate
                .replace("${firstName}", userEmailInfo.getFirstName())
                .replace("${lastName}", userEmailInfo.getLastName())
                .replace("${userName}", userEmailInfo.getUserName())
                .replace("${email}", userEmailInfo.getEmail())
                .replace("${phone}", userEmailInfo.getPhone())
                .replace("${verifyURL}", userEmailInfo.getVerifyURL());

        emailService.sendHtmlEmail(emailInfo.getRecipient(), emailInfo.getSubject(), msg);
    }


}
