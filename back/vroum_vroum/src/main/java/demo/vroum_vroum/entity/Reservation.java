package demo.vroum_vroum.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RESERVATION")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "DATE_DEPART")
    private Date dateDepart;
    @Column(name = "DATE_RETOUR")
    private Date dateRetour;
    @ManyToOne
    @JoinColumn(name = "ID_COLLABORATEUR")
    private Collaborateur collaborateur;
    @ManyToOne
    @JoinColumn(name = "ID_VEHICULE_SERVICE")
    private VehiculeService vehiculeService;

    public Reservation() {}

    public Reservation(int id, Date dateDepart, Date dateRetour) {
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

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
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
