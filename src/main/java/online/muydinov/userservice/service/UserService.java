package online.muydinov.userservice.service;

import online.muydinov.userservice.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User saveUser(User user);
    Boolean verifyToken(String token);
}
