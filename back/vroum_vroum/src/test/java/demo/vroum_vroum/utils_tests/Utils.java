package demo.vroum_vroum.utils_tests;

import java.util.List;
import java.util.function.Function;

/**
 * Classe utilitaire des tests unitaires
 */
public final class Utils {
    /**
     * Méthode permettant de mapper une liste réduite à partir d'une autre liste, selon une méthode de mapper.
     * @param <T> type de données de la liste initiale
     * @param <R> type de données de la liste de sortie
     * @param originalList liste initiale
     * @param mapper fonction récupérant les valeurs à mapper
     * @return liste de valeurs R mappées à partir de la liste initiale
     */
    public static <T, R> List<R> customMap(List<T> originalList, Function<T, R> mapper) {
        return originalList.stream()
        .map(mapper)
        .toList();
    }
}
