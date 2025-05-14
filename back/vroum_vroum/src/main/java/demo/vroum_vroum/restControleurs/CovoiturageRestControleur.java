package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.dto.CovoiturageDTO;
import demo.vroum_vroum.entity.Collaborateur;
import demo.vroum_vroum.entity.Covoiturage;
import demo.vroum_vroum.mappers.CovoiturageMapper;
import demo.vroum_vroum.service.CollaborateurService;
import demo.vroum_vroum.service.CovoiturageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/covoiturage")
public class CovoiturageRestControleur {
    /**
     * Service concernant le covoiturage
     */
    private final CovoiturageService covoiturageService;

    /**
     * service concernant les collaborateurs
     */
    private final CollaborateurService collaborateurService;

    /**
     * Constructeur CovoiturageRestControleur
     * @param covoiturageService service pour le covoiturage
     * @param collaborateurService service pour les collaborateurs
     */
    @Autowired
    public CovoiturageRestControleur(CovoiturageService covoiturageService, CollaborateurService collaborateurService) {
        this.covoiturageService = covoiturageService;
        this.collaborateurService = collaborateurService;
    }

    @GetMapping("/all")
    public List<CovoiturageDTO> getAllCovoitBy(@RequestParam adresseDepart) {

    }

    /**
     * Méthode retournant les informations d'un covoiturage
     * @param id Id du covoiturage recherché
     * @return un covoiturage dto
     */
    @GetMapping("/details/{id}")
    public CovoiturageDTO getById(@PathVariable int id) {
        Optional<Covoiturage> covoit = covoiturageService.getCovoiturageById(id);

        return covoit.map(CovoiturageMapper::toDto).orElse(null);
    }

    /**
     * Méthode retournant les covoiturages auxquels participe l'utilisateur connecté.
     * @return liste de covoiturages dto
     */
    @GetMapping("/myPassengerCovoit")
    public List<CovoiturageDTO> getMyPassengerCovoit() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Collaborateur currentUser = collaborateurService.findByPseudo(username);

            List<Covoiturage> covoit = covoiturageService.getMyPassengerCovoit(currentUser);

        }
        return null;
    }
}
