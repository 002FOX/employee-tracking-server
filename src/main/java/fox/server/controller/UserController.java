package fox.server.controller;

import fox.server.model.User;
import fox.server.payload.UserEditRequest;
import fox.server.payload.UserResponse;
import fox.server.repository.UserRepository;
import fox.server.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PatchMapping("/{userId}/disable")
    public ResponseEntity<String> disableUser(@PathVariable Long userId) {
        try {
            userService.disableUser(userId);
            return ResponseEntity.ok("User disabled successfully");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        try{
            List<User> users = userRepository.findAll();
            List<UserResponse> response = new ArrayList<>();
            for(User user: users) {
                response.add(new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.isEnabled(), user.getRole(), user.getPosition().getTitle()));
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> searchUser(@PathVariable Long userId) {
        User user = userRepository.findUserById(userId).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.isEnabled(), user.getRole(), user.getPosition().getTitle()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/edit/{userId}")
    public ResponseEntity<String> editUser(@PathVariable Long userId, @RequestBody UserEditRequest userEditRequest) {
        userService.editUser(userId, userEditRequest);
        return ResponseEntity.ok("User details updated successfully");
    }



}