package demo.vroum_vroum.dto;

import java.io.Serializable;

/**
 * Collaborateur DTO avec des informations allégées (lite)
 */
public class CollaborateurLiteDto implements Serializable {
    private int id;

    /** Nom du collaborateur */
    private String nom;

    /** Prénom du collaborateur */
    private String prenom;

    /** Numéro de téléphone du collaborateur */
    private String telephone;

    /** Constructeur vide */
    public CollaborateurLiteDto() {}

    /**
     * Constructeur
     * @param nom Nom
     * @param prenom Prénom
     * @param telephone Numéro téléphone
     */
    public CollaborateurLiteDto(int id, String nom, String prenom, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    /**
     * Getter
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter
     * @param nom nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter
     * @return prénom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter
     * @param prenom prénom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Getter
     * @return numéro téléphone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Setter
     * @param telephone numéro téléphone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
