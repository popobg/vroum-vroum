package demo.vroum_vroum.exceptions;

/**
 * Classe d'exception personnalisée si l'application ne trouve pas l'utilisateur connecté
 */
public class NotAuthenticatedException extends RuntimeException {
    /** Constructeur sans argument */
    public NotAuthenticatedException() {
        super("Utilisateur non connecté");
    }

    /**
     * Constructeur
     * @param message message d'erreur personnalisé
     */
    public NotAuthenticatedException(String message) {
        super(message);
    }
}
