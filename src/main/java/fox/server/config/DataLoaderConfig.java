package fox.server.config;

import fox.server.model.Position;
import fox.server.model.Role;
import fox.server.model.User;
import fox.server.repository.PositionRepository;
import fox.server.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Configuration
public class DataLoaderConfig {

    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    public DataLoaderConfig(PositionRepository positionRepository, UserRepository userRepository) {
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Position p = new Position(1L, "Employee", "Default position of an employee", Double.valueOf(10000), LocalTime.of(9, 0), LocalTime.of(5, 0));
            positionRepository.save(p);
            User user = new User("Sami", "ELARIF", "sami@mail.com", "password", p, Role.ADMIN, true);
            User user1 = new User("User", "USER", "user@mail.com", "password", p, Role.USER, true);
            userRepository.save(user);
            userRepository.save(user1);
        };
    }
}