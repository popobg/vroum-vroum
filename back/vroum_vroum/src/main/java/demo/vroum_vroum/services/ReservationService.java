package demo.vroum_vroum.services;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Reservation;
import demo.vroum_vroum.exceptions.Controle;
import demo.vroum_vroum.repositories.CollaborateurRepository;
import demo.vroum_vroum.repositories.ReservationRepository;
import demo.vroum_vroum.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    private CollaborateurRepository collaborateurRepository;

    public Iterable<Reservation> findAll() {return reservationRepository.findAll();}

    public Reservation findById(int id) throws Controle {
        if (reservationRepository.findById(id).isPresent()) {
            return reservationRepository.findById(id).get();
        } else {
            throw new Controle("L'id n'existe pas");
        }
    }

    /**
     * Méthode de service récupérant les réservations d'un collaborateur
     * @param collaborateur vollaborateur
     * @return liste de réservation
     */
    public List<Reservation> getMesReservationVehicule(Collaborateur collaborateur) {
        return reservationRepository.findByCollaborateur(collaborateur);
    }

    public void create(Reservation reservation) throws Controle {
        reservationRepository.save(reservation);
    }

    public void update(Reservation reservation) throws Controle {
        reservationRepository.save(reservation);
    }

    /**
     * Méthode permettant de supprimer une réservation de vehicule pour un collaborateur
     * @param idreservation Id du la réservation
     * @param collaborateur Id du collaborateur
     * @throws Controle controle
     */
    public void annulerReservationVehicule(int idreservation, Collaborateur collaborateur) throws Controle {
        // Check de l'ID dans la méthode findById
        Reservation reservation = this.findById(idreservation);

        // Impossible d'annuler si la réservation est déjà passé
        if (!Validator.matchDateUlterieure(reservation.getDateDepart())) {
            throw new IllegalArgumentException("Impossible d'annuler une réservation dont la date-heure de départ est déjà passée !");
        }else {
            // On supprime la réservation
            reservationRepository.delete(reservation);
            // On supprime la réservation du collaborateur
            collaborateur.getReservations().remove(reservation);
            // On sauvegarde les modifications en BDD
            collaborateurRepository.save(collaborateur);
        }
    }
}
