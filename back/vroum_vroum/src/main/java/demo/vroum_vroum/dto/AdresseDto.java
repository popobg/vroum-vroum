package demo.vroum_vroum.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO de l'entité Adresse
 */
public class AdresseDto {
    /** Identifiant unique et non modifiable */
    private int id;

    /** Numéro de rue (peut être null) */
    @Size(min = 1, max = 6, message = "Le numéro de rue doit contenir entre 1 et 6 caractères")
    private String numero;

    /** Libellé de la voie (peut être null) */
    @Size(min = 1, message = "La rue doit comporter au moins un caractère")
    private String rue;

    /** Code postal */
    @NotNull
    private int idCodePostal;

    @Size(min = 5, max = 5, message = "Le code postal doit comporter 5 caractères")
    private String codePostal;

    /** Nom de la ville */
    @NotNull
    private int idVille;

    private String nomVille;

    /** Constructeur vide */
    public AdresseDto() {}

    /**
     * Constructeur
     * @param id identifiant
     * @param numero numéro rue
     * @param rue libellé de la voie
     * @param idCodePostal Id code postal
     * @param codePostal code postal
     * @param idVille Id de la ville
     * @param nomVille nom de la ville
     */
    public AdresseDto(int id, String numero, String rue, int idCodePostal, String codePostal, int idVille, String nomVille) {
        this.id = id;
        this.numero = numero;
        this.rue = rue;
        this.idCodePostal = idCodePostal;
        this.idVille = idVille;
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
     * @return numéro
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Setter
     * @param numero numéro
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Getter
     * @return libellé voie
     */
    public String getRue() {
        return rue;
    }

    /**
     * Setter
     * @param rue libellé voie
     */
    public void setRue(String rue) {
        this.rue = rue;
    }

    /**
     * Getter
     * @return Id code postal
     */
    public int getIdCodePostal() {
        return idCodePostal;
    }

    /**
     * Setter
     * @param idCodePostal Id code postal
     */
    public void setIdCodePostal(int idCodePostal) {
        this.idCodePostal = idCodePostal;
    }

    /**
     * Getter
     * @return Code postal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * Setter
     * @param codePostal Code postal
     */
    public void setIdCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * Getter
     * @return Id ville
     */
    public int getIdVille() {
        return idVille;
    }

    /**
     * Setter
     * @param idVille Id ville
     */
    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }

    /**
     * Getter
     * @return Nom ville
     */
    public String getNomVille() {
        return nomVille;
    }

    /**
     * Setter
     * @param nomVille Nom ville
     */
    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
    }
}
