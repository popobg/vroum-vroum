package demo.vroum_vroum.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ValidatorTest {
    @ParameterizedTest
    @ValueSource(strings = {"75000", "69501", "78150"})
    void testMatchCodePostalFormatOK(String input) {
        assertTrue(Validator.matchCodePostalFormat(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2", "6950100", "7815 "})
    @NullAndEmptySource
    void testMatchCodePostalFormat_invalidInput(String input) {
        assertFalse(Validator.matchCodePostalFormat(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Password6!", "!6drowssaP", "69passW,", "[0mMoooo", "Voici 1 phrase plutôt longue"})
    void testIsPasswordValidOK(String password) {
        assertTrue(Validator.isPasswordValid(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "[0mMoo!", "Manque 1 caractere special", "manque une majuscule!6", "Manque un chiffre !", "MANQUE 1 MINUSCULE!"})
    @NullAndEmptySource
    void testIsPasswordValid_invalidInput(String password) {
        assertFalse(Validator.isPasswordValid(password));
    }

    @Test
    void testMatchDateUlterieureOK() {
        LocalDateTime date = LocalDateTime.of(2050, 01, 01, 8, 00);
        assertTrue(Validator.matchDateUlterieure(date));
    }

    @Test
    void testMatchDateUlterieure_dateAnterieure() {
        LocalDateTime date = LocalDateTime.of(2025, 11, 16, 16, 00);
        assertFalse(Validator.matchDateUlterieure(date));
    }

    @Test
    void testMatchDateUlterieure_today() {
        LocalDateTime date = LocalDateTime.now();
        assertFalse(Validator.matchDateUlterieure(date));
    }
}
