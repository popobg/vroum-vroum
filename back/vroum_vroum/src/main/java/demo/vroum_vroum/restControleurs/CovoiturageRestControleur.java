package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.dto.CovoiturageDto;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.exceptions.UserNotFoundException;
import demo.vroum_vroum.mappers.CovoiturageMapper;
import demo.vroum_vroum.services.CollaborateurService;
import demo.vroum_vroum.services.CovoiturageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public ResponseEntity<List<CovoiturageDto>> getCovoitDisponiblesByAdressesDate(
            @RequestParam("villedep") String nomVilleDepart,
            @RequestParam("cpdep") String codePostalDepart,
            @RequestParam("villearr") String nomVilleArrivee,
            @RequestParam("cparr") String codePostalArrivee,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDepart)
    {
        return ResponseEntity.ok(CovoiturageMapper.toDtos(covoiturageService.getCovoitDisponiblesByAdressesDate(nomVilleDepart.toLowerCase(),codePostalDepart.toLowerCase(), nomVilleArrivee.toLowerCase(), codePostalArrivee.toLowerCase(), dateDepart)));
    }

    /**
     * Méthode retournant les informations d'un covoiturage
     * @param id Id du covoiturage recherché
     * @return un covoiturage dto (ou null si l'ID ne correspond à aucun covoiturage)
     */
    @GetMapping("/{id}")
    public ResponseEntity<CovoiturageDto> getById(@PathVariable int id) {
        return ResponseEntity.ok(CovoiturageMapper.toDto(covoiturageService.getCovoiturageById(id)));
    }

    /**
     * Méthode retournant les covoiturages auxquels participe l'utilisateur connecté.
     * @return une liste de covoiturages dto
     */
    @GetMapping("/reservations")
    public ResponseEntity<List<CovoiturageDto>> getMesReservations() throws UserNotFoundException {
        Collaborateur currentUser = collaborateurService.getCurrentUser();

        if (currentUser == null) {
            throw new UserNotFoundException("Pas d'utilisateur connecté pour lequel afficher les réservations de covoiturage.");
        }

        return ResponseEntity.ok(CovoiturageMapper.toDtos(covoiturageService.getMesReservationsCovoit(currentUser)));
    }

    /**
     * Méthode permettant d'annuler une réservation de covoiturage faite par un passager
     * @param id Id du covoiturage
     * @return true si l'annulation est un succès, sinon false
     */
    @PutMapping("/reservations/annuler/{id}")
    public ResponseEntity<Boolean> annulerReservation(@PathVariable int id) throws Exception {
        Collaborateur currentUser = collaborateurService.getCurrentUser();

        if (currentUser == null) {
            throw new UserNotFoundException("Pas d'utilisateur connecté pour lequel annuler une réservation de covoiturage.");
        }

        return ResponseEntity.ok(covoiturageService.annulerReservationCovoit(id, currentUser));
    }

    /**
     * Méthode permettant de réserver un covoiturage disponible en tant que passager
     * @param id Id du covoiturage
     * @return true si la réservation est un succès, sinon false
     */
    @PutMapping("/reserver/{id}")
    public ResponseEntity<Boolean> reserverCovoit(@PathVariable int id) {
        Collaborateur currentUser = collaborateurService.getCurrentUser();

        if (currentUser == null) {
            throw new UserNotFoundException("Pas d'utilisateur connecté pour lequel réserver un covoiturage.");
        }

        return ResponseEntity.ok(covoiturageService.reserverCovoit(id, currentUser));
    }
}
