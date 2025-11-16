package demo.vroum_vroum.entities;

import demo.vroum_vroum.enums.Categorie;
import demo.vroum_vroum.enums.StatutVehicule;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Entité Véhicule de service
 */
@Entity
@Table(name = "VEHICULE_SERVICE")
public class VehiculeService implements Serializable {
    /** Identifiant du véhicule de service */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    /** Immatriculation du véhicule de service */
    @Column(name = "IMMATRICULATION")
    private String immatriculation;

    /** Marque du véhicule de service */
    @Column(name = "MARQUE")
    private String marque;

    /** Modèle du véhicule de service */
    @Column(name = "MODELE")
    private String modele;

    /** Catégorie du véhicule de service */
    @Column(name = "CATEGORIE")
    @Enumerated(EnumType.STRING)
    private Categorie categorie;

    /** Photo du véhicule de service */
    @Column(name = "PHOTO")
    private int photo;

    /** Motorisation du véhicule de service */
    @Column(name = "MOTORISATION")
    private int motorisation;

    /** Co2/km du véhicule de service */
    @Column(name = "CO2_Km")
    private int CO2parKm;

    /** Nombre de places assises dans le véhicule de service (conducteur compris) */
    @Column(name = "NB_PLACES")
    private int nbPlaces;

    /** Statut de disponibilité du véhicule */
    @Column(name = "STATUT")
    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;

    /** Réservations effectuées pour ce véhicule */
    @OneToMany(mappedBy = "vehiculeService")
    private List<Reservation> reservations = new ArrayList<>();

    /** Administrateurs du véhicule */
    @ManyToMany(mappedBy = "vehiculesService")
    private Set<Collaborateur> collaborateurs = new HashSet<>();

    /**
     * Constructeur
     */
    public VehiculeService() {}

    /**
     * Constructeur
     * @param id identifiant
     * @param immatriculation immatriculation
     * @param marque marque
     * @param modele modèle
     * @param categorie catégorie de véhicule
     * @param photo photo
     * @param motorisation motorisation
     * @param CO2parKm CO2/km
     * @param nbPlaces nombre de places
     * @param statut statut du véhicule
     */
    public VehiculeService(int id, String immatriculation, String marque, String modele, Categorie categorie, int photo, int motorisation, int CO2parKm, int nbPlaces, StatutVehicule statut) {
        this.id = id;
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.categorie = categorie;
        this.photo = photo;
        this.motorisation = motorisation;
        this.CO2parKm = CO2parKm;
        this.nbPlaces = nbPlaces;
        this.statut = statut;
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
     * @return modele
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
     * @return catégorie
     */
    public Categorie getCategorie() {
        return categorie;
    }

    /**
     * Setter
     * @param categorie catégorie
     */
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    /**
     * Getter
     * @return photo
     */
    public int getPhoto() {
        return photo;
    }

    /**
     * Setter
     * @param photo photo
     */
    public void setPhoto(int photo) {
        this.photo = photo;
    }

    /**
     * Getter
     * @return motorisation
     */
    public int getMotorisation() {
        return motorisation;
    }

    /**
     * Setter
     * @param motorisation motorisation
     */
    public void setMotorisation(int motorisation) {
        this.motorisation = motorisation;
    }

    /**
     * Getter
     * @return CO2/km
     */
    public int getCO2parKm() {
        return CO2parKm;
    }

    /**
     * Setter
     * @param CO2parKm CO2/km
     */
    public void setCO2parKm(int CO2parKm) {
        this.CO2parKm = CO2parKm;
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
     * @return statut du véhicule
     */
    public StatutVehicule getStatut() {
        return statut;
    }

    /**
     * Setter
     * @param statut statut du véhicule
     */
    public void setStatut(StatutVehicule statut) {
        this.statut = statut;
    }

    /**
     * Getter
     * @return réservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Setter
     * @param reservations réservations
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Getter
     * @return collaborateurs administrateurs du véhicule
     */
    public Set<Collaborateur> getCollaborateurs() {
        return collaborateurs;
    }

    /**
     * Setter
     * @param collaborateurs collaborateurs administrateurs du véhicule
     */
    public void setCollaborateurs(Set<Collaborateur> collaborateurs) {
        this.collaborateurs = collaborateurs;
    }

    /**
     * Méthode générant une chaîne de caractères à partir des attributs du véhicule de service.
     * @return String
     */
    @Override
    public String toString() {
        return "VehiculeService{" +
                "id=" + id +
                ", immatriculation='" + immatriculation + '\'' +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", categorie='" + categorie + '\'' +
                ", photo=" + photo +
                ", motorisation=" + motorisation +
                ", CO2parKm=" + CO2parKm +
                ", nbPlaces=" + nbPlaces +
                ", statut='" + statut + '\'' +
                '}';
    }

    /**
     * Vérifie si deux objets de la classe VehiculeService sont identiques.
     * @param o instance de l'objet à comparer
     * @return boolean, true si les objets sont identiques, sinon false
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehiculeService autreVS)) return false;

        return this.id == autreVS.id
        && Objects.equals(this.immatriculation, autreVS.immatriculation)
        && Objects.equals(this.marque, autreVS.marque)
        && Objects.equals(this.modele, autreVS.modele)
        && Objects.equals(this.categorie, autreVS.categorie)
        && Objects.equals(this.motorisation, autreVS.motorisation)
        && this.CO2parKm == autreVS.CO2parKm
        && this.nbPlaces == autreVS.nbPlaces
        && Objects.equals(this.statut, autreVS.statut);
    }

    /**
     * Retourne le hashcode de l'objet VehiculeService.
     * @return entier
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.nbPlaces, this.immatriculation, this.marque, this.modele, this.categorie, this.motorisation, this.CO2parKm, this.statut);
    }
}
