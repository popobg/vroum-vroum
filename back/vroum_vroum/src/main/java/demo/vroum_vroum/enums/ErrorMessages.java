package demo.vroum_vroum.enums;

/**
 * Enumération pour les messages d'erreur
 */
public enum ErrorMessages {
    ERROR_MESSAGE_PASSWORD("Le mot de passe doit contenir au moins 8 caractères, dont au minimum une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial"),
    ERROR_MESSAGE_UNKNOWN_ID("Pas de collaborateur trouvé pour l'ID "),
    ERROR_MESSAGE_UNKNOWN_USERNAME("Pas d'utilisateur trouvé pour le pseudo "),
    ERROR_MESSAGE_NO_CONNECTED_USER("Pas d'utilisateur connecté"),
    ERROR_MESSAGE_ID_FOR_NEW_ITEM("Un nouveau collaborateur ne peut pas avoir d'ID");

    private final String text;

    ErrorMessages(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
