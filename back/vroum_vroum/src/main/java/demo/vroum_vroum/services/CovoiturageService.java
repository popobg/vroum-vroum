package demo.vroum_vroum.services;

import demo.vroum_vroum.dto.CovoiturageDto;
import demo.vroum_vroum.entities.Adresse;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Covoiturage;
import demo.vroum_vroum.entities.Vehicule;
import demo.vroum_vroum.exceptions.Controle;
import demo.vroum_vroum.mappers.CovoiturageMapper;
import demo.vroum_vroum.repositories.CovoiturageRepository;
import demo.vroum_vroum.utils.Validator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Service en lien avec le covoiturage
 */
@Service
public class CovoiturageService {
    /** Covoiturage repository */
    private final CovoiturageRepository covoiturageRepository;
    private final CollaborateurService collaborateurService;
    private final VehiculeService vehiculeService;

    /**
     * Constructeur CovoiturageService
     * @param covoiturageRepository repository de l'entité Covoiturage
     */
    public CovoiturageService(CovoiturageRepository covoiturageRepository, CollaborateurService collaborateurService, VehiculeService vehiculeService) {
        this.covoiturageRepository = covoiturageRepository;
        this.collaborateurService = collaborateurService;
        this.vehiculeService = vehiculeService;
    }

    /**
     * Crée un nouveau covoiturage
     * @param dto DTO contenant toutes les informations nécessaires
     * @param idOrganisateur ID du collaborateur conducteur
     * @param idVehicule ID du véhicule utilisé
     * @return le covoiturage créé
     * @throws EntityNotFoundException si collaborateur ou véhicule introuvable
     * @throws IllegalArgumentException si données invalides (places, date, adresses)
     */
    public Covoiturage creerCovoiturage(CovoiturageDto dto, int idOrganisateur, int idVehicule) throws EntityNotFoundException, IllegalArgumentException, Controle {

        Collaborateur organisateur = collaborateurService.getCollaborateurById(idOrganisateur);
        Vehicule vehicule = vehiculeService.getVehiculeById(idVehicule);

        // Vérification date
        if (!Validator.matchDateUlterieure(dto.getDate())) {
            throw new IllegalArgumentException("La date de départ doit être ultérieure à la date et heure actuelle.");
        }

        // Vérification adresses
        Adresse adresseDepart = CovoiturageMapper.toEntity(dto).getAdresseDepart();
        Adresse adresseArrivee = CovoiturageMapper.toEntity(dto).getAdresseArrivee();

        if (adresseDepart == null || adresseArrivee == null) {
            throw new IllegalArgumentException("Les adresses de départ et d'arrivée doivent être renseignées.");
        }

        // Vérification nombre de places
        if (dto.getNbPlaces() <= 0) {
            throw new IllegalArgumentException("Le nombre de places doit être supérieur à 0.");
        }

        // Création de l'entité Covoiturage
        Covoiturage covoiturage = new Covoiturage();
        covoiturage.setDate(dto.getDate());
        covoiturage.setAdresseDepart(adresseDepart);
        covoiturage.setAdresseArrivee(adresseArrivee);
        covoiturage.setNbPlaces(dto.getNbPlaces());
        covoiturage.setDistance(dto.getDistance());
        covoiturage.setDuree(dto.getDuree());
        covoiturage.setOrganisateur(organisateur);
        covoiturage.setVehicule(vehicule);
        covoiturage.setCollaborateurs(new HashSet<>()); // pas encore de passagers

        // Sauvegarde en base
        return covoiturageRepository.save(covoiturage);
    }

    /**
     * Méthode de service récupérant les covoiturages organisés par un collaborateur.
     *
     * @param idCollaborateur identifiant du collaborateur organisateur
     * @return liste de covoiturages organisés
     */
    public Set<Covoiturage> getMesCovoituragesOrganises(int idCollaborateur) throws EntityNotFoundException {
        Collaborateur collaborateur = collaborateurService.getCollaborateurById(idCollaborateur);

        // On filtre tous les covoiturages où l'organisateur est ce collaborateur
        return covoiturageRepository.findByOrganisateur(collaborateur);
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
    public Set<Covoiturage> getCovoitDisponiblesByAdressesDate(String villeDepart, String codePostalDepart, String villeArrivee, String codePostalArrivee, LocalDateTime dateDepart) throws IllegalArgumentException {
        if (villeDepart.isEmpty()
                || codePostalDepart.isEmpty()
                || villeArrivee.isEmpty()
                || codePostalArrivee.isEmpty()) {
            throw new IllegalArgumentException("Vous devez fournir une ville de départ, un code postal de départ, une ville d'arrivée et un code postal d'arrivée.");
        }

        if (!Validator.matchCodePostalFormat(codePostalDepart)
                || !Validator.matchCodePostalFormat(codePostalArrivee)) {
            throw new IllegalArgumentException("Le code postal doit faire 5 caractères.");
        }

        if (!Validator.matchDateUlterieure(dateDepart)) {
            throw new IllegalArgumentException("La date de départ recherchée doit être ultérieure à la date et heure actuelle.");
        }

        return covoiturageRepository.findCovoitDisponiblesByAdressesDate(villeDepart, codePostalDepart, villeArrivee, codePostalArrivee, dateDepart);
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
        // Une place disponible de plus dans le covoiturage !
        covoit.setNbPlaces(covoit.getNbPlaces() + 1);

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
}
