package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.dto.CovoiturageDto;
import demo.vroum_vroum.mappers.CovoiturageMapper;
import demo.vroum_vroum.services.CovoiturageService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * REST controller des routes en lien avec le covoiturage
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/covoiturage")
public class CovoiturageRestControleur {
    /** Service concernant le covoiturage */
    private final CovoiturageService covoiturageService;

    /**
     * Constructeur CovoiturageRestControleur
     * @param covoiturageService service pour le covoiturage
     * @param collaborateurService service pour les collaborateurs
     */
    public CovoiturageRestControleur(CovoiturageService covoiturageService) {
        this.covoiturageService = covoiturageService;
    }

    @GetMapping("/tous")
    public ResponseEntity<Set<CovoiturageDto>> getTousLesCovoiturages() {
        Set<CovoiturageDto> covoitDtos = CovoiturageMapper.toDtos(covoiturageService.findAll());
        return ResponseEntity.ok(covoitDtos);
    }

    /**
     * Récupère les covoiturages disponibles pour les informations saisies.
     *
     * @param nomVilleDepart nomm ville départ
     * @param codePostalDepart code postal départ
     * @param nomVilleArrivee nom ville arrivée
     * @param codePostalArrivee code postal arrivée
     * @param dateDepart date minimale du départ
     * @return un set de covoiturages dto (ou une liste vide si aucun covoiturage ne correspond aux critères)
     * @throws IllegalArgumentException 400 : conditions de recherche non satisfaites
     */
    @GetMapping("/rechercher")
    public ResponseEntity<Set<CovoiturageDto>> getCovoitDisponiblesByAdressesDate(
            @RequestParam("villedep") String nomVilleDepart,
            @RequestParam("cpdep") String codePostalDepart,
            @RequestParam("villearr") String nomVilleArrivee,
            @RequestParam("cparr") String codePostalArrivee,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDepart) throws IllegalArgumentException
    {
        return ResponseEntity.ok(CovoiturageMapper.toDtos(covoiturageService.getCovoitDisponiblesByAdressesDate(nomVilleDepart.toLowerCase(),codePostalDepart.toLowerCase(), nomVilleArrivee.toLowerCase(), codePostalArrivee.toLowerCase(), dateDepart)));
    }

    /**
     * Récupère les informations d'un covoiturage.
     *
     * @param id Id du covoiturage recherché
     * @return un covoiturage dto (ou null si l'ID ne correspond à aucun covoiturage)
     * @throws EntityNotFoundException 404 : covoiturage non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<CovoiturageDto> getById(@PathVariable int id) throws EntityNotFoundException {
        return ResponseEntity.ok(CovoiturageMapper.toDto(covoiturageService.getCovoiturageById(id)));
    }

    /**
     * Récupère les covoiturages auxquels participe un collaborateur.
     *
     * @param idCollaborateur id du collaborateur passager
     * @return set de covoiturages dto
     * @throws EntityNotFoundException 404 : collaborateur non trouvé
     */
    @GetMapping("/reservations")
    public ResponseEntity<Set<CovoiturageDto>> getMesReservations(@RequestParam int idCollaborateur) throws EntityNotFoundException {
        return ResponseEntity.ok(CovoiturageMapper.toDtos(covoiturageService.getMesReservationsCovoit(idCollaborateur)));
    }

    /**
     * Annule une réservation de covoiturage faite par un passager collaborateur.
     *
     * @param idReservation Id de la réservation de covoiturage
     * @param idCollaborateur Id du collaborateur passager
     * @return réponse 204 si l'annulation a été faite avec succès, sinon 404 ou 500
     * @throws EntityNotFoundException 404 : collaborateur ou covoit non trouvé
     * @throws IllegalArgumentException 400 : conditions d'annulation non respectées
     * @throws Exception 500 : erreur lors de l'opération
     */
    @PutMapping("/reservations/annuler/{idReservation}/{idCollaborateur}")
    public ResponseEntity<Void> annulerReservation(
            @PathVariable int idReservation,
            @PathVariable int idCollaborateur) {

        try {
            covoiturageService.annulerReservationCovoit(idReservation, idCollaborateur);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace(); // voir l’erreur exacte dans les logs
            return ResponseEntity.status(500).build();
        }
    }


    /**
     * Réserve un covoiturage disponible en tant que passager collaborateur.
     *
     * @param idReservation id covoit
     * @param idCollaborateur id collaborateur passager
     * @return statut 204 si l'opération s'est déroulée avec succès, sinon 404 ou 400
     * @throws EntityNotFoundException 404 : covoit ou collaborateur non trouvé
     * @throws IllegalArgumentException 400 : conditions de réservation non satisfaites
     * @throws Exception 500 : erreur lors de l'opération
     */
    @PutMapping("/reservations/reserver/{idReservation}/{idCollaborateur}")
    public ResponseEntity<Void> reserverCovoit(
            @PathVariable int idReservation,
            @PathVariable int idCollaborateur)
            throws EntityNotFoundException, IllegalArgumentException, Exception {

        covoiturageService.reserverCovoit(idReservation, idCollaborateur);
        return ResponseEntity.noContent().build();
    }

}
