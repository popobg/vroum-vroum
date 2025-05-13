package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.dto.CovoiturageDTO;
import demo.vroum_vroum.service.CovoiturageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/covoiturage")
public class CovoiturageRestControleur {

    private CovoiturageService covoiturageService;

    @Autowired
    public CovoiturageRestControleur(CovoiturageService covoiturageService) {
        this.covoiturageService = covoiturageService;
    }

    @GetMapping("/myList")
    public List<CovoiturageDTO> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentId = authentication.getId();
            return currentUserName;
        }
        return covoiturageService.getAll();
    }

    @GetMapping("/details")
    public CovoiturageDTO getById(int id) {
        return covoiturageService.getById(id);
    }
}
