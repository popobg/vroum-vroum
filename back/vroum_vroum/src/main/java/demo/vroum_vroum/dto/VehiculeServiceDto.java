package demo.vroum_vroum.dto;

import demo.vroum_vroum.entities.Reservation;
import demo.vroum_vroum.enums.Categorie;
import demo.vroum_vroum.enums.StatutVehicule;

import java.util.List;

public class VehiculeServiceDto {
    private String immatriculation;
    private String marque;
    private String modele;
    private Categorie categorie;
    private int photo;
    private int motorisation;
    private int CO2parKm;
    private int nbPlaces;
    private StatutVehicule statut;
    private List<Reservation> reservations;

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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
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

    public StatutVehicule getStatut() {
        return statut;
    }

    public void setStatut(StatutVehicule statut) {
        this.statut = statut;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
