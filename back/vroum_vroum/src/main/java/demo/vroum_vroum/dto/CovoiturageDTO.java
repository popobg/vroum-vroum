package demo.vroum_vroum.dto;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO de l'entité Covoiturage
 */
public class CovoiturageDto implements Serializable {
    /** Identifiant unique et non modifiable du covoiturage */
    private int id;

    /** Date et heure du covoiturage */
    private LocalDateTime date;

    /** Adresse dto de départ du covoiturage */
    private AdresseDto adresseDepart;

    /** Adresse dto d'arrivée du covoiturage */
    private AdresseDto adresseArrivee;

    /** Distance estimée du trajet */
    private int distance;

    /** Durée estimée du trajet */
    private int duree;

    /** Collaborateur organisateur (conducteur) du covoiturage */
    private CollaborateurLiteDto organisateur;

    /** Véhicule utilisé par l'organisateur pour ce covoiturage */
    private VehiculeLiteDto vehicule;

    /** Collaborateurs passagers du covoiturage */
    private Set<CollaborateurLiteDto> passagers;

    /** Constructeur vide */
    public CovoiturageDto() {}

    public CovoiturageDto(int id, LocalDateTime date, AdresseDto adresseDepart, AdresseDto adresseArrivee, int distance, int duree, CollaborateurLiteDto orga, VehiculeLiteDto vehicule, Set<CollaborateurLiteDto> passagers) {
        this.id = id;
        this.date = date;
        this.adresseDepart = adresseDepart;
        this.adresseArrivee = adresseArrivee;
        this.distance = distance;
        this.duree = duree;
        this.organisateur = orga;
        this.vehicule = vehicule;
        this.passagers = passagers;
    }

    /**
     * Getter
     * @return Id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     * @param id Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter
     * @return date-heure
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Setter
     * @param date date-heure
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Getter
     * @return adresse de départ
     */
    public AdresseDto getAdresseDepart() {
        return adresseDepart;
    }

    /**
     * Setter
     * @param adresseDepart adresse de départ
     */
    public void setAdresseDepart(AdresseDto adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    /**
     * Getter
     * @return adresse d'arrivée
     */
    public AdresseDto getAdresseArrivee() {
        return adresseArrivee;
    }

    /**
     * Setter
     * @param adresseArrivee adresse d'arrivée
     */
    public void setAdresseArrivee(AdresseDto adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
    }

    /**
     * Getter
     * @return distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Setter
     * @param distance distance
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Getter
     * @return durée
     */
    public int getDuree() {
        return duree;
    }

    /**
     * Setter
     * @param duree durée
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }

    /**
     * Getter
     * @return collaborateur lite dto organisateur
     */
    public CollaborateurLiteDto getOrganisateur() {
        return organisateur;
    }

    /**
     * Setter
     * @param organisateur collaboteur lite dto
     */
    public void setOrganisateur(CollaborateurLiteDto organisateur) {
        this.organisateur = organisateur;
    }

    /**
     * Getter
     * @return véhicule
     */
    public VehiculeLiteDto getVehicule() {
        return vehicule;
    }

    /**
     * Setter
     * @param vehicule véhicule
     */
    public void setVehicule(VehiculeLiteDto vehicule) {
        this.vehicule = vehicule;
    }

    /**
     * Getter
     * @return liste de collaborateurs lite dto passagers
     */
    public Set<CollaborateurLiteDto> getPassagers() {
        return passagers;
    }

    /**
     * Setter
     * @param passagers liste de collaborateurs lite dto
     */
    public void setPassagers(Set<CollaborateurLiteDto> passagers) {
        this.passagers = passagers;
    }
}
