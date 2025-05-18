package demo.vroum_vroum.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.text.Collator;
import java.util.*;

/**
 * Entité Collaborateur
 */
@Entity
@Table(name = "COLLABORATEUR")
public class Collaborateur implements UserDetails, Comparable<Collaborateur>, Serializable {
    /** Identifiant unique et non modifiable du collaborateur */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    /** Nom du collaborateur */
    @Column(name = "NOM")
    private String nom;

    /** Prénom du collaborateur */
    @Column(name = "PRENOM")
    private String prenom;

    /** Adresse du collaborateur */
    @Column(name = "ADRESSE")
    private String adresse;


    /** Adresse email du collaborateur */
    @Column(name = "EMAIL")
    private String email;

    /** Numéro de téléphone du collaborateur */
    @Column(name = "TELEPHONE")
    private String telephone;

    /** Pseudo en tant qu'utilisateur de l'application */
    @Column(name = "PSEUDO")
    private String pseudo;

    /** Mot de passe en tant qu'utilisateur de l'application */
    @Column(name = "PASSWORD")
    private String password;

    /** Statut administrateur de l'application */
    @Column(name = "ADMIN")
    private Boolean admin;

    /** Véhicules possédés par le collaborateur (pour covoiturage) */
    @OneToMany(mappedBy = "collaborateur")
    private List<Vehicule> vehicules;

    /** Covoiturages auxquels le collaborateur participe en tant que passager */
    @ManyToMany
    @JoinTable(name = "PASSAGER_COVOITURAGE", joinColumns = @JoinColumn(name="ID_PASSAGER", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name="ID_COVOITURAGE", referencedColumnName = "ID"))
    private Set<Covoiturage> covoiturages;

    /** Réservations de véhicules de service réalisées par le collaborateur */
    @OneToMany(mappedBy = "collaborateur")
    private List<Reservation> reservations;

    /** Véhicules de service gérés par le collaborateur */
    @ManyToMany
    @JoinTable(name = "ADMINISTRATEUR_VEHICULE_SERVICE", joinColumns = @JoinColumn(name = "ID_ADMINISTARTEUR", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_VEHICULE_SEVICE", referencedColumnName = "ID"))
    private Set<VehiculeService> vehiculeServices;

    {
        covoiturages = new HashSet<>();
        vehicules = new ArrayList<>();
        reservations = new ArrayList<>();
        vehiculeServices = new HashSet<>();
    }

    /** Constructeur vide */
    public Collaborateur() {}

    /**
     * Constructeur
     * @param id identifiant
     * @param nom nom
     * @param prenom prénom
     * @param adresse adresse
     * @param email adresse email
     * @param telephone numéro téléphone
     * @param pseudo pseudo utilisateur app
     * @param password mot de passe utilisateur app
     * @param admin statut administrateur
     */
    public Collaborateur(int id, String nom, String prenom, String adresse, String email, String telephone, String pseudo, String password, Boolean admin) {
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

    /**
     * Méthode déterminant le rôle de l'utilisateur (admin ou utilisateur)
     * @return Autorisation
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> admin ? "ROLE_ADMIN" : "ROLE_USER");
    }

    /**
     * Getter
     * @return pseudo
     */
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

    /**
     * Getter
     * @return Id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     * @param id identifiant
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter nom
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter
     * @param nom nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter
     * @return prénom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter
     * @param prenom prénom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Getter
     * @return adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Setter
     * @param adresse adresse
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Getter
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter
     * @return num téléphone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Setter
     * @param telephone num téléphone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Getter
     * @return pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Setter
     * @param pseudo pseudo
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Getter
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter
     * @return admin
     */
    public Boolean getAdmin() {
        return admin;
    }

    /**
     * Setter
     * @param admin admin
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    /**
     * Getter
     * @return liste de véhicules
     */
    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    /**
     * Setter
     * @param vehicules liste de véhicules
     */
    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    /**
     * Getter
     * @return liste de covoiturages
     */
    public Set<Covoiturage> getCovoiturages() {
        return covoiturages;
    }

    /**
     * Setter
     * @param covoiturages liste de covoiturages
     */
    public void setCovoiturages(Set<Covoiturage> covoiturages) {
        this.covoiturages = covoiturages;
    }

    /**
     * Getter
     * @return liste de réservations de véhicule de service
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Setter
     * @param reservations liste de réservations de véhicule de service
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Getter
     * @return liste de véhicules de service
     */
    public Set<VehiculeService> getVehiculeServices() {
        return vehiculeServices;
    }

    /**
     * Setter
     * @param vehiculeServices liste de véhicules de service
     */
    public void setVehiculeServices(Set<VehiculeService> vehiculeServices) {
        this.vehiculeServices = vehiculeServices;
    }

    /**
     * Méthode générant une chaîne de caractères à partir des attributs du collaborateur
     * @return String
     */
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

    /**
     * Vérifie si deux objets de la classe Collaborateur sont identiques.
     * @param o instance de l'objet à comparer
     * @return boolean, true si les objets sont identiques, sinon false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Collaborateur autreCollab)) return false;
        return id == autreCollab.id && Objects.equals(nom, autreCollab.nom)
                && Objects.equals(prenom, autreCollab.prenom)
                && Objects.equals(telephone, autreCollab.telephone)
                && Objects.equals(email, autreCollab.email)
                && Objects.equals(pseudo, autreCollab.pseudo)
                && Objects.equals(password, autreCollab.password)
                && Objects.equals(admin, autreCollab.admin);
    }

    /**
     * Retourne le hashcode de l'objet Collaborateur.
     * @return entier
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, telephone, email, pseudo, password, admin);
    }

    /**
     * Compare deux collaborateurs sur la base de leur nom, puis du prénom.
     * @param autreCollab le collaborateur de comparaison
     * @return int, indiquant si le collaborateur doit être classé avant ou après :
     *          0 = même classement, même nom
     *          1 = l'instance actuelle est supérieure au collaborateur de comparaison
     *          -1 = l'instance actuelle est inférieure au collaborateur de comparaison
     */
    @Override
    public int compareTo(Collaborateur autreCollab) {
        Collator collator = Collator.getInstance(Locale.FRANCE);

        // Tri par nom
        int nomComparaison = collator.compare(this.nom.toLowerCase(), autreCollab.nom.toLowerCase());

        if (nomComparaison != 0) {
            return nomComparaison;
        }

        // Si nom identique, tri par prénom
        return collator.compare(this.prenom.toLowerCase(), autreCollab.prenom.toLowerCase());
    }
}
