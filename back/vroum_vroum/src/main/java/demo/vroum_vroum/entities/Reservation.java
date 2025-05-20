package demo.vroum_vroum.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "RESERVATION")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "DATE_DEPART")
    private LocalDateTime dateDepart;
    @Column(name = "DATE_RETOUR")
    private LocalDateTime dateRetour;
    @ManyToOne
    @JoinColumn(name = "ID_COLLABORATEUR")
    private Collaborateur collaborateur;
    @ManyToOne
    @JoinColumn(name = "ID_VEHICULE_SERVICE")
    private VehiculeService vehiculeService;

    public Reservation() {}

    public Reservation(int id, LocalDateTime dateDepart, LocalDateTime dateRetour) {
        this.id = id;
        this.dateDepart = dateDepart;
        this.dateRetour = dateRetour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDateTime dateDepart) {
        this.dateDepart = dateDepart;
    }

    public LocalDateTime getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDateTime dateRetour) {
        this.dateRetour = dateRetour;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    public VehiculeService getVehiculeService() {
        return vehiculeService;
    }

    public void setVehiculeService(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", dateDepart=" + dateDepart +
                ", dateRetour=" + dateRetour +
                '}';
    }
}
