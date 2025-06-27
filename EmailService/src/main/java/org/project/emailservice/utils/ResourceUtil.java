package org.project.emailservice.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ResourceUtil {
    public static String emailTemplateLoader(String fileName) {
        try {
            ClassPathResource resource = new ClassPathResource("email-template/" + fileName);
            return Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template", e);
        }
    }
}
