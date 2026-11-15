package course.project.backforservicecenterorderaccountung.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
  public String getCurrentUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
