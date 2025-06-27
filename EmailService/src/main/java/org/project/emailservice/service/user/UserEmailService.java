package org.project.emailservice.service.user;

import org.project.emailservice.user.dto.EmailInfo;

public interface UserEmailService {
    void sendVerificationEmail(EmailInfo email);
}
