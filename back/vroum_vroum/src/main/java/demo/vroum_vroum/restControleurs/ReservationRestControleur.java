package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.dto.ReservationDto;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Reservation;
import demo.vroum_vroum.exceptions.Controle;
import demo.vroum_vroum.exceptions.UserNotFoundException;
import demo.vroum_vroum.mappers.ReservationMapper;
import demo.vroum_vroum.services.CollaborateurService;
import demo.vroum_vroum.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  REST controller des routes en lien avec la reservation de véhicule de service
 */
@RestController
@RequestMapping("/reservation")
public class ReservationRestControleur {

    /** Service concernant la reservatio*/
    private final ReservationService reservationService;

    /**Service concernant les collaborateurs */
    private final CollaborateurService collaborateurService;

    /**
     * Constructeur ReservationRestControlleur
     * @param reservationService service pour la reservation
     * @param collaborateurService service pour les collaborateurs
     */
    @Autowired
    public ReservationRestControleur(ReservationService reservationService, CollaborateurService collaborateurService) {
        this.reservationService = reservationService;
        this.collaborateurService = collaborateurService;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
       Iterable<Reservation> reservations = reservationService.findAll();
       List<Reservation> reservationList = new ArrayList<>();
       for (Reservation reservation : reservations) {
           reservationList.add(reservation);
       }
       return reservationList;
    }

    @GetMapping("{id}")
    public Reservation getReservationById(@PathVariable int id) throws Controle {
        return reservationService.findById(id);
    }

    /**
     * Méthode retournant les reservations de l'utilisateur connecté.
     * @return une liste de réservation dto
     * @throws UserNotFoundException UserNotFoundException
     */
    @GetMapping("/reservationsVehicule")
    public ResponseEntity<List<ReservationDto>> getReservationVehicule()  throws UserNotFoundException {
        Collaborateur cureentUser = collaborateurService.getCurrentUser();

        if (cureentUser == null) {
            throw new UserNotFoundException("Pas d'utilisateur connecté pour lequel afficher les réservations de véhicule de service.");
        }

        return  ResponseEntity.ok(ReservationMapper.toDtos(reservationService.getMesReservationVehicule(cureentUser)));
    }

    /**
     * Méthode permettant d'annuler une réservation de véhicule faite par un collaborateur
     * @param id Id de la reservation
     */
    @PutMapping("/reservationsVehicule/annuler/{id}")
    public ResponseEntity<String> annulerReservation(@PathVariable int id) throws Exception {
        Collaborateur currentUser = collaborateurService.getCurrentUser();

        if (currentUser == null) {
            throw new UserNotFoundException("Pas d'utilisateur connecté pour lequel annuler une réservation de véhicule.");
        }else {
            reservationService.annulerReservationVehicule(id, currentUser);
        }

        return ResponseEntity.ok("Reservation annulé avec succès");
    }

    /**
     * Méthode permettant de créer une reservation de véhicule
     * @param reservation reservation à créer
     * @return message de validation
     * @throws Controle
     */
    @PostMapping("/reservationVehicule")
    public ResponseEntity<String> postReservation(@RequestBody Reservation reservation) throws Controle {
        reservationService.create(reservation);
        return ResponseEntity.ok("Reservation crée avec succès");
    }

    @PutMapping
    public ResponseEntity<String> putReservation(@RequestBody Reservation reservation) throws Controle {
        reservationService.update(reservation);
        return ResponseEntity.ok("Le département " + reservation.getId() + " à etait modifiée");
    }
}
