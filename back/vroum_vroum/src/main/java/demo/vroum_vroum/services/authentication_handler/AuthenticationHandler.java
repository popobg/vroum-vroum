package demo.vroum_vroum.services.authentication_handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Classe de sécurité indiquant comment gérer l'authentification
 */
@Configuration
public class AuthenticationHandler {
    /**
     * Méthode permettant de gérer la réponse envoyée au client en cas d'erreur d'authentification.
     * @return un objet AuthenticationFailureHandler
     */
    @Bean
    public static AuthenticationFailureHandler authenticationFailureHandler() {
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
