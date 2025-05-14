package demo.vroum_vroum.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "VILLE")
public class Ville implements Serializable, Comparable<Ville> {
    /** identifiant unique et non modifiable de la ville */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    /** Nom de la ville */
    @Column(name="NOM")
    private String nom;

    /** Code postal de la ville */
    @OneToMany(mappedBy = "ville")
    private Set<CodePostal> codesPostaux;

    /**
     * Constructeur vide
     */
    public Ville() {
    }

    /**
     * Constructeur
     * @param nom nom de la ville
     */
    public Ville(String nom) {
        this(0, nom);
    }

    /**
     * Constructeur
     * @param id identifiant unique de la ville
     * @param nom nom de la ville
     */
    public Ville(int id, String nom) {
        this(0, nom, null, null);
    }

    /**
     * Constructeur
     * @param id identifiant unique
     * @param nom nom de la ville
     * @param codePostaux codes postaux rattachés à la ville
     * @param adresses adresses rattachées à la ville
     */
    public Ville(long id, String nom, Set<CodePostal> codePostaux, Set<Adresse> adresses) {
        this.id = id;
        this.nom = nom;
        this.codesPostaux = codePostaux;
    }



    /**
     * Vérifie si deux objets de la classe Ville sont identiques.
     * @param o instance de l'objet à comparer
     * @return boolean, true si les objets sont identiques, sinon false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ville ville)) return false;
        return id == ville.id && Objects.equals(nom, ville.nom);
    }

    /**
     * Retourne le hashcode de l'objet Ville.
     * @return entier
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, nom);
    }

    /**
     * Retourne les informations générales liées à l'objet Ville.
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ville : ");
        sb.append("id = ").append(id);
        sb.append(", nom = \"").append(nom).append('"');
        sb.append(";\n");
        return sb.toString();
    }

    /**
     * Compare deux villes sur la base de leur nom et détermine leur ordre.
     * @param autreVille la ville de comparaison
     * @return int, indiquant si la ville doit être classée avant ou après :
     *          0 = même classement, même nom
     *          1 = l'instance actuelle est supérieure à la ville de comparaison
     *          -1 = l'instance actuelle est inférieure à la ville de comparaison
     */
    // Critère de tri : nom de la ville (sans tenir compte de la casse)
    @Override
    public int compareTo(Ville autreVille) {
        Collator collator = Collator.getInstance(Locale.FRANCE);
        return collator.compare(this.nom.toLowerCase(), autreVille.getNom().toLowerCase());
    }

    /**
     * Getter
     * @return id
     */
    public long getId() {
        return id;
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
     * @return code postaux
     */
    public Set<CodePostal> getCodesPostaux() {
        return codesPostaux;
    }

    /**
     * Setter
     * @param codePostaux codes postaux
     */
    public void setCodesPostaux(Set<CodePostal> codePostaux) {
        this.codesPostaux = codePostaux;
    }
}
