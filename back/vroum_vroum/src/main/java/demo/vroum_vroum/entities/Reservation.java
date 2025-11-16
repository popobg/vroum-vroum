package demo.vroum_vroum.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entité Reservation = réservation d'un véhicule de service par un collaborateur
 */
@Entity
@Table(name = "RESERVATION")
public class Reservation implements Serializable {
    /** Identifiant de la réservation */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    /** Date-heure de début de la réservation */
    @Column(name = "DATE_DEPART")
    private LocalDateTime dateDepart;

    /** Date-heure de fin de la réservation */
    @Column(name = "DATE_RETOUR")
    private LocalDateTime dateRetour;

    /** Collaborateur ayant réservé le véhicule de service */
    @ManyToOne
    @JoinColumn(name = "ID_COLLABORATEUR")
    private Collaborateur collaborateur;

    /** Véhicule de service concerné par la réservation */
    @ManyToOne
    @JoinColumn(name = "ID_VEHICULE_SERVICE")
    private VehiculeService vehiculeService;

    /**
     * Constructeur vide
     */
    public Reservation() {}

    /**
     * Constructeur
     * @param id identifiant
     * @param dateDepart date de départ
     * @param dateRetour date de retour
     */
    public Reservation(int id, LocalDateTime dateDepart, LocalDateTime dateRetour) {
        this.id = id;
        this.dateDepart = dateDepart;
        this.dateRetour = dateRetour;
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
     * @return date de début
     */
    public LocalDateTime getDateDepart() {
        return dateDepart;
    }

    /**
     * Setter
     * @param dateDepart date de début
     */
    public void setDateDepart(LocalDateTime dateDepart) {
        this.dateDepart = dateDepart;
    }

    /**
     * Getter
     * @return date de fin
     */
    public LocalDateTime getDateRetour() {
        return dateRetour;
    }

    /**
     * Setter
     * @param dateRetour date de fin
     */
    public void setDateRetour(LocalDateTime dateRetour) {
        this.dateRetour = dateRetour;
    }

    /**
     * Getter
     * @return collaborateur ayant réservé
     */
    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    /**
     * Setter
     * @param collaborateur collaborateur ayant réservé
     */
    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    /**
     * Getter
     * @return véhicule de service réservé
     */
    public VehiculeService getVehiculeService() {
        return vehiculeService;
    }

    /**
     * Setter
     * @param vehiculeService véhicule de service réservé
     */
    public void setVehiculeService(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    /**
     * Méthode générant une chaîne de caractères à partir des attributs de la réservation.
     * @return String
     */
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", dateDepart=" + dateDepart +
                ", dateRetour=" + dateRetour +
                '}';
    }
}
