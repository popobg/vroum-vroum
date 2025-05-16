package demo.vroum_vroum.dto;

import java.io.Serializable;

/**
 * DTO lite de l'entité Vehicule
 */
public class VehiculeLiteDto implements Serializable {
    /** Identifiant unique et non modifiable du véhicule */
    private int id;

    /** Marque du véhicule */
    private String marque;

    /** Modèle du véhicule */
    private String modele;

    /** Constructeur vide */
    public VehiculeLiteDto() {}

    /**
     * Constructeur
     * @param id Id
     * @param marque marque
     * @param modele modèle
     */
    public VehiculeLiteDto(int id, String marque, String modele) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
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
}
