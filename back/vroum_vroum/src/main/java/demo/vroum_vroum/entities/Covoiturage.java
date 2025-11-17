package demo.vroum_vroum.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entité Covoiturage
 */
@Entity
@Table(name = "COVOITURAGE")
public class Covoiturage implements Serializable {
    /** Identifiant unique et non modifiable d'un covoiturage */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    /** Date d'un covoiturage */
    @Column(name = "DATE")
    private LocalDateTime date;

    /** Adresse de départ du covoiturage */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_ADRS_DEPART")
    private Adresse adresseDepart;

    /** Adresse d'arrivée du covoiturage */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_ADRS_ARRIVEE")
    private Adresse adresseArrivee;

    /** Nombre de places passagers du covoiturage */
    @Column(name = "NB_PLACES")
    private int nbPlaces;

    /** Distance du trajet (en kilomètres) */
    @Column(name = "DISTANCE")
    private int distance;

    /** Durée estimée du trajet (en secondes) */
    @Column(name = "DUREE")
    private int duree;

    /** Collaborateur organisant le covoiturage (conducteur) */
    @ManyToOne
    @JoinColumn(name = "ID_COLLABORATEUR")
    private Collaborateur organisateur;

    /** Véhicule utilisé par l'organisateur */
    @ManyToOne
    @JoinColumn(name = "ID_VEHICULE")
    private Vehicule vehicule;

    /** Collaborateur(s) passager(s) du covoiturage */
    @ManyToMany(mappedBy = "covoiturages")
    private Set<Collaborateur> collaborateurs = new HashSet<>();

    /**
     * Constructeur vide
     */
    public Covoiturage() {}

    /**
     * Constructeur
     * @param id identifiant du covoiturage
     * @param duree durée estimée du covoiturage
     * @param distance distance du covoiturage
     * @param nbPlaces nombre de places passagers
     * @param adresseArrivee adresse d'arrivée
     * @param adresseDepart adresse de départ
     * @param date date et heure du départ
     */
    public Covoiturage(int id, int duree, int distance, int nbPlaces, Adresse adresseArrivee, Adresse adresseDepart, LocalDateTime date) {
        this.id = id;
        this.duree = duree;
        this.distance = distance;
        this.nbPlaces = nbPlaces;
        this.adresseArrivee = adresseArrivee;
        this.adresseDepart = adresseDepart;
        this.date = date;
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
     * @return date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Setter
     * @param date date
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Getter
     * @return adresse de départ
     */
    public Adresse getAdresseDepart() {
        return adresseDepart;
    }

    /**
     * Setter
     * @param adresseDepart adresse de départ
     */
    public void setAdresseDepart(Adresse adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    /**
     * Getter
     * @return adresse d'arrivée
     */
    public Adresse getAdresseArrivee() {
        return adresseArrivee;
    }

    /**
     * Setter
     * @param adresseArrivee adresse d'arrivée
     */
    public void setAdresseArrivee(Adresse adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
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
     * @return distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Setter
     * @param distance distance
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Getter
     * @return durée
     */
    public int getDuree() {
        return duree;
    }

    /**
     * Setter
     * @param duree durée
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }

    /**
     * Getter
     * @return collaborateur organisateur du covoiturage
     */
    public Collaborateur getOrganisateur() {
        return organisateur;
    }

    /**
     * Setter
     * @param organisateur collaborateur organisateur du covoiturage
     */
    public void setOrganisateur(Collaborateur organisateur) {
        this.organisateur = organisateur;
    }

    /**
     * Getter
     * @return véhicule utilisé par le collaborateur
     */
    public Vehicule getVehicule() {
        return vehicule;
    }

    /**
     * Setter
     * @param vehicule véhicule utilisé par le collaborateur
     */
    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    /**
     * Getter
     * @return collaborateurs passagers du covoiturage
     */
    public Set<Collaborateur> getCollaborateurs() {
        return collaborateurs;
    }

    /**
     * Setter
     * @param Collaborateurs collaborateurs passagers du covoiturage
     */
    public void setCollaborateurs(Set<Collaborateur> Collaborateurs) {
        this.collaborateurs = Collaborateurs;
    }

    /**
     * Méthode générant une chaîne de caractères à partir des attributs du covoiturage.
     * @return String
     */
    @Override
    public String toString() {
        return "Covoiturage{" +
                "id=" + id +
                ", date=" + date +
                ", adresseDepart=" + (adresseDepart != null ? adresseDepart : "null") +
                ", adresseArrivee=" + (adresseArrivee != null ? adresseArrivee : "null") +
                ", nbPlaces=" + nbPlaces +
                ", distance=" + distance +
                ", duree=" + duree +
                '}';
    }

    /**
     * Vérifie si deux objets de la classe Covoiturage sont identiques.
     * @param o instance de l'objet à comparer
     * @return boolean, true si les objets sont identiques, sinon false
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Covoiturage autreCovoit)) return false;

        return this.id == autreCovoit.id
        && this.distance == autreCovoit.distance
        && this.duree == autreCovoit.duree
        && this.nbPlaces == autreCovoit.nbPlaces
        && Objects.equals(this.date, autreCovoit.date);
    }

    /**
     * Retourne le hashcode de l'objet Covoiturage.
     * @return entier
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.nbPlaces, this.distance, this.duree, this.date, this.adresseArrivee, this.adresseDepart);
    }
}
