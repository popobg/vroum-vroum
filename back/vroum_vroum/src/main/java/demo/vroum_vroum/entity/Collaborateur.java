package demo.vroum_vroum.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "COLLABORATEUR")
public class Collaborateur implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NOM")
    private String nom;

    @Column(name = "PRENOM")
    private String prenom;

    @ManyToOne
    @JoinColumn(name = "ID_ADRESSE")
    private Adresse adresse;

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

    public Collaborateur() {}

    public Collaborateur(int id, String nom, String prenom, Adresse adresse, String email, int telephone, String pseudo, String password, Boolean admin) {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> admin ? "ROLE_ADMIN" : "ROLE_USER");
    }

    @Override
    public String getUsername() {
        return pseudo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
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
                ", admin=" + admin +
                '}';
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Setter
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    /**
     * Setter
     *
     * @param nom nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter
     *
     * @param prenom prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Setter
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelephone() {
        return telephone;
    }

    /**
     * Setter
     *
     * @param telephone telephone
     */
    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getPseudo() {
        return pseudo;
    }

    /**
     * Setter
     *
     * @param pseudo pseudo
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Boolean getAdmin() {
        return admin;
    }

    /**
     * Setter
     *
     * @param admin admin
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    /**
     * Setter
     *
     * @param vehicules vehicules
     */
    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public Set<Covoiturage> getCovoiturages() {
        return covoiturages;
    }

    /**
     * Setter
     *
     * @param covoiturages covoiturages
     */
    public void setCovoiturages(Set<Covoiturage> covoiturages) {
        this.covoiturages = covoiturages;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Setter
     *
     * @param reservations reservations
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<VehiculeService> getVehiculeServices() {
        return vehiculeServices;
    }

    /**
     * Setter
     *
     * @param vehiculeServices vehiculeServices
     */
    public void setVehiculeServices(Set<VehiculeService> vehiculeServices) {
        this.vehiculeServices = vehiculeServices;
    }
}
