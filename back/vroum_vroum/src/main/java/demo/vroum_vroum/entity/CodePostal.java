package demo.vroum_vroum.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import java.util.Objects;

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

    @ManyToOne
    @JoinColumn(name = "ID_VILLE")
    private Ville ville;

    /**
     * Constructeur vide
     */
    public CodePostal() {
    }

    /**
     * Constructeur
     * @param code code postal
     * @param ville ville associée
     */
    public CodePostal(String code, Ville ville) {
        this(0L, code, ville);
    }

    /**
     * Constructeur
     * @param id identifiant de la ville
     * @param code code postal
     * @param ville ville associée
     */
    public CodePostal(long id, String code, Ville ville) {
        this.id = id;
        this.code = code;
        this.ville = ville;
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
     * @return ville
     */
    public Ville getVille() {
        return ville;
    }

    /**
     * Setter
     * @param ville ville
     */
    public void setVille(Ville ville) {
        this.ville = ville;
    }
}
