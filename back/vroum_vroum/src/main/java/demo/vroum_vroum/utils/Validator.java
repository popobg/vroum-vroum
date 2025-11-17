package demo.vroum_vroum.utils;

import java.time.LocalDateTime;

/**
 * Classe de validation d'inputs
 */
public class Validator {
    /**
     * Vérifie si la string passée correspond au format d'un code postal français
     * @param input code postal à vérifier
     * @return true si le format correspond, sinon false
     */
    public static boolean matchCodePostalFormat(String input) {
        if (input == null) return false;

        return input.matches("^\\d{5}$");
    }

    /**
     * Vérifie la date-heure passée est ultérieure à la date-heure actuelle
     * @param date date à vérifier
     * @return true si la date passée est ultérieure, sinon false
     */
    public static boolean matchDateUlterieure(LocalDateTime date) {
        return date.isAfter(LocalDateTime.now());
    }

    /**
     * Vérifie que la chaîne de caractères répond au pattern suivant :
     * - Au moins 8 caractères
     * - Au moins une lettre majuscule et une lettre minuscule
     * - Au moins un chiffre
     * - Au moins un caractère spécial
     * @param password
     * @return true si le format est correct, sinon false
     */
    public static boolean isPasswordValid(String password) {
        if (password == null) return false;

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\d\\s])[a-zA-Z\\d\\w\\W]{8,}$";
        return password != null && password.matches(regex);
    }
}
