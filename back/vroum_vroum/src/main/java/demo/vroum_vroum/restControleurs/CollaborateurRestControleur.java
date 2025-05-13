// ==============================
// Collaborateur Rest Controller
// ==============================
package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.entity.Collaborateur;
import demo.vroum_vroum.service.CollaborateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/collaborateur")
public class CollaborateurRestControleur {

    @Autowired
    private CollaborateurService collaborateurService;

    @GetMapping("/me")
    public ResponseEntity<Collaborateur> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Collaborateur currentUser = collaborateurService.findByPseudo(username);
        if (currentUser != null) {
            return ResponseEntity.ok(currentUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // GET all collaborateurs
    @GetMapping
    public List<Collaborateur> getAllCollaborateurs() {
        return collaborateurService.getAllCollaborateurs();
    }

    // GET collaborateur by ID
    @GetMapping("/{id}")
    public Optional<Collaborateur> getCollaborateurById(@PathVariable int id) {
        return collaborateurService.getCollaborateurById(id);
    }

    // POST new collaborateur
    @PostMapping
    public Collaborateur addCollaborateur(@RequestBody Collaborateur collaborateur) {
        return collaborateurService.saveCollaborateur(collaborateur);
    }

    // PUT update collaborateur
    @PutMapping("/{id}")
    public Collaborateur updateCollaborateur(@PathVariable int id, @RequestBody Collaborateur collaborateur) {
        collaborateur.setId(id);
        return collaborateurService.saveCollaborateur(collaborateur);
    }

    // DELETE collaborateur by ID
    @DeleteMapping("/{id}")
    public void deleteCollaborateur(@PathVariable int id) {
        collaborateurService.deleteCollaborateur(id);
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
