package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.dto.CovoiturageDto;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.exceptions.UserNotFoundException;
import demo.vroum_vroum.mappers.CovoiturageMapper;
import demo.vroum_vroum.services.CollaborateurService;
import demo.vroum_vroum.services.CovoiturageService;
import org.apache.coyote.Response;
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
    public ResponseEntity<CovoiturageDto> getCovoitById(@PathVariable int id) {
        return ResponseEntity.ok(CovoiturageMapper.toDto(covoiturageService.getCovoiturageById(id)));
    }

    /**
     * Méthode retournant les covoiturages auxquels participe l'utilisateur connecté
     * @return une liste de covoiturages dto
     */
    @GetMapping("/reservations")
    public ResponseEntity<List<CovoiturageDto>> getMesReservationsCovoit() {
        Collaborateur currentUser = collaborateurService.getCurrentUser();

        if (currentUser == null) {
            throw new UserNotFoundException("Pas d'utilisateur connecté pour lequel afficher les réservations de covoiturage.");
        }

        return ResponseEntity.ok(CovoiturageMapper.toDtos(covoiturageService.getMesReservationsCovoit(currentUser)));
    }

    /**
     * Méthode retournant les covoiturages proposés par l'utilisateur connecté
     * @return une liste de covoiturages dto
     */
    @GetMapping("/annonces")
    public ResponseEntity<List<CovoiturageDto>> getMesAnnoncesCovoit() {
        Collaborateur currentUser = collaborateurService.getCurrentUser();

        if (currentUser == null) {
            throw new UserNotFoundException("Pas d'utilisateur connecté pour lequel afficher les annonces de covoiturage.");
        }

        return ResponseEntity.ok(CovoiturageMapper.toDtos(covoiturageService.getMesAnnoncesCovoit(currentUser)));
    }

    /**
     * Méthode permettant de créer une annonce de covoiturage à venir.
     * L'utilisateur connecté est l'organisateur du covoiturage.
     * @param covoiturageDto nouvel objet CovoiturageDto à ajouter en base
     * @return true si l'ajout est un succès, sinon false
     */
    @PostMapping("/annonces")
    public ResponseEntity<Boolean> addAnnonceCovoit(CovoiturageDto covoiturageDto) {
        Collaborateur currentUser = collaborateurService.getCurrentUser();

        if (currentUser == null) {
            throw new UserNotFoundException("Pas d'utilisateur connecté pour lequel créer une annonce de covoiturage.");
        }

        return ResponseEntity.ok(covoiturageService.creerAnnonceCovoit(CovoiturageMapper.toEntity(covoiturageDto, currentUser)));
    }

    /**
     * Méthode permettant de modifier une annonce de covoiturage à venir.
     * L'organisateur du covoiturage doit être l'utilisateur connecté.
     * @param covoiturageDto objet CovoiturageDto à modifier
     * @return true si la modification est un succès, sinon false
     */
    @PutMapping("/annonces")
    public ResponseEntity<Boolean> updateAnnonceCovoit(CovoiturageDto covoiturageDto) {
        Collaborateur currentUser = collaborateurService.getCurrentUser();

        if (currentUser == null) {
            throw new UserNotFoundException("Pas d'utilisateur connecté pour lequel modifier l'annonce de covoiturage.");
        }

        return ResponseEntity.ok(covoiturageService.modifierAnnonceCovoit(CovoiturageMapper.toEntity(covoiturageDto, currentUser)));
    }

    /**
     * Méthode permettant d'annuler une réservation de covoiturage faite par un passager
     * @param id Id du covoiturage
     * @return true si l'annulation est un succès, sinon false
     */
    @PutMapping("/reservations/annuler/{id}")
    public ResponseEntity<Boolean> annulerReservation(@PathVariable int id) {
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

    /**
     * Méthode permettant de supprimer une annonce de covoiturage à venir.
     * L'organisateur du covoiturage doit être l'utilisateur connecté.
     * @param id identifiant du covoiturage à supprimer
     * @return true si la suppression est un succès, sinon false
     */
    @DeleteMapping("/annonces/{id}")
    public ResponseEntity<Boolean> deleteAnnonceCovoit(@PathVariable int id) {
        Collaborateur currentUser = collaborateurService.getCurrentUser();

        if (currentUser == null) {
            throw new UserNotFoundException("Pas d'utilisateur connecté pour lequel supprimer ce covoiturage.");
        }

        return ResponseEntity.ok(covoiturageService.supprimerAnnonceCovoit(id));
    }
}
