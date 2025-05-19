package demo.vroum_vroum.services;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Reservation;
import demo.vroum_vroum.exceptions.Controle;
import demo.vroum_vroum.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReservationCovoit {

    @Autowired
    ReservationRepository reservationRepository;

    public Iterable<Reservation> findByCollaborateur(Collaborateur collaborateur) throws Controle{
        return reservationRepository.findByCollaborateur(collaborateur);
    }

    public Reservation findById(int id) throws Controle {
        if (reservationRepository.findById(id).isPresent()) {
            return reservationRepository.findById(id).get();
        } else {
            throw new Controle("L'id n'existe pas");
        }
    }

    public void create(Reservation reservation) throws Controle {
        reservationRepository.save(reservation);
    }

    public void update(Reservation reservation) throws Controle {
        reservationRepository.save(reservation);
    }

    public void delete(int id) throws Controle {
        if (reservationRepository.findById(id).isPresent()) {
            reservationRepository.deleteById(id);
        } else {
            throw new Controle("L'id n'existe pas");
        }
    }
}
