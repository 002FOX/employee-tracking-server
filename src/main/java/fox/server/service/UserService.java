package fox.server.service;

import fox.server.model.User;
import fox.server.payload.UserEditRequest;
import fox.server.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository repository;

    public void disableUser(Long userId) {
        Optional<User> userOptional = repository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEnabled(false);
            repository.save(user);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public void editUser(Long userId, UserEditRequest updatedUser) {
        Optional<User> userOptional = repository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());

            repository.save(user);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }
}