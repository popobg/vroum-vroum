package demo.vroum_vroum.dto;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.VehiculeService;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO de l'entité Reservation
 */
public class ReservationDto implements Serializable {
    /** Identifiant unique et non modifiable de la réservation */
    private int id;
    /** Date et heure du début de la réservation */
    private LocalDateTime dateDepart;
    /** Date et heure de la fin de la réservation */
    private LocalDateTime dateRetour;
    /** Collaborateurs de la réservation*/
    private Collaborateur collaborateur;
    /** Véhicule de service utilisé pour la réservation */
    private VehiculeService vehiculeService;

    /** Constructeur vide */
    public ReservationDto() {}

    /** Constructeur avec arguments */
    public ReservationDto(int id, LocalDateTime dateDepart, Collaborateur collaborateur, LocalDateTime dateRetour, VehiculeService vehiculeService) {
        this.id = id;
        this.dateDepart = dateDepart;
        this.collaborateur = collaborateur;
        this.dateRetour = dateRetour;
        this.vehiculeService = vehiculeService;
    }

    /**
     * Getter
     * @return Id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter
     * @return date-heure
     */
    public LocalDateTime getDateDepart() {
        return dateDepart;
    }

    /**
     * Setter
     * @param dateDepart date-heure
     */
    public void setDateDepart(LocalDateTime dateDepart) {
        this.dateDepart = dateDepart;
    }

    /**
     * Getter
     * @return date-heure
     */
    public LocalDateTime getDateRetour() {
        return dateRetour;
    }

    /**
     * Setter
     * @param dateRetour date-heure
     */
    public void setDateRetour(LocalDateTime dateRetour) {
        this.dateRetour = dateRetour;
    }

    /**
     * Getter
     * @return collaborateur
     */
    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    /**
     * Setter
     * @param collaborateur collaborateur
     */
    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    /**
     * Getter
     * @return vehiculeService
     */
    public VehiculeService getVehiculeService() {
        return vehiculeService;
    }

    /**
     * Setter
     * @param vehiculeService vehiculeService
     */
    public void setVehiculeService(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }
}
