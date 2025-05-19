package demo.vroum_vroum.dto;

import demo.vroum_vroum.entities.Collaborateur;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO de l'entité Vehicule
 */
public class VehiculeDto {
    /** Identifiant unique et non modifiable du véhicule */
    private int id;

    /** Immatriculation du véhicule */
    @NotNull
    private String immatriculation;

    /** Marque du véhicule */
    @NotNull
    @Size(min = 1)
    private String marque;

    /** Modèle du véhicule */
    @NotNull
    @Size(min = 1)
    private String modele;

    /** Nombre de places dans le véhicule */
    @NotNull
    @Size(min = 2)
    private int nbPlaces;

    /** Collaborateur possédant ce véhicule */
    private Collaborateur conducteur;

    /** Constructeur vide */
    public VehiculeDto() {}

    /**
     * Constructeur avec informations lite
     * @param id identifiant
     * @param nbPlaces nombre places
     * @param modele modèle
     * @param marque marque
     */
    public VehiculeDto(int id, int nbPlaces, String modele, String marque) {
        this(id, nbPlaces, modele, marque, null, null);
    }

    /**
     * Constructeur
     * @param nbPlaces nombre de places
     * @param modele modèle
     * @param marque marque
     * @param immatriculation immatriculation
     * @param conducteur collaborateur conducteur
     */
    public VehiculeDto(int nbPlaces, String modele, String marque, String immatriculation, Collaborateur conducteur) {
        this(0, nbPlaces, modele, marque, immatriculation, conducteur);
    }

    /**
     * Constructeur
     * @param id identifiant
     * @param nbPlaces nombre de places
     * @param modele modèle
     * @param marque marque
     * @param immatriculation immatriculation
     * @param conducteur collaborateur conducteur
     */
    public VehiculeDto(int id, int nbPlaces, String modele, String marque, String immatriculation, Collaborateur conducteur) {
        this.id = id;
        this.nbPlaces = nbPlaces;
        this.modele = modele;
        this.marque = marque;
        this.immatriculation = immatriculation;
        this.conducteur = conducteur;
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
     * @return immatriculation
     */
    public String getImmatriculation() {
        return immatriculation;
    }

    /**
     * Setter
     * @param immatriculation immatriculation
     */
    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    /**
     * Getter
     * @return marque
     */
    public String getMarque() {
        return marque;
    }

    /**
     * Setter
     * @param marque marque
     */
    public void setMarque(String marque) {
        this.marque = marque;
    }

    /**
     * Getter
     * @return modèle
     */
    public String getModele() {
        return modele;
    }

    /**
     * Setter
     * @param modele modèle
     */
    public void setModele(String modele) {
        this.modele = modele;
    }

    /**
     * Getter
     * @return nombre de places
     */
    public int getNbPlaces() {
        return nbPlaces;
    }

    /**
     * Setter
     * @param nbPlaces nombre de places
     */
    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    /**
     * Getter
     * @return collaborateur possédant le véhicule
     */
    public Collaborateur getConducteur() {
        return conducteur;
    }

    /**
     * Setter
     * @param conducteur collaborateur possédant le véhicule
     */
    public void setConducteur(Collaborateur conducteur) {
        this.conducteur = conducteur;
    }
}
