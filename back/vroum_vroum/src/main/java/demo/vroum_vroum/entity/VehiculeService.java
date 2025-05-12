package demo.vroum_vroum.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "VEHICULE_SERVICE")
public class VehiculeService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "IMMATRICULATION")
    private String immatriculation;
    @Column(name = "MARQUE")
    private String marque;
    @Column(name = "MODELE")
    private String modele;
    @Column(name = "CATEGORIE")
    private String categorie;
    @Column(name = "PHOTO")
    private int photo;
    @Column(name = "MOTORISATION")
    private int motorisation;
    @Column(name = "CO2_Km")
    private int CO2parKm;
    @Column(name = "NB_PLACES")
    private int nbPlaces;
    @Column(name = "STATUT")
    private String statut;
    @OneToMany(mappedBy = "vehiculeService")
    private List<Reservation> reservations;
    @ManyToMany(mappedBy = "vehiculeService")
    private Set<Colaborateur> colaborateurs;

    {
        reservations = new ArrayList<>();
    }

    public VehiculeService() {}

    public VehiculeService(int id, String immatriculation, String marque, String modele, String categorie, int photo, int motorisation, int CO2parKm, int nbPlaces, String statut) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getMotorisation() {
        return motorisation;
    }

    public void setMotorisation(int motorisation) {
        this.motorisation = motorisation;
    }

    public int getCO2parKm() {
        return CO2parKm;
    }

    public void setCO2parKm(int CO2parKm) {
        this.CO2parKm = CO2parKm;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Colaborateur> getColaborateurs() {
        return colaborateurs;
    }

    public void setColaborateurs(Set<Colaborateur> colaborateurs) {
        this.colaborateurs = colaborateurs;
    }

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
}
