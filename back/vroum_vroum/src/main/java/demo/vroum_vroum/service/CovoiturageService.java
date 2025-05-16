package demo.vroum_vroum.service;

import demo.vroum_vroum.entity.Collaborateur;
import demo.vroum_vroum.entity.Covoiturage;
import demo.vroum_vroum.repository.CollaborateurRepository;
import demo.vroum_vroum.repository.CovoiturageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service en lien avec le covoiturage
 */
@Service
public class CovoiturageService {
    /** Covoiturage repository */
    private final CovoiturageRepository covoiturageRepository;

    private final CollaborateurRepository collaborateurRepository;

    /**
     * Constructeur CovoiturageService
     * @param covoiturageRepository repository de l'entité Covoiturage
     */
    @Autowired
    public CovoiturageService(CovoiturageRepository covoiturageRepository, CollaborateurRepository collaborateurRepository) {
        this.covoiturageRepository = covoiturageRepository;
        this.collaborateurRepository = collaborateurRepository;
    }

    /**
     * Méthode permettant de récupérer la liste des covoiturages pour lesquels il reste des places disponibles
     * à partir de la ville et cp de départ, de la ville et cp d'arrivée et de la date du départ.
     * @param villeDepart ville de départ
     * @param codePostalDepart cp départ
     * @param villeArrivee ville d'arrivée
     * @param codePostalArrivee cp arrivée
     * @param dateDepart date-heure de départ
     * @return liste de covoiturages
     */
    public List<Covoiturage> getCovoitDisponiblesByAdressesDate(String villeDepart, String codePostalDepart, String villeArrivee, String codePostalArrivee, LocalDateTime dateDepart) {
        return covoiturageRepository.findCovoitDisponiblesByAdressesDate(villeDepart, codePostalDepart, villeArrivee, codePostalArrivee, dateDepart);
    }

    /**
     * Méthode de service récupérant un covoiturage à partir de son Id
     * @param id Id du covoiturage
     * @return un covoiturage ou null si pas de covoiturage trouvé à cet Id
     */
    public Optional<Covoiturage> getCovoiturageById(int id) {
        return covoiturageRepository.findById(id);
    }

    /**
     * Méthode de service récupérant les covoiturages d'un passager
     * @param collaborateur collaborateur passager
     * @return liste de covoiturages
     */
    public List<Covoiturage> getMesReservationsCovoit(Collaborateur collaborateur) {
        return covoiturageRepository.findByCollaborateursContaining(collaborateur);
    }

    /**
     * Méthode permettant de supprimer une réservation de covoiturage pour un passager
     * @param idCovoit Id du covoiturage
     * @param idPassager Id du collaborateur passager
     * @return true si la suppression est un succès, false sinon
     */
    public Boolean annulerReservationCovoit(int idCovoit, int idPassager) {
        Optional<Covoiturage> optCovoit = covoiturageRepository.findById(idCovoit);

        Optional<Collaborateur> optCollab = collaborateurRepository.findById(idPassager);

        // Id incorrect
        if (optCovoit.isEmpty() || optCollab.isEmpty()) {
            return false;
        }

        Covoiturage covoit = optCovoit.get();
        Collaborateur collab = optCollab.get();

        // Impossible d'annuler si le covoit est déjà passé
        if (covoit.getDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        try {
            // On supprime le passager du covoit et le covoit des réservations du passager
            covoit.getCollaborateurs().remove(collab);
            collab.getCovoiturages().remove(covoit);

            // On sauvegarde les modifications en BDD
            covoiturageRepository.save(covoit);
            collaborateurRepository.save(collab);

            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    /**
     * Méthode permettant d'ajouter un passager à un covoiturage disponible
     * @param idCovoit Id du covoiturage
     * @param idPassager Id du passager
     * @return true si la réservation est un succès, sinon false
     */
    public Boolean reserverCovoit(int idCovoit, int idPassager) {
        Optional<Covoiturage> optCovoit = covoiturageRepository.findById(idCovoit);

        Optional<Collaborateur> optCollab = collaborateurRepository.findById(idPassager);

        // Id incorrect
        if (optCovoit.isEmpty() || optCollab.isEmpty()) {
            return false;
        }

        Covoiturage covoit = optCovoit.get();
        Collaborateur collab = optCollab.get();

        LocalDateTime dateActuelle = LocalDateTime.now();

        int nbPlaces = covoit.getNbPlaces();

        // Plus de places disponibles dans le covoiturage
        // ou date de départ du covoiturage passé
        // ou passager ayant déjà réservé ce covoit
        // ou utilisateur actuel = conducteur
        if (nbPlaces == 0
                || covoit.getDate().isBefore(LocalDateTime.now())
                || covoit.getCollaborateurs().contains(collab)
                || covoit.getOrganisateur().getId() == collab.getId()) {
            return false;
        }

        try {
            // On ajoute le passager au covoit et le covoit à la liste des covoiturages du passager
            covoit.getCollaborateurs().add(collab);
            collab.getCovoiturages().add(covoit);
            // Une place en moins dans le covoit !
            nbPlaces--;
            covoit.setNbPlaces(nbPlaces);

            // On sauvegarde les modifications en BDD
            covoiturageRepository.save(covoit);
            collaborateurRepository.save(collab);

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
