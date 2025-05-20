package demo.vroum_vroum.repositories;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    /**
     * Requete récupérant les reservations du collaborateur passé en paramètre
     * @param collaborateur
     * @return liste de reservation
     */
    List<Reservation> findByCollaborateur(Collaborateur collaborateur);

    Collaborateur Collaborateur(Collaborateur collaborateur);

}
