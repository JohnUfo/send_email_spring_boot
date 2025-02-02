package online.muydinov.userservice.service.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import online.muydinov.userservice.service.EmailService;
import online.muydinov.userservice.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New user Account Verification";
    public static final String UTF_8_ENCODING = "UTF-8";
    private final JavaMailSender emailSender;
    @Value("${spring.mail.verify.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    @Override
    public void sendSimpleMailMessage(String name, String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(EmailUtils.getEmailMessage(name, host, token));
            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Async
    @Override
    public void sendMimeMessageWithAttachment(String name, String to, String token) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(EmailUtils.getEmailMessage(name, host, token));
            FileSystemResource fat = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/fat.png"));
            FileSystemResource resume = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/Resume.pdf"));
            helper.addAttachment(resume.getFilename(), resume);
            helper.addAttachment(fat.getFilename(), fat);
            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Async
    @Override
    public void sendMimeMessageWithEmbeddedFiles(String name, String to, String token) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(EmailUtils.getEmailMessage(name, host, token));
            FileSystemResource fat = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/fat.png"));
            FileSystemResource resume = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/Resume.pdf"));
            helper.addInline(getContentId(resume.getFilename()), resume);
            helper.addInline(getContentId(fat.getFilename()), fat);
            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Async
    @Override
    public void sendMimeMessageWithEmbedded(String name, String to, String token) {

    }

    @Async
    @Override
    public void sendHtmlEmail(String name, String to, String token) {

    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }

    private String getContentId(String fileName) {
        return "<" + fileName + "/>";
    }
}
