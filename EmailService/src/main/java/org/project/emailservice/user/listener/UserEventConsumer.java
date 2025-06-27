package org.project.emailservice.user.listener;

import org.project.emailservice.service.user.UserEmailService;
import org.project.emailservice.user.dto.EmailInfo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventConsumer {
    private final UserEmailService userEmailService;

    public UserEventConsumer(UserEmailService userEmailService) {
        this.userEmailService = userEmailService;
    }

    @RabbitListener(queues = {"email.queue"})
    public void receiveEmail(EmailInfo email) {
        switch (email.getType()) {
            case VERIFY_ACCOUNT:
                userEmailService.sendVerificationEmail(email);
                break;
            default: {
                throw new RuntimeException("Email type invalid");
            }
        }

    }
}
