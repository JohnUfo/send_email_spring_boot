package online.muydinov.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import online.muydinov.userservice.domain.Confirmation;
import online.muydinov.userservice.domain.User;
import online.muydinov.userservice.repository.ConfirmationRepository;
import online.muydinov.userservice.repository.UserRepository;
import online.muydinov.userservice.service.EmailService;
import online.muydinov.userservice.service.UserService;
import org.springframework.stereotype.Service;

import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;

    @Override
    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setEnabled(false);
        userRepository.save(user);
        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);
//        emailService.sendSimpleMailMessage(user.getName(), user.getEmail(), confirmation.getToken());
//        emailService.sendMimeMessageWithAttachment(user.getName(), user.getEmail(), confirmation.getToken());
//        emailService.sendMimeMessageWithEmbeddedFiles(user.getName(), user.getEmail(), confirmation.getToken());
        emailService.sendHtmlEmail(user.getName(), user.getEmail(), confirmation.getToken());
        return user;
    }

    @Override
    public Boolean verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token);
        User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
//        confirmationRepository.delete(confirmation);
        return TRUE;
    }
}
