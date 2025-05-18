package demo.vroum_vroum.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/**
 * Entité Adresse
 */
@Entity
@Table(name = "ADRESSE")
public class Adresse implements Serializable, Comparable<Adresse> {
    /** identifiant unique et non modifiable de l'adresse */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    /** Numéro de la rue */
    @Column(name = "NUMERO")
    private String numero;

    /** Libellé de la voie */
    @Column(name = "RUE")
    private String rue;

    /** Code postal associé à l'adresse */
    @Column(name = "CODE_POSTAL")
    private String codePostal;

    /** Ville associée à l'adresse */
    @Column(name = "VILLE")
    private String ville;

    /** Covoiturages possédant cette adresse comme point de départ */
    @OneToMany(mappedBy = "adresseDepart")
    private Set<Covoiturage> covoituragesDepart;

    /** Covoiturages possédant cette adresse comme point d'arrivée */
    @OneToMany(mappedBy = "adresseArrivee")
    private Set<Covoiturage> covoituragesArrivee;

    /**
     * Constructeur vide
     */
    public Adresse() {}

    /**
     * Constructeur Adresse
     * @param id identifiant unique de l'adresse
     * @param numero numéro de rue
     * @param rue rue
     * @param codePostal code postal
     * @param ville ville
     */
    public Adresse(int id, String numero, String rue, String codePostal, String ville) {
        this.id = id;
        this.numero = numero;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    /**
     * Vérifie si deux objets de la classe Adresse sont identiques.
     * @param o instance de l'objet à comparer
     * @return boolean, true si les objets sont identiques, sinon false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adresse autreAdresse)) return false;
        return id == autreAdresse.id && Objects.equals(numero, autreAdresse.numero)
                && Objects.equals(rue, autreAdresse.rue) && codePostal.equals(autreAdresse.codePostal)
                && ville.equals(autreAdresse.ville);
    }

    /**
     * Retourne le hashcode de l'objet Adresse.
     * @return entier
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, numero, rue, codePostal, ville);
    }

    /**
     * Retourne les informations générales liées à l'objet Adresse.
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(numero);
        sb.append(" ").append(rue);
        sb.append(", ").append(codePostal);
        sb.append(" ").append(ville);
        return sb.toString();
    }

    /**
     * Compare deux villes sur la base du nom de leur ville, puis du nom de la rue et enfin du numéro de la rue
     * et détermine leur ordre de tri.
     * @param autreAdresse l'adresse de comparaison
     * @return int, indiquant si l'adresse doit être classée avant ou après :
     *          0 = même classement, même nom
     *          1 = l'instance actuelle est supérieure à la ville de comparaison
     *          -1 = l'instance actuelle est inférieure à la ville de comparaison
     */
    @Override
    public int compareTo(Adresse autreAdresse) {
        // Tri par nom de ville
        int villeComparaison = this.ville.compareTo(autreAdresse.getVille());

        if (villeComparaison != 0) {
            return villeComparaison;
        }

        Collator collator = Collator.getInstance(Locale.FRANCE);

        // Si ville identique, tri par nom de rue
        int rueComparaison = collator.compare(this.rue.toLowerCase(), autreAdresse.rue.toLowerCase());
        if (rueComparaison != 0) {
            return rueComparaison;
        }

        // Si nom de rue identique, tri par numéro de rue
        return collator.compare(this.numero.toLowerCase(), autreAdresse.numero.toLowerCase());
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
     * @param id identifiant
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
     * @return rue
     */
    public String getRue() {
        return rue;
    }

    /**
     * Setter
     * @param rue rue
     */
    public void setRue(String rue) {
        this.rue = rue;
    }

    /**
     * Getter
     * @return code postal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * Setter
     * @param codePostal code postal
     */
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * Getter
     * @return ville
     */
    public String getVille() {
        return ville;
    }

    /**
     * Setter
     * @param ville ville
     */
    public void setVille(String ville) {
        this.ville = ville;
    }
}
