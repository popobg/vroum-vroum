package demo.vroum_vroum.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "COVOITURAGE")
public class Covoiturage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "DATE")
    private Date date;
    @Column(name = "ADRS_DEPART")
    private String adresseDepart;
    @Column(name = "ADRS_ARRIVEE")
    private String adresseArrivee;
    @Column(name = "NB_PLACES")
    private int nbPlaces;
    @Column(name = "DISTANCE")
    private String distance;
    @Column(name = "DUREE")
    private int duree;
    @ManyToOne
    @JoinColumn(name = "ID_COLLABORATEUR")
    private Collaborateur organisateur;
    @ManyToOne
    @JoinColumn(name = "ID_VEHICULE")
    private Vehicule vehicule;
    @ManyToMany(mappedBy = "covoiturages")
    private Set<Collaborateur> collaborateurs;

    public Covoiturage() {}

    public Covoiturage(int id, int duree, String distance, int nbPlaces, String adresseArrivee, String adresseDepart, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAdresseDepart() {
        return adresseDepart;
    }

    public void setAdresseDepart(String adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    public String getAdresseArrivee() {
        return adresseArrivee;
    }

    public void setAdresseArrivee(String adresseArrivee) {
        this.adresseArrivee = adresseArrivee;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
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
