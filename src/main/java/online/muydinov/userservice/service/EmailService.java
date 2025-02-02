package online.muydinov.userservice.service;


public interface EmailService {
    void sendSimpleMailMessage(String name, String to, String token);
    void sendMimeMessageWithAttachment(String name, String to, String token);
    void sendMimeMessageWithEmbedded(String name, String to, String token);
    void sendHtmlEmail(String name, String to, String token);
    void sendMimeMessageWithEmbeddedFiles(String name, String to, String token);
}
