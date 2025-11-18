package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.dto.ReservationDto;
import demo.vroum_vroum.entities.Reservation;
import demo.vroum_vroum.mappers.ReservationMapper;
import demo.vroum_vroum.services.ReservationService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  REST controller des routes en lien avec la réservation de véhicule de service
 */
@RestController
@RequestMapping("/reservation")
public class ReservationRestControleur {

    /** Service concernant la reservatio*/
    private final ReservationService reservationService;

    /**
     * Constructeur ReservationRestControlleur
     *
     * @param reservationService service pour la réservation
     * @param collaborateurService service pour les collaborateurs
     */
    public ReservationRestControleur(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Récupère toutes les réservations de véhicules de service.
     *
     * @return liste de réservations
     */
    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
      return ResponseEntity.ok(ReservationMapper.toDtos(reservationService.findAll()));
    }

    /**
     * Récupère une réservation de véhicule de service à partir de son Id.
     *
     * @param id id de la réservation
     * @return une réservation
     */
    @GetMapping("{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable int id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    /**
     * Récupère les reservations d'un utilisateur.
     *
     * @return une liste de réservation dto
     */
    @GetMapping("/vehicule")
    public ResponseEntity<List<ReservationDto>> getReservationsVehicule(int idCollaborateur) {
        return  ResponseEntity.ok(ReservationMapper.toDtos(reservationService.getMesReservationsVehicule(idCollaborateur)));
    }

    /**
     * Crée une reservation de véhicule.
     *
     * @param reservation reservation à créer
     * @return statut 204 si l'opération s'est déroulée avec succès, sinon erreur 500
     */
    @PostMapping("/vehicule")
    public ResponseEntity<Void> postReservation(@RequestBody Reservation reservation) {
        reservationService.create(reservation);
        return ResponseEntity.noContent().build();
    }

    /**
     * Met-à-jour les informations d'une réservation de véhicule.
     *
     * @param reservation réservation avec informations modifiées
     * @return statut 204 si l'opération s'est déroulée avec succès, sinon erreur 500
     */
    @PutMapping("/vehicule")
    public ResponseEntity<Void> putReservation(@RequestBody Reservation reservation) {
        reservationService.update(reservation);
        return ResponseEntity.noContent().build();
    }


    /**
     * Annule une réservation de véhicule de service faite par un collaborateur.
     *
     * @param idReservation Id de la réservation
     * @param idCollaborateur Id du collaborateur
     * @return statut HTTP 204 si l'annulation a eu lieu avec succès, sinon 404 ou 500
     */
    @PutMapping("/vehicule/annuler/{idReservation}")
    public ResponseEntity<Void> cancelReservation(@PathVariable int idReservation, int idCollaborateur) {
        reservationService.annulerReservationVehicule(idReservation, idCollaborateur);

        return ResponseEntity.noContent().build();
    }
}
