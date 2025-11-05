package demo.vroum_vroum.services;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Reservation;
import demo.vroum_vroum.repositories.ReservationRepository;
import demo.vroum_vroum.utils.Validator;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Classe de service liée à l'entité Réservation dédiée à la réservation des véhicules de service
 */
@Service
public class ReservationService {

    /** Repository de l'entité Réservation */
    private final ReservationRepository reservationRepository;

    /** Service de l'entité Collaborateur */
    private final CollaborateurService collaborateurService;

    /**
     * Constructeur
     *
     * @param reservationRepository repo réservation
     * @param collaborateurService service collaborateur
     */
    public ReservationService(ReservationRepository reservationRepository, CollaborateurService collaborateurService) {
        this.reservationRepository = reservationRepository;
        this.collaborateurService = collaborateurService;
    }

    /**
     * Méthode de service permettant de récupérer une liste des réservations de véhicule de service.
     * @return liste entité réservation
     */
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    /**
     * Méthode de service permettant de récupérer une réservation à partir de son ID.
     * @param id id de la réservation
     * @return une Reservation
     * @throws EntityNotFoundException aucune réservation trouvée
     */
    public Reservation findById(int id) throws EntityNotFoundException {
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if (reservation.isEmpty()) {
            throw new EntityNotFoundException("Pas de réservation à l'ID " + id);
        }

        return reservation.get();
    }

    /**
     * Méthode de service récupérant les réservations de véhicule de service d'un collaborateur.
     * @param idCollaborateur id du collaborateur
     * @return liste de réservation
     * @throws EntityNotFoundException pas de collaborateur trouvé
     */
    public List<Reservation> getMesReservationsVehicule(int idCollaborateur) throws EntityNotFoundException {
        Collaborateur collaborateur = collaborateurService.getCollaborateurById(idCollaborateur);

        return collaborateur.getReservations();
    }

    public void create(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public void update(Reservation reservation) throws EntityNotFoundException {
        if (!reservationRepository.existsById(reservation.getId())) {
            throw new EntityNotFoundException("La réservation d'ID" + reservation.getId() + "n'existe pas");
        }

        reservationRepository.save(reservation);
    }

    /**
     * Méthode permettant de supprimer une réservation de véhicule pour un collaborateur.
     * @param idReservation Id de la réservation
     * @param idCollaborateur Id du collaborateur
     * @throws EntityNotFoundException collaborateur ou réservation non trouvé.s
     * @throws IllegalArgumentException conditions d'annulation non respectées
     */
    public void annulerReservationVehicule(int idReservation, int idCollaborateur) throws EntityNotFoundException, IllegalArgumentException {
        Collaborateur collaborateur = collaborateurService.getCollaborateurById(idCollaborateur);
        Reservation reservation = this.findById(idReservation);

        // Impossible d'annuler si la réservation est déjà passée
        if (!Validator.matchDateUlterieure(reservation.getDateDepart())) {
            throw new IllegalArgumentException("Impossible d'annuler une réservation dont la date-heure de départ est déjà passée !");
        }

        // On supprime la réservation
        reservationRepository.delete(reservation);
        // On supprime la réservation des réservations du collaborateur
        collaborateur.getReservations().remove(reservation);
        // On sauvegarde les modifications en BDD
        collaborateurService.updateCollaborateur(collaborateur);
    }
}
