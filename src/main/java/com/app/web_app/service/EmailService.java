package com.app.web_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    public static final String CHANGE_PASSWORD_MESSAGE = "To change your password, please click the link below:\n'%s://%s:%s/changePassword?token=%s";
    public static final String REGISTRATION_MESSAGE = "To confirm your e-mail address, please click the link below:\n'%s://%s:%s/confirm?token=%s";
    public static final String ADMIN_EMAIL = "firelight.code@gmail.com";

    @Async
    public void sendEmail(EmailType emailType, String to, String mailText, String from) {

        SimpleMailMessage mailMessage = createMail(emailType, to, mailText, from);
        mailSender.send(mailMessage);
    }

    private SimpleMailMessage createMail(EmailType emailType, String to, String mailText, String from) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setSubject(switch (emailType) {
            case REGISTRATION -> "Registration confirmation";
            case CHANGE_PASSWORD -> "Password change";
            case REQUEST_ADMIN -> "Request to admin";
        });

        mailMessage.setFrom(Objects.requireNonNullElse(from, "noreply@domain.com"));
        mailMessage.setTo(to);

        mailMessage.setText(mailText);

        return mailMessage;
    }


}
