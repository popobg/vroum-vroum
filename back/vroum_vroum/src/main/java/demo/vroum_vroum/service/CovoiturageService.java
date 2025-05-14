package demo.vroum_vroum.service;

import demo.vroum_vroum.entity.Collaborateur;
import demo.vroum_vroum.entity.Covoiturage;
import demo.vroum_vroum.repository.CovoiturageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CovoiturageService {
    /**
     * Covoiturage repository
     */
    private final CovoiturageRepository covoiturageRepository;

    /**
     * Constructeur CovoiturageService
     * @param covoiturageRepository repository de l'entité Covoiturage
     */
    @Autowired
    public CovoiturageService(CovoiturageRepository covoiturageRepository) {
        this.covoiturageRepository = covoiturageRepository;
    }

    public List<Covoiturage> getMyPassengerCovoit(Collaborateur collaborateur) {
        return covoiturageRepository.findByCollaborateursContaining(collaborateur);
    }

    public Optional<Covoiturage> getCovoiturageById(int id) {
        return covoiturageRepository.findById(id);
    }
}
