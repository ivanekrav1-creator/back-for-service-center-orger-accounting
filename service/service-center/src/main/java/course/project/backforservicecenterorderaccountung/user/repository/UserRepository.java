package course.project.backforservicecenterorderaccountung.user.repository;

import course.project.backforservicecenterorderaccountung.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByUsername(String username);
}
