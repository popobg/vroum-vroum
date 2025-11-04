// ==============================
// Collaborateur Rest Controller
// ==============================
package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.dto.CollaborateurLiteDto;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.mappers.CollaborateurMapper;
import demo.vroum_vroum.services.CollaborateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/collaborateur")
public class CollaborateurRestControleur {

    @Autowired
    private CollaborateurService collaborateurService;

    @GetMapping("/me")
    public ResponseEntity<CollaborateurLiteDto> getCurrentUserLite(@RequestParam(required = false) String pseudo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Collaborateur currentUser = collaborateurService.findByPseudo(username);
        if (currentUser != null) {
            CollaborateurLiteDto dto = CollaborateurMapper.toLiteDto(currentUser);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public List<Collaborateur> getAllCollaborateurs() {
        return collaborateurService.getAllCollaborateurs();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Collaborateur>> getCollaborateurById(@PathVariable int id) {
        return ResponseEntity.ok(collaborateurService.getCollaborateurById(id));
    }

    @PostMapping
    public Collaborateur addCollaborateur(@RequestBody Collaborateur collaborateur) {
        return collaborateurService.saveCollaborateur(collaborateur);
    }

    @PutMapping("/{id}")
    public Collaborateur updateCollaborateur(@PathVariable int id, @RequestBody Collaborateur collaborateur) {
        collaborateur.setId(id);
        return collaborateurService.saveCollaborateur(collaborateur);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCollaborateur(@PathVariable int id) {
        collaborateurService.deleteCollaborateur(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String pseudo, @RequestParam String password) {
        Optional<Collaborateur> collaborateur = collaborateurService.login(pseudo, password);
        if (collaborateur.isPresent()) {
            return ResponseEntity.ok("Connexion réussie");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects");
    }
}
