package demo.vroum_vroum.service;

import demo.vroum_vroum.entity.Reservation;
import demo.vroum_vroum.exeption.Controle;
import demo.vroum_vroum.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public Iterable<Reservation> findByCollaborateur(Collaborateur) throws Controle {
        if(reservationRepository.findByCollaborateur(Collaborateur).isPresent()){
            return reservationRepository.findByCollaborateur(Collaborateur);
        }else {
            throw new Controle("Ce collaborateur n'existe pas")
        }
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
