package demo.vroum_vroum.services;

import demo.vroum_vroum.entities.Adresse;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Covoiturage;
import demo.vroum_vroum.repositories.CollaborateurRepository;
import demo.vroum_vroum.repositories.CovoiturageRepository;
import demo.vroum_vroum.utils.Validator;
import jakarta.persistence.EntityNotFoundException;
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
    /** Messages d'erreur */
    private final String DATE_ERROR_MESSAGE = "La date-heure du covoiturage doit être ultérieure à la date-heure actuelle.";

    /** Covoiturage repository */
    private final CovoiturageRepository covoiturageRepository;

    /** Collaborateur repository */
    private final CollaborateurRepository collaborateurRepository;
    private final AdresseService adresseService;

    /**
     * Constructeur CovoiturageService
     * @param covoiturageRepository repository de l'entité Covoiturage
     */
    @Autowired
    public CovoiturageService(CovoiturageRepository covoiturageRepository, CollaborateurRepository collaborateurRepository, AdresseService adresseService) {
        this.covoiturageRepository = covoiturageRepository;
        this.collaborateurRepository = collaborateurRepository;
        this.adresseService = adresseService;
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
        // Check validité des infomations liées à l'adresse
        String checkAdresseDepart = adresseService.checkChampsAdresse(new Adresse(villeDepart, codePostalDepart));

        if (!checkAdresseDepart.isEmpty()) {
            throw new IllegalArgumentException("Ville départ : " + checkAdresseDepart);
        }

        String checkAdresseArrivee = adresseService.checkChampsAdresse(new Adresse(villeArrivee, codePostalArrivee));

        if (!checkAdresseArrivee.isEmpty()) {
            throw new IllegalArgumentException("Ville d'arrivée : " + checkAdresseDepart);
        }

        // On ne peut rechercher que des covoiturages à venir
        if (!Validator.matchDateUlterieure(dateDepart)) {
            throw new IllegalArgumentException(DATE_ERROR_MESSAGE);
        }

        return covoiturageRepository.findCovoitDisponiblesByAdressesDate(villeDepart, codePostalDepart, villeArrivee, codePostalArrivee, dateDepart);
    }

    /**
     * Méthode de service récupérant un covoiturage à partir de son Id
     * @param id Id du covoiturage
     * @return un covoiturage ou null si pas de covoiturage trouvé à cet Id
     */
    public Covoiturage getCovoiturageById(int id) {
        Optional<Covoiturage> optCovoit = covoiturageRepository.findById(id);

        if (optCovoit.isEmpty()) {
            throw new EntityNotFoundException("Aucun covoiturage ne correspond à l'ID " + id + " dans la base de données.");
        }

        return optCovoit.get();
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
     * Méthode de service récupérant les covoiturages organisés par un collaborateur spécifique
     * @param organisateur collaborateur organisateur du covoiturage
     * @return liste de covoiturages
     */
    public List<Covoiturage> getMesAnnoncesCovoit(Collaborateur organisateur) {
        return covoiturageRepository.findByOrganisateur(organisateur);
    }

    /**
     * Méthode de service permettant de persister un objet Covoiturage en base de données
     * @param covoiturage un objet Covoiturage
     * @return true si l'ajout en BDD est un succès, sinon false
     */
    public Boolean sauvegarderAnnonceCovoit(Covoiturage covoiturage) {
        // Check les champs date, code postal et voiture
        String check = checkChampsCovoiturage(covoiturage);
        if (!check.isEmpty()) {
            throw new UnsupportedOperationException(check);
        }

        // On cherche si les adresses existent déjà en base

        // On cherche si le véhicule existe déjà en base


        // Sauvegarde du covoiturage en base de données
        covoiturageRepository.save(covoiturage);
        return true;
    }

    /**
     * Méthode de service permettant de modifier un objet Covoiturage existant en base de données,
     * à condition que celui-ci n'ait pas déjà de réservation
     * @param covoiturage un objet Covoiturage existant
     * @return true si la modification est un succès, sinon false
     */
    public Boolean modifierAnnonceCovoit(Covoiturage covoiturage) {
        // Si l'ID n'existe pas en BDD, une exception sera levée
        Covoiturage covoitExistant = getCovoiturageById(covoiturage.getId());

        if (!covoitExistant.getCollaborateurs().isEmpty()) {
            throw new UnsupportedOperationException("Vous ne pouvez pas modifier une annonce pour un covoiturage comportant déjà des réservations.");
        }

        if (covoitExistant.getOrganisateur().getId() != covoiturage.getOrganisateur().getId()) {
            throw new UnsupportedOperationException("Vous ne pouvez pas changer l'organisateur d'un covoiturage.");
        }

        // Sauvegarde et check des champs de saisie via la même méthode que celle de création des entités
        sauvegarderAnnonceCovoit(covoiturage);

        return true;
    }

    /**
     * Méthode permettant de supprimer une réservation de covoiturage pour un passager
     * @param idCovoit Id du covoiturage
     * @param passager Id du collaborateur passager
     * @return true si la suppression est un succès, false sinon
     */
    public Boolean annulerReservationCovoit(int idCovoit, Collaborateur passager) {
        // Check de l'ID dans la méthode getCovoiturageById
        Covoiturage covoit = this.getCovoiturageById(idCovoit);

        // Impossible d'annuler si le covoit est déjà passé
        if (!Validator.matchDateUlterieure(covoit.getDate())) {
            throw new UnsupportedOperationException(DATE_ERROR_MESSAGE);
        }

        try {
            // On supprime le passager du covoit et le covoit des réservations du passager
            covoit.getCollaborateurs().remove(passager);
            passager.getCovoiturages().remove(covoit);

            // On sauvegarde les modifications en BDD
            covoiturageRepository.save(covoit);
            collaborateurRepository.save(passager);

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Méthode permettant d'ajouter un passager à un covoiturage disponible
     * @param idCovoit Id du covoiturage
     * @param passager Id du passager
     * @return true si la réservation est un succès, sinon false
     */
    public Boolean reserverCovoit(int idCovoit, Collaborateur passager) {
        Covoiturage covoit = this.getCovoiturageById(idCovoit);

        if (!Validator.matchDateUlterieure(covoit.getDate())) {
            throw new UnsupportedOperationException(DATE_ERROR_MESSAGE);
        }

        int nbPlaces = covoit.getNbPlaces();

        if (nbPlaces == 0) {
            throw new UnsupportedOperationException("Ce covoiturage ne présente plus de places disponibles.");
        }

        if (covoit.getCollaborateurs().contains(passager)) {
            throw new UnsupportedOperationException("Vous êtes déjà inscrit dans ce coivoiturage.");
        }

        if (covoit.getOrganisateur().getId() == passager.getId()) {
            throw new UnsupportedOperationException("Vous ne pouvez pas vous inscrire en tant que passager dans un covoiturage dont vous êtes le conducteur.");
        }

        try {
            // On ajoute le passager au covoit et le covoit à la liste des covoiturages du passager
            covoit.getCollaborateurs().add(passager);
            passager.getCovoiturages().add(covoit);
            // Une place en moins dans le covoit !
            nbPlaces--;
            covoit.setNbPlaces(nbPlaces);

            // On sauvegarde les modifications en BDD
            covoiturageRepository.save(covoit);
            collaborateurRepository.save(passager);

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Boolean supprimerAnnonceCovoit(int idCovoit, int idOrganisateur) {
        Covoiturage covoit = this.getCovoiturageById(idCovoit);

        if (covoit.getOrganisateur().getId() != idOrganisateur) {
            throw new IllegalCallerException("Seul l'organisateur du covoiturage peut le supprimer.");
        }

        covoiturageRepository.delete(covoit);
        return true;
    }

    /*
     * METHODES PRIVEES
     */

    /**
     * Méthode privée de vérification des champs de l'objet Covoiturage à sa création ou modification
     * @param covoiturage objet Covoiturage
     * @return Message d'erreur ou String vide
     */
    private String checkChampsCovoiturage(Covoiturage covoiturage) {
        if (!Validator.matchDateUlterieure(covoiturage.getDate())) {
           return DATE_ERROR_MESSAGE;
        }

        if (covoiturage.getVehicule().getCollaborateur().getId() != covoiturage.getOrganisateur().getId()) {
            return "Le véhicule doit appartenir au collaborateur organisateur du covoiturage.";
        }

        String champAdresseDepart = adresseService.checkChampsAdresse(covoiturage.getAdresseDepart());

        if (!champAdresseDepart.isEmpty()) {
            return champAdresseDepart;
        }

        String champAdresseArrivee = adresseService.checkChampsAdresse(covoiturage.getAdresseArrivee());

        if (!champAdresseArrivee.isEmpty()) {
            return champAdresseArrivee;
        }

        return "";
    }
}
