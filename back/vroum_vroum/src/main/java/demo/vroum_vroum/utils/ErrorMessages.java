package demo.vroum_vroum.utils;

/**
 * Classe utilitaire de constantes de messages d'erreur.
 */
public final class ErrorMessages {
    private ErrorMessages() {
        throw new IllegalStateException("Classe utilitaire de constantes");
    }

    public static final String INVALID_PASSWORD_ERROR_MESSAGE = "Le mot de passe doit contenir au moins 8 caractères, dont au minimum une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial";
    public static final String UNKNOWN_ID_ERROR_MESSAGE = "Pas de collaborateur trouvé pour l'ID donné";
    public static final String UNKNOWN_USERNAME_ERROR_MESSAGE = "Pas d'utilisateur trouvé pour le pseudo donné";
    public static final String NO_CONNECTED_USER_ERROR_MESSAGE = "Pas d'utilisateur connecté";
    public static final String ID_FOR_NEW_ITEM_ERROR_MESSAGE = "Un nouveau collaborateur ne peut pas avoir d'ID";
    public static final String INVALID_PSEUDO_ERROR_MESSAGE = "Le collaborateur doit avoir un pseudo";
    public static final String INVALID_NOM_ERROR_MESSAGE = "Le nom doit comporter au moins deux caractères";
    public static final String INVALID_PRENOM_ERROR_MESSAGE = "Le prénom doit comporter au moins deux caractères";
    public static final String INVALID_EMAILE_ERROR_MESSAGE = "Le format de l'email n'est pas valide";
}