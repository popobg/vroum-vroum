package demo.vroum_vroum.exceptions;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller renvoyant des réponses contenant les messages d'erreur si des exceptions sont levées
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Messages d'erreur par défaut
    private static final String DEFAULT_ENTITY_NOT_FOUND_ERROR_MESSAGE = "L'entité demandée est introuvable dans la base de données.";
    private static final String DEFAULT_ILLEGAL_ARGUMENTS_ERROR_MESSAGE = "Les données communiquées ne sont pas valides.";
    private static final String DEFAULT_USER_NOT_FOUND_ERROR_MESSAGE = "Aucun utilisateur connecté.";
    private static final String DEFAULT_GENERAL_ERROR_MESSAGE = "Une erreur interne s'est produite.";

    // Logger
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Méthode gérant les exceptions lorsqu'une entité ne peut être récupérée via un identifiant unique
     * @param ex exception levée lorsque le serveur ne trouve pas l'entité dans la base de données
     * @return réponse contenant le message d'erreur informant que l'entité est introuvable
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("EntityNotFoundException", ex);

        Map<String, String> response = new HashMap<>();
        response.put("Erreur", "EntityNotFoundException");
        // Message d'erreur : soit celui apporté par l'exception s'il existe,
        // Sinon le message par défaut
        response.put("message", StringUtils.isEmpty(ex.getMessage()) ? DEFAULT_ENTITY_NOT_FOUND_ERROR_MESSAGE : ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Méthode gérant les exceptions lorsque des arguments passés en paramètre de requête ne sont pas valides
     * @param ex exception levée lorsque les arguments passés en paramètre de la requête ne sont pas valides
     * @return réponse contenant le message d'erreur informant que les arguments ne sont pas valides
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException", ex);

        Map<String, String> response = new HashMap<>();
        response.put("Erreur", "IllegalArgumentException");
        response.put("message", StringUtils.isEmpty(ex.getMessage()) ? DEFAULT_ILLEGAL_ARGUMENTS_ERROR_MESSAGE : ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Méthode gérant les exceptions lorsque l'utilisateur connecté n'a pas pu être reconnu
     * @param ex exception levée lorsque l'utilisateur connecté n'a pas pu être reconnu
     * @return réponse contenant le message d'erreur informant que l'utilisateur connecté n'a pas pu être reconnu
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("UserNotFoundException", ex);

        Map<String, String> response = new HashMap<>();
        response.put("Erreur", "UserNotFoundException");
        response.put("message", StringUtils.isEmpty(ex.getMessage()) ? DEFAULT_USER_NOT_FOUND_ERROR_MESSAGE : ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * Méthode gérant les exceptions générales
     * @param ex exception levée lorsqu'une erreur inattendue se produit
     * @return un message d'erreur indiquant qu'une erreur interne s'est produite
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        log.error("Internal server error: ", ex);

        Map<String, String> response = new HashMap<>();
        response.put("Erreur", "Internal server error");
        response.put("message", StringUtils.isEmpty(ex.getMessage()) ? DEFAULT_GENERAL_ERROR_MESSAGE : ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
