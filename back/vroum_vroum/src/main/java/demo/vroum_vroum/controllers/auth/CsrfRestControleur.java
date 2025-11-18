package demo.vroum_vroum.controllers.auth;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller offrant une route permettant de récupérer le token CSRF
 */
@RestController
public class CsrfRestControleur {

    /**
     * Retourne un token CSRF et Spring le garde en mémoire.
     * A ajouter dans le header des requêtes modifiant la BDD (POST, PUT, DELETE)
     *
     * @param token token CSRF
     * @return token CSRF
     */
    @GetMapping("/csrf/token")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
