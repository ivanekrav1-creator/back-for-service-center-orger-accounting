package course.project.backforservicecenterorderaccountung.service;

import course.project.backforservicecenterorderaccountung.user.entity.User;
import course.project.backforservicecenterorderaccountung.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.center.model.UserCreateDto;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SecurityService securityService;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SecurityService securityService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
      this.securityService = securityService;
  }

  @Transactional
  public User createUser(UserCreateDto dto) {
    String encodedPassword = passwordEncoder.encode(dto.getPassword());
    User user = new User(dto.getUsername(), encodedPassword, dto.getRole());
    return userRepository.save(user);
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public User getUserById(UUID userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
  }

    public User getCurrentUser() {
        String username = securityService.getCurrentUsername();
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }
}
