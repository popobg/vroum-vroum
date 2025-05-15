package demo.vroum_vroum.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO de l'entité Adresse
 */
public class AdresseDto {
    private int id;

    @Size(min = 1, max = 6, message = "Le numéro de rue doit contenir entre 1 et 6 caractères")
    private String numero;

    @Size(min = 1, message = "La rue doit comporter au moins un caractère")
    private String rue;

    @NotNull
    @Size(min = 5, max = 5, message = "Le code postal doit comporter 5 caractères")
    private String codePostal;

    @NotNull
    private String nomVille;
}
