package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.dto.CovoiturageDto;
import demo.vroum_vroum.entity.Collaborateur;
import demo.vroum_vroum.entity.Covoiturage;
import demo.vroum_vroum.mappers.CovoiturageMapper;
import demo.vroum_vroum.service.CollaborateurService;
import demo.vroum_vroum.service.CovoiturageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller des routes en lien avec le covoiturage
 */
@RestController
@RequestMapping("/covoiturage")
public class CovoiturageRestControleur {
    /** Service concernant le covoiturage */
    private final CovoiturageService covoiturageService;

    /** Service concernant les collaborateurs */
    private final CollaborateurService collaborateurService;

    /**
     * Constructeur CovoiturageRestControleur
     * @param covoiturageService service pour le covoiturage
     * @param collaborateurService service pour les collaborateurs
     */
    @Autowired
    public CovoiturageRestControleur(CovoiturageService covoiturageService, CollaborateurService collaborateurService) {
        this.covoiturageService = covoiturageService;
        this.collaborateurService = collaborateurService;
    }

    /**
     * Méthode retournant les covoiturages disponibles pour les informations saisies
     * @param nomVilleDepart nomm ville départ
     * @param codePostalDepart code postal départ
     * @param nomVilleArrivee nom ville arrivée
     * @param codePostalArrivee code postal arrivée
     * @param dateDepart date minimale du départ
     * @return une liste de covoiturages dto (ou une liste vide si aucun covoiturage ne correspond aux critères)
     */
    @GetMapping("/rechercher")
    public List<CovoiturageDto> getCovoitDisponiblesByAdressesDate(@RequestParam("villedep") String nomVilleDepart,
                                                                   @RequestParam("cpdep") String codePostalDepart, @RequestParam("villearr") String nomVilleArrivee, @RequestParam("cparr") String codePostalArrivee, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDepart) {
        return CovoiturageMapper.toDtos(covoiturageService.getCovoitDisponiblesByAdressesDate(nomVilleDepart, codePostalDepart, nomVilleArrivee, codePostalArrivee, dateDepart));
    }

    /**
     * Méthode retournant les informations d'un covoiturage
     * @param id Id du covoiturage recherché
     * @return un covoiturage dto (ou null si l'ID ne correspond à aucun covoiturage)
     */
    @GetMapping("/{id}")
    public CovoiturageDto getById(@PathVariable int id) {
        Optional<Covoiturage> covoit = covoiturageService.getCovoiturageById(id);

        return covoit.map(CovoiturageMapper::toDto).orElse(null);
    }

    /**
     * Méthode retournant les covoiturages auxquels participe l'utilisateur connecté.
     * @return une liste de covoiturages dto
     */
    @GetMapping("/reservations")
    public List<CovoiturageDto> getMesReservations() {
        Collaborateur currentUser = collaborateurService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }

        return CovoiturageMapper.toDtos(covoiturageService.getMesReservationsCovoit(currentUser));
    }

    /**
     * Méthode permettant d'annuler une réservation de covoiturage faite par un passager
     * @param id Id du covoiturage
     * @return true si l'annulation est un succès, sinon false
     */
    @PutMapping("/reservations/annuler/{id}")
    public Boolean annulerReservation(@PathVariable int id) {
        Collaborateur currentUser = collaborateurService.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        return covoiturageService.annulerReservationCovoit(id, currentUser.getId());
    }

    /**
     * Méthode permettant de réserver un covoiturage disponible en tant que passager
     * @param id Id du covoiturage
     * @return true si la réservation est un succès, sinon false
     */
    @PutMapping("/reserver/{id}")
    public Boolean reserverCovoit(@PathVariable int id) {
        Collaborateur currentUser = collaborateurService.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        return covoiturageService.reserverCovoit(id, currentUser.getId());
    }
}
