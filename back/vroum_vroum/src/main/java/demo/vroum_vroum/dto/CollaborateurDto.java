package demo.vroum_vroum.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Collaborateur DTO
 */
public class CollaborateurDto implements Serializable {
    /** Id du collaborateur */
    private int id;

    /** Nom du collaborateur */
    @Size(min = 2, message = "Le nom doit comporter au moins deux caractères")
    private String nom;

    /** Prénom du collaborateur */
    @Size(min = 2, message = "Le prénom doit comporter au moins deux caractères")
    private String prenom;

    /** Adresse du collaborateur */
    private String adresse;

    /** Adresse électronique du collaborateur */
    @Email(message = "Le format de l'email n'est pas valide")
    private String email;

    /** Numéro de téléphone du collaborateur */
    private String telephone;

    /** Pseudo du collaborateur */
    @NotBlank(message = "Le collaborateur doit avoir un pseudo")
    private String pseudo;

    /** Mot de passe du collaborateur */
    private String password;

    /** Statut administrateur de l'application */
    // false par défaut
    private Boolean admin = false;

    /** Véhicules lite dtos possédés par le collaborateur (pour organiser des covoiturages) */
    private List<VehiculeLiteDto> vehicules = new ArrayList<>();

    /** Constructeur vide */
    public CollaborateurDto() {}

    /**
     * Constructeur
     * @param nom nom
     * @param prenom prénom
     * @param adresse adresse
     * @param email adresse électronique
     * @param telephone numéro de téléphone
     * @param pseudo pseudo
     * @param password mot de passe
     * @param admin statut administrateur de l'application
     */
    public CollaborateurDto(String nom, String prenom, String adresse, String email, String telephone, String pseudo, String password, Boolean admin) {
        this(0, nom, prenom, adresse, email, telephone, pseudo, password, admin, new ArrayList<>());
    }

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
        this(id, nom, prenom, adresse, email, telephone, pseudo, password, admin, new ArrayList<>());
    }

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
     * @param vehicules véhicules personnels du collaborateur
     */
    public CollaborateurDto(int id, String nom, String prenom, String adresse, String email, String telephone, String pseudo, String password, Boolean admin, List<VehiculeLiteDto> vehicules) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.pseudo = pseudo;
        this.password = password;
        this.admin = admin;
        this.vehicules = vehicules;
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

    /**
     * Getter
     * @return véhicules lite dtos (véhicules personnels)
     */
    public List<VehiculeLiteDto> getVehicules() {
        return this.vehicules;
    }

    /**
     * Setter
     * @param vehicules véhicules lite dtos (véhicules personnels)
     */
    public void setVehicules(List<VehiculeLiteDto> vehicules) {
        this.vehicules = vehicules;
    }
}
