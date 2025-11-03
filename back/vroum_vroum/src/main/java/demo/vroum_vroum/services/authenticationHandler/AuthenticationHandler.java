package demo.vroum_vroum.services.authenticationHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class AuthenticationHandler {
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            String message;

            if (exception instanceof UsernameNotFoundException) {
                message = "Collaborateur inconnu";
            } else if (exception instanceof BadCredentialsException) {
                message = "Pseudo et/ou mot de passe invalide";
            } else {
                message = "L'authentification a échoué : " + exception.getMessage()
;            }

            response.getWriter().write("{\"error\": \"" + message + "\"}");
        };
    }
}
