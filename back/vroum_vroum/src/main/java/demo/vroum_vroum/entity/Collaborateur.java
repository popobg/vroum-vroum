package demo.vroum_vroum.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "COLLABORATEUR")
public class Collaborateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "NOM")
    private String nom;
    @Column(name = "PRENOM")
    private String prenom;
    @Column(name = "ADRESSE")
    private String adresse;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TELEPHONE")
    private int telephone;
    @Column(name = "PSEUDO")
    private String pseudo;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ADMIN")
    private Boolean admin;
    @OneToMany(mappedBy = "collaborateur")
    private List<Vehicule> vehicules;
    @ManyToMany
    @JoinTable(name = "PASSAGER_COVOITURAGE", joinColumns = @JoinColumn(name="ID_PASSAGER", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name="ID_COVOITURAGE", referencedColumnName = "ID"))
    private Set<Covoiturage> covoiturages;
    @OneToMany(mappedBy = "collaborateur")
    private List<Reservation> reservations;
    @ManyToMany
    @JoinTable(name = "ADMINISTRATEUR_VEHICULE_SERVICE", joinColumns = @JoinColumn(name = "ID_ADMINISTARTEUR", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_VEHICULE_SEVICE", referencedColumnName = "ID"))
    private Set<VehiculeService> vehiculeServices;

    {
        vehicules = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    public Collaborateur() {}

    public Collaborateur(int id, String nom, String prenom, String adresse, String email, int telephone, String pseudo, String password, Boolean admin) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.pseudo = pseudo;
        this.password = password;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public Set<Covoiturage> getCovoiturages() {
        return covoiturages;
    }

    public void setCovoiturages(Set<Covoiturage> covoiturages) {
        this.covoiturages = covoiturages;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<VehiculeService> getVehiculeServices() {
        return vehiculeServices;
    }

    public void setVehiculeServices(Set<VehiculeService> vehiculeServices) {
        this.vehiculeServices = vehiculeServices;
    }

    @Override
    public String toString() {
        return "Collaborateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", telephone=" + telephone +
                ", pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }
}
