package demo.vroum_vroum.repositories;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Covoiturage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository de l'entité Covoiturage
 */
public interface CovoiturageRepository extends JpaRepository<Covoiturage, Integer> {
    /**
     * Requête personnalisée pour récupérer les covoiturages disponibles aux adresses et date données
     * @param nomVilleDepart nom ville de départ
     * @param codePostalDepart cp départ
     * @param nomVilleArrivee nom ville d'arrivée
     * @param codePostalArrivee cp arrivée
     * @param dateMin date-heure minimum du départ
     * @return liste de covoiturages
     */
    @Query("""
    SELECT covoit FROM Covoiturage covoit
    WHERE covoit.nbPlaces > 0
    and covoit.date >= :dateMin
    and covoit.adresseDepart.ville = :nomVilleDepart
    and covoit.adresseDepart.codePostal = :codePostalDepart
    and covoit.adresseArrivee.ville = :nomVilleArrivee
    and covoit.adresseArrivee.codePostal = :codePostalArrivee
""")
    public List<Covoiturage> findCovoitDisponiblesByAdressesDate(String nomVilleDepart, String codePostalDepart, String nomVilleArrivee, String codePostalArrivee, LocalDateTime dateMin);

    /**
     * Requête récupérant les covoiturages dont le collaborateur passé en paramètre est passager
     * @param collaborateur passager
     * @return liste de covoiturages
     */
    public List<Covoiturage> findByCollaborateursContaining(Collaborateur collaborateur);
}
