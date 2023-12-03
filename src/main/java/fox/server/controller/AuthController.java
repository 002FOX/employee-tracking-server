package fox.server.controller;


import fox.server.model.Position;
import fox.server.model.Role;
import fox.server.repository.PositionRepository;
import fox.server.utils.*;
import jakarta.transaction.Transactional;
import fox.server.model.Token;
import fox.server.model.User;
import fox.server.repository.TokenRepository;
import fox.server.repository.UserRepository;
import fox.server.utils.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<TokenLoginResponse> loginUser(@RequestBody UserLoginRequest loginRequest) {

        try {
            User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
            if (user != null && BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
                String token = JwtUtil.generateToken(user);
                Token jwt = new Token(token, user);
                tokenRepository.save(jwt);
                user.addToken(jwt);
                return ResponseEntity.ok(new TokenLoginResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {

        try {
            if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username is already registered");
            }

            Position pos = positionRepository.getReferenceById(1L);
            User user = new User(registrationRequest, pos, Role.USER, true);
            userRepository.save(user);
            return ResponseEntity.ok("Registration successful");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestBody UserLogoutRequest request){
        try {
            if (userRepository.findByUsername(request.getUsername()).isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user is authenticated");
            }
            User u = userRepository.findByUsername(request.getUsername()).orElse(null);
            if(u != null) {
                List<Token> t = tokenRepository.findByUserId(u.getId());
                tokenRepository.deleteAll(t);
                return ResponseEntity.ok("Logged out successfully!");
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}