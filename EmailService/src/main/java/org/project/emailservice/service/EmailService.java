package org.project.emailservice.service;

import java.io.File;

public interface EmailService {
    void sendHtmlEmail(String to, String subject, String htmlContent);

    void sendEmailWithAttachment(String to, String subject, String htmlContent, File attachment);
}