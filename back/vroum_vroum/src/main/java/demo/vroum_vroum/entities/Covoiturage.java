package demo.vroum_vroum.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    @ManyToOne
    @JoinColumn(name = "ID_ADRS_DEPART")
    private Adresse adresseDepart;

    /** Adresse d'arrivée du covoiturage */
    @ManyToOne
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
    private Set<Collaborateur> collaborateurs;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Adresse getAdresseDepart() {
        return adresseDepart;
    }

    public void setAdresseDepart(Adresse adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    public Adresse getAdresseArrivee() {
        return adresseArrivee;
    }

    public void setAdresseArrivee(Adresse adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Collaborateur getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(Collaborateur organisateur) {
        this.organisateur = organisateur;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Set<Collaborateur> getCollaborateurs() {
        return collaborateurs;
    }

    public void setCollaborateurs(Set<Collaborateur> Collaborateurs) {
        this.collaborateurs = Collaborateurs;
    }

    @Override
    public String toString() {
        return "Covoiturage{" +
                "id=" + id +
                ", date=" + date +
                ", adresseDepart='" + adresseDepart + '\'' +
                ", adresseArrivee='" + adresseArrivee + '\'' +
                ", nbPlaces=" + nbPlaces +
                ", distance='" + distance + '\'' +
                ", duree=" + duree +
                '}';
    }
}
