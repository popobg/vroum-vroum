package demo.vroum_vroum.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "VEHICULE")
public class Vehicule implements Serializable {
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
    @Column(name = "NB_PLACES")
    private int nbPlaces;
    @ManyToOne
    @JoinColumn(name = "ID_COLLABORATEUR")
    private Collaborateur collaborateur;

    public Vehicule() {}

    public Vehicule(int id, int nbPlaces, String modele, String marque, String immatriculation) {
        this.id = id;
        this.nbPlaces = nbPlaces;
        this.modele = modele;
        this.marque = marque;
        this.immatriculation = immatriculation;
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

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public void setCollaborateur(Collaborateur collaborateur) {
        this.collaborateur = collaborateur;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "id=" + id +
                ", immatriculation='" + immatriculation + '\'' +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", nbPlaces=" + nbPlaces +
                '}';
    }
}
