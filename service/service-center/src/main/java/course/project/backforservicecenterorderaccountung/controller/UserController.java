package course.project.backforservicecenterorderaccountung.controller;

import course.project.backforservicecenterorderaccountung.service.UserService;
import course.project.backforservicecenterorderaccountung.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.center.api.UsersApi;
import service.center.model.UserCreateDto;
import service.center.model.UserDto;
import service.center.model.UserUpdateDto;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class UserController implements UsersApi{

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserDto> createUser(UserCreateDto userCreateDto) {
    User user = userService.createUser(userCreateDto);
    UserDto createdUser = mapToDto(user);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdUser.getId())
        .toUri();
    return ResponseEntity.created(location).body(createdUser);
  }


  @Override
  public ResponseEntity<Void> deleteUser(UUID userId) {
    return null;
  }

  @Override
  public ResponseEntity<UserDto> getCurrentUser() {
    return null;
  }

  @Override
  public ResponseEntity<UserDto> getUserById(UUID userId) {
    return null;
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<UserDto>> getUsers() {
    List<UserDto> users = userService.getUsers().stream().map(this::mapToDto).toList();
    return ResponseEntity.ok(users);
  }

  @Override
  public ResponseEntity<Void> updateUser(UUID userId, UserUpdateDto userUpdateDto) {
    return null;
  }

  private UserDto mapToDto(User user) {
    return new UserDto(user.getId(),user.getUsername(),user.getRole());
  }
}
