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

    @NotNull
    @Size(min = 5, max = 5, message = "Le code postal doit comporter 5 caractères")
    private String codePostal;

    @NotNull
    private String nomVille;

    /** Constructeur vide */
    public AdresseDto() {}

    /**
     * Constructeur
     * @param id identifiant
     * @param numero numéro rue
     * @param rue libellé de la voie
     * @param codePostal code postal
     * @param nomVille nom de la ville
     */
    public AdresseDto(int id, String numero, String rue, String codePostal, String nomVille) {
        this.id = id;
        this.numero = numero;
        this.rue = rue;
        this.codePostal = codePostal;
        this.nomVille = nomVille;
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
