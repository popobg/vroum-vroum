package demo.vroum_vroum.exceptions;

/**
 * Classe d'exception personnalisée si l'application ne trouve pas l'utilisateur connecté
 */
public class UserNotFoundException extends RuntimeException {
    /** Constructeur sans argument */
    public UserNotFoundException() {
        super("Utilisateur non connecté");
    }

    /**
     * Constructeur
     * @param message message d'erreur personnalisé
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
