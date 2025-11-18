package demo.vroum_vroum.enums;

/**
 * Enumération pour les messages d'erreur
 */
public final class ErrorMessages {
    public final static String ERROR_MESSAGE_PASSWORD = "Le mot de passe doit contenir au moins 8 caractères, dont au minimum une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial";
    public final static String ERROR_MESSAGE_UNKNOWN_ID = "Pas de collaborateur trouvé pour l'ID ";
    public final static String ERROR_MESSAGE_UNKNOWN_USERNAME = "Pas d'utilisateur trouvé pour le pseudo ";
    public final static String ERROR_MESSAGE_NO_CONNECTED_USER = "Pas d'utilisateur connecté";
    public final static String ERROR_MESSAGE_ID_FOR_NEW_ITEM = "Un nouveau collaborateur ne peut pas avoir d'ID";
    public final static String ERROR_MESSAGE_PSEUDO = "Le collaborateur doit avoir un pseudo";
    public final static String ERROR_MESSAGE_NOM = "Le nom doit comporter au moins deux caractères";
    public final static String ERROR_MESSAGE_PRENOM = "Le prénom doit comporter au moins deux caractères";
    public final static String ERROR_MESSAGE_EMAIL = "Le format de l'email n'est pas valide";
}