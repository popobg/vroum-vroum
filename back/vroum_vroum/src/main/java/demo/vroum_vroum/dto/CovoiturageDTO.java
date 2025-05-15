package demo.vroum_vroum.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Classe DTO utilisée pour sérialiser/désérialiser les données en entités JPA (Covoiturage)
 */
public class CovoiturageDto implements Serializable {
    /** Identifiant du covoiturage */
    private int id;

    /** Date du covoiturage */
    private LocalDateTime date;

    /** Adresse de départ du covoiturage */
    private AdresseDto adresseDepart;

    /** Adresse d'arrivée du covoiturage */
    private AdresseDto adresseArrivee;

    /** Nombre de places dans le covoiturage */
    private int nbPlaces;

    /** Distance estimée du trajet */
    private long distance;

    /** Durée estimée du trajet */
    private long duree;

    /** Organisateur et conducteur du covoiturage */
    private CollaborateurLiteDto organisateur;

    /** Collaborateurs passagers dans le covoiturage */
    private Set<CollaborateurLiteDto> collaborateurs;
}
