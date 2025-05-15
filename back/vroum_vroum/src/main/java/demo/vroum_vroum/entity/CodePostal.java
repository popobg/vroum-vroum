package demo.vroum_vroum.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CODE_POSTAL")
public class CodePostal implements Serializable, Comparable<CodePostal> {
    /** identifiant unique et non modifiable de la ville */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    /** Code postal */
    @Column(name="CODE")
    private String code;

    /** Villes associées au code postal */
    @ManyToMany(mappedBy = "codesPostaux")
    private Set<Ville> villes;

    /** Adresses associées à ce code postal */
    @OneToMany(mappedBy = "codePostal")
    private Set<Adresse> adresses;

    /**
     * Constructeur vide
     */
    public CodePostal() {
    }

    /**
     * Constructeur
     * @param code code postal
     * @param villes villes associées
     */
    public CodePostal(String code, Set<Ville> villes) {
        this(0L, code, villes);
    }

    /**
     * Constructeur
     * @param id identifiant de la ville
     * @param code code postal
     * @param villes villes associées
     */
    public CodePostal(long id, String code, Set<Ville> villes) {
        this.id = id;
        this.code = code;
        this.villes = villes;
    }

    /**
     * Vérifie si deux objets de la classe CodePostal sont identiques.
     * @param o instance de l'objet à comparer
     * @return boolean, true si les objets sont identiques, sinon false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodePostal cp)) return false;
        return id == cp.id && Objects.equals(code, cp.code);
    }

    /**
     * Retourne le hashcode de l'objet CodePostal.
     * @return entier
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }

    /**
     * Retourne les informations générales liées à l'objet CodePostal.
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Code postal : ");
        sb.append("id = ").append(id);
        sb.append(", code = \"").append(code).append('"');
        sb.append(";\n");
        return sb.toString();
    }

    /**
     * Compare deux codes postaux sur la base de leur code et détermine leur ordre.
     * @param autreCp le code postal de comparaison
     * @return int, indiquant si le code postal doit être classé avant ou après :
     *          0 = même classement, même code
     *          1 = l'instance actuelle est supérieure au code postal de comparaison
     *          -1 = l'instance actuelle est inférieure au code postal de comparaison
     */
    @Override
    public int compareTo(CodePostal autreCp) {
        Collator collator = Collator.getInstance(Locale.FRANCE);
        return collator.compare(this.code.toLowerCase(), autreCp.getCode().toLowerCase());
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
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter
     * @param code code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter
     * @return villes
     */
    public Set<Ville> getVilles() {
        return villes;
    }

    /**
     * Setter
     * @param villes villes
     */
    public void setVille(Set<Ville> villes) {
        this.villes = villes;
    }
}
