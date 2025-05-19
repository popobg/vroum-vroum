package demo.vroum_vroum.repositories;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Iterable<Reservation> findByCollaborateur(Collaborateur collaborateur);

    Collaborateur Collaborateur(Collaborateur collaborateur);
}
