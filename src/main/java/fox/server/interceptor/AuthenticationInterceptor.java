package fox.server.interceptor;

import fox.server.config.RoleRequired;
import fox.server.model.Role;
import fox.server.model.User;
import fox.server.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import fox.server.model.Token;
import fox.server.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Extract the token from the Authorization header
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            Token jwt = tokenRepository.findByToken(token).orElse(null);

            if (jwt != null) {
                // Authentication successful, check if the user has the required role
                Role requiredRole = getRequiredRole(handler);

                if (requiredRole == null || jwt.getUser().getRole() == requiredRole) {
                    // User has the required role or no specific role is required
                    return true;
                } else {
                    // User does not have the required role
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    return false;
                }
            }
        }

        // Token is missing or invalid
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
    }

    private Role getRequiredRole(Object handler) {

        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            RoleRequired roleRequired = method.getMethodAnnotation(RoleRequired.class);
            if (roleRequired != null) {
                return roleRequired.value();
            }
        }
        return null;
    }
}