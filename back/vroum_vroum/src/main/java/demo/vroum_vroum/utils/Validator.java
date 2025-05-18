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
}
