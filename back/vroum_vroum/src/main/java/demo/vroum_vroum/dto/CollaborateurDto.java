package demo.vroum_vroum.dto;

import java.io.Serializable;

/**
 * Collaborateur DTO sans relations many-to-many
 */
public class CollaborateurDto implements Serializable {
    /** Id du collaborateur */
    private int id;

    /** Nom du collaborateur */
    private String nom;

    /** Prénom du collaborateur */
    private String prenom;

    /** Adresse du collaborateur */
    private String adresse;

    /** Adresse électronique du collaborateur */
    private String email;

    /** Numéro de téléphone du collaborateur */
    private String telephone;

    /** Pseudo du collaborateur */
    private String pseudo;

    /** Mot de passe du collaborateur */
    private String password;

    /** Statut administrateur de l'application */
    private Boolean admin;

    /** Constructeur vide */
    public CollaborateurDto() {}

    /**
     * Constructeur
     * @param id identifiant
     * @param nom nom
     * @param prenom prénom
     * @param adresse adresse
     * @param email adresse électronique
     * @param telephone numéro de téléphone
     * @param pseudo pseudo
     * @param password mot de passe
     * @param admin statut administrateur de l'application
     */
    public CollaborateurDto(int id, String nom, String prenom, String adresse, String email, String telephone, String pseudo, String password, Boolean admin) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.pseudo = pseudo;
        this.password = password;
        this.admin = admin;
    }

    /**
     * Getter
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     * @return id
     */
    public void setId(int id) {
        this.id = id;
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
     * @return adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Setter
     * @param adresse adresse
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Getter
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter
     * @return numéro de téléphone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Setter
     * @param telephone numéro de téléphone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Getter
     * @return pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Setter
     * @param pseudo pseudo
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Getter
     * @return mot de passe
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter
     * @param password mot de passe
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter
     * @return admin
     */
    public Boolean getAdmin() {
        return admin;
    }

    /**
     * Setter
     * @param admin statut administrateur
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
