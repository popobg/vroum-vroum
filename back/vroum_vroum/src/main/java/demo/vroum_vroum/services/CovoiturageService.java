package demo.vroum_vroum.services;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Covoiturage;
import demo.vroum_vroum.repositories.CovoiturageRepository;
import demo.vroum_vroum.utils.Validator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service en lien avec le covoiturage
 */
@Service
public class CovoiturageService {
    /** Covoiturage repository */
    private final CovoiturageRepository covoiturageRepository;

    private final CollaborateurService collaborateurService;

    /**
     * Constructeur CovoiturageService
     * @param covoiturageRepository repository de l'entité Covoiturage
     */
    public CovoiturageService(CovoiturageRepository covoiturageRepository, CollaborateurService collaborateurService) {
        this.covoiturageRepository = covoiturageRepository;
        this.collaborateurService = collaborateurService;
    }

    public Set<Covoiturage> findAll() {
        return new HashSet<Covoiturage>(covoiturageRepository.findAll());
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
//    public Set<Covoiturage> getCovoitDisponiblesByAdressesDate(String villeDepart, String codePostalDepart, String villeArrivee, String codePostalArrivee, LocalDateTime dateDepart) throws IllegalArgumentException {
//        if (villeDepart.isEmpty()
//                || codePostalDepart.isEmpty()
//                || villeArrivee.isEmpty()
//                || codePostalArrivee.isEmpty()) {
//            throw new IllegalArgumentException("Vous devez fournir une ville de départ, un code postal de départ, une ville d'arrivée et un code postal d'arrivée.");
//        }
//
//        if (!Validator.matchCodePostalFormat(codePostalDepart)
//                || !Validator.matchCodePostalFormat(codePostalArrivee)) {
//            throw new IllegalArgumentException("Le code postal doit faire 5 caractères.");
//        }
//
//        if (!Validator.matchDateUlterieure(dateDepart)) {
//            throw new IllegalArgumentException("La date de départ recherchée doit être ultérieure à la date et heure actuelle.");
//        }
//
//        return covoiturageRepository.findCovoitDisponiblesByAdressesDate(villeDepart, codePostalDepart, villeArrivee, codePostalArrivee, dateDepart);
//    }

    public Set<Covoiturage> getCovoitDisponiblesByAdressesDate(
            String villeDepart,
            String codePostalDepart,
            String villeArrivee,
            String codePostalArrivee,
            LocalDateTime dateDepart) {

        return covoiturageRepository.findAll().stream()
                .filter(c -> villeDepart.isEmpty() || c.getAdresseDepart().getVille().equalsIgnoreCase(villeDepart))
                .filter(c -> codePostalDepart.isEmpty() || c.getAdresseDepart().getCodePostal().equals(codePostalDepart))
                .filter(c -> villeArrivee.isEmpty() || c.getAdresseArrivee().getVille().equalsIgnoreCase(villeArrivee))
                .filter(c -> codePostalArrivee.isEmpty() || c.getAdresseArrivee().getCodePostal().equals(codePostalArrivee))
                .filter(c -> dateDepart == null || !c.getDate().isBefore(dateDepart))
                .collect(Collectors.toSet());
    }


    /**
     * Méthode de service récupérant un covoiturage à partir de son Id.
     * @param id Id du covoiturage
     * @return un covoiturage ou null si pas de covoiturage trouvé à cet Id
     * @throws EntityNotFoundException aucune entité correspondante à cet Id
     */
    public Covoiturage getCovoiturageById(int id) throws EntityNotFoundException {
        Optional<Covoiturage> optCovoit = covoiturageRepository.findById(id);

        if (optCovoit.isEmpty()) {
            throw new EntityNotFoundException("Aucun covoiturage ne correspond à l'ID " + id + " dans la base de données.");
        }

        return optCovoit.get();
    }

    /**
     * Méthode de service récupérant les covoiturages d'un passager.
     * @param collaborateur collaborateur passager
     * @return liste de covoiturages
     */
    public Set<Covoiturage> getMesReservationsCovoit(int idCollaborateur) throws EntityNotFoundException {
        Collaborateur collaborateur = collaborateurService.getCollaborateurById(idCollaborateur);

        return collaborateur.getCovoiturages();
    }

    /**
     * Méthode permettant de supprimer une réservation de covoiturage pour un passager
     * @param idCovoit Id du covoiturage
     * @param idCollaborateur Id du collaborateur passager
     * @throws EntityNotFoundException collaborateur ou covoit non trouvé
     * @throws IllegalArgumentException conditions d'annulation non respectées
     * @throws Exception erreur lors de l'opération
     */
    public void annulerReservationCovoit(int idCovoit, int idCollaborateur) throws EntityNotFoundException, IllegalArgumentException, Exception {
        Collaborateur passager = collaborateurService.getCollaborateurById(idCollaborateur);
        Covoiturage covoit = this.getCovoiturageById(idCovoit);

        // Impossible d'annuler si le covoit est déjà passé
        if (!Validator.matchDateUlterieure(covoit.getDate())) {
            throw new IllegalArgumentException("Impossible d'annuler un covoiturage dont la date-heure de départ est déjà passée !");
        }

        // On supprime le passager du covoit
        covoit.getCollaborateurs().remove(passager);
        // On supprime le covoit des réservations du passager
        passager.getCovoiturages().remove(covoit);

        // On sauvegarde les modifications en BDD
        covoiturageRepository.save(covoit);
        collaborateurService.updateCollaborateur(passager);
    }

    /**
     * Méthode permettant d'ajouter un passager à un covoiturage disponible
     * @param idCovoit Id du covoiturage
     * @param idCollaborateur Id du passager
     * @throws EntityNotFoundException collaborateur ou covoit non trouvé
     * @throws IllegalArgumentException conditions de réservation non respectées
     * @throws Exception erreur lors de l'opération
     */
    public void reserverCovoit(int idCovoit, int idCollaborateur) throws EntityNotFoundException, IllegalArgumentException, Exception {
        Collaborateur passager = collaborateurService.getCollaborateurById(idCollaborateur);
        Covoiturage covoit = this.getCovoiturageById(idCovoit);

        if (!Validator.matchDateUlterieure(covoit.getDate())) {
            throw new IllegalArgumentException("Impossible de réserver un covoiturage dont la date-heure de départ est déjà passée !");
        }

        int nbPlaces = covoit.getNbPlaces();

        if (nbPlaces == 0) {
            throw new IllegalArgumentException("Impossible de réserver un covoiturage qui n'a plus de places disponibles.");
        }

        if (covoit.getCollaborateurs().contains(passager)) {
            throw new IllegalArgumentException("Vous êtes déjà inscrit dans ce coivoiturage.");
        }

        if (covoit.getOrganisateur().getId() == passager.getId()) {
            throw new IllegalArgumentException("Vous ne pouvez pas vous inscrire en tant que passager dans un covoiturage dont vous êtes le conducteur.");
        }

        // On ajoute le passager au covoit et le covoit à la liste des covoiturages du passager
        covoit.getCollaborateurs().add(passager);
        passager.getCovoiturages().add(covoit);
        // Une place en moins dans le covoit !
        nbPlaces--;
        covoit.setNbPlaces(nbPlaces);

        // On sauvegarde les modifications en BDD
        covoiturageRepository.save(covoit);
        collaborateurService.updateCollaborateur(passager);
    }

    /**
     * Recherche des covoiturages selon les critères facultatifs.
     * Tous les paramètres peuvent être null ou vides.
     */
    public Set<Covoiturage> rechercherCovoiturages(
            String villeDep,
            String cpDep,
            String villeArr,
            String cpArr,
            LocalDateTime dateDepart
    ) {
        // Récupération de tous les covoiturages
        Set<Covoiturage> tous = new HashSet<>(covoiturageRepository.findAll());

        // Filtrage en mémoire — simple et efficace pour commencer
        return tous.stream()
                .filter(c -> villeDep == null || villeDep.isEmpty() ||
                        (c.getAdresseDepart() != null &&
                                c.getAdresseDepart().getVille() != null &&
                                c.getAdresseDepart().getVille().toLowerCase().contains(villeDep.toLowerCase())))
                .filter(c -> cpDep == null || cpDep.isEmpty() ||
                        (c.getAdresseDepart() != null &&
                                c.getAdresseDepart().getCodePostal() != null &&
                                c.getAdresseDepart().getCodePostal().toLowerCase().contains(cpDep.toLowerCase())))
                .filter(c -> villeArr == null || villeArr.isEmpty() ||
                        (c.getAdresseArrivee() != null &&
                                c.getAdresseArrivee().getVille() != null &&
                                c.getAdresseArrivee().getVille().toLowerCase().contains(villeArr.toLowerCase())))
                .filter(c -> cpArr == null || cpArr.isEmpty() ||
                        (c.getAdresseArrivee() != null &&
                                c.getAdresseArrivee().getCodePostal() != null &&
                                c.getAdresseArrivee().getCodePostal().toLowerCase().contains(cpArr.toLowerCase())))
                .filter(c -> dateDepart == null || !c.getDate().isBefore(dateDepart))
                .collect(Collectors.toSet());
    }

}
