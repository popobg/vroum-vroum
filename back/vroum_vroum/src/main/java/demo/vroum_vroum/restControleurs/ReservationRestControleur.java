package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.entity.Reservation;
import demo.vroum_vroum.exeption.Controle;
import demo.vroum_vroum.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationRestControleur {

    @Autowired
    ReservationService reservationService;

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

    @PostMapping
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
