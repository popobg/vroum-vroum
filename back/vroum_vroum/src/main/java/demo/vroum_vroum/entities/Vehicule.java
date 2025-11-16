package demo.vroum_vroum.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entité Véhicule = véhicule personnel d'un collaborateur
 */
@Entity
@Table(name = "VEHICULE")
public class Vehicule implements Serializable {
    /** Identifiant du véhicule */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    /** Immatriculation du véhicule */
    @Column(name = "IMMATRICULATION")
    private String immatriculation;

    /** Marque du véhicule */
    @Column(name = "MARQUE")
    private String marque;

    /** Modèle du véhicule */
    @Column(name = "MODELE")
    private String modele;

    /** Nombre de places assises dans le véhicule */
    @Column(name = "NB_PLACES")
    private int nbPlaces;

    /** Propriétaire du véhicule */
    @ManyToOne
    @JoinColumn(name = "ID_COLLABORATEUR")
    @JsonBackReference
    private Collaborateur collaborateur;

    /**
     * Constructeur vide
     */
    public Vehicule() {}

    /**
     * Constructeur
     * @param nbPlaces nombre de places
     * @param modele modèle du véhicule
     * @param marque marque du véhicule
     * @param immatriculation immatriculation du véhicule
     */
    public Vehicule(int nbPlaces, String modele, String marque, String immatriculation) {
        this(0, nbPlaces, modele, marque, immatriculation, null);
    }

    /**
     * Constructeur
     * @param id identifiant du véhicule en BDD
     * @param nbPlaces nombre de places
     * @param modele modèle du véhicule
     * @param marque marque du véhicule
     * @param immatriculation immatriculation du véhicule
     */
    public Vehicule(int id, int nbPlaces, String modele, String marque, String immatriculation) {
        this(id, nbPlaces, modele, marque, immatriculation, null);
    }

    /**
     * Constructeur
     * @param id identifiant du véhicule en BDD
     * @param nbPlaces nombre de places
     * @param modele modèle du véhicule
     * @param marque marque du véhicule
     * @param immatriculation immatriculation du véhicule
     * @param collaborateur collaborateur propriétaire du véhicule
     */
    public Vehicule(int id, int nbPlaces, String modele, String marque, String immatriculation, Collaborateur collaborateur) {
        this.id = id;
        this.nbPlaces = nbPlaces;
        this.modele = modele;
        this.marque = marque;
        this.immatriculation = immatriculation;
        this.collaborateur = collaborateur;
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
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter
     * @return immatriculation
     */
    public String getImmatriculation() {
        return immatriculation;
    }

    /**
     * Setter
     * @param immatriculation immatriculation
     */
    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    /**
     * Getter
     * @return marque
     */
    public String getMarque() {
        return marque;
    }

    /**
     * Setter
     * @param marque marque
     */
    public void setMarque(String marque) {
        this.marque = marque;
    }

    /**
     * Getter
     * @return modèle
     */
    public String getModele() {
        return modele;
    }

    /**
     * Setter
     * @param modele modèle
     */
    public void setModele(String modele) {
        this.modele = modele;
    }

    /**
     * Getter
     * @return nombre de places
     */
    public int getNbPlaces() {
        return nbPlaces;
    }

    /**
     * Setter
     * @param nbPlaces nombre de places
     */
    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    /**
     * Getter
     * @return collaborateur, propriétaire du véhicule
     */
    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    /**
     * Setter
     * @param collaborateur collaborateur, propriétaire du véhicule
     */
    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    /**
     * Méthode générant une chaîne de caractères à partir des attributs du vehicule.
     * @return String
     */
    @Override
    public String toString() {
        return "Vehicule{" +
                "id=" + id +
                ", immatriculation='" + immatriculation + '\'' +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", nbPlaces=" + nbPlaces +
                ", collaborateur=" + collaborateur +
                '}';
    }

    /**
     * Vérifie si deux objets de la classe Vehicule sont identiques.
     * @param o instance de l'objet à comparer
     * @return boolean, true si les objets sont identiques, sinon false
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicule autreVehicule)) return false;

        return this.id == autreVehicule.id
        && this.nbPlaces == autreVehicule.nbPlaces
        && Objects.equals(this.immatriculation, autreVehicule.immatriculation)
        && Objects.equals(this.marque, autreVehicule.marque)
        && Objects.equals(this.modele, autreVehicule.modele);
    }

    /**
     * Retourne le hashcode de l'objet Vehicule.
     * @return entier
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.nbPlaces, this.immatriculation, this.marque, this.modele);
    }
}
