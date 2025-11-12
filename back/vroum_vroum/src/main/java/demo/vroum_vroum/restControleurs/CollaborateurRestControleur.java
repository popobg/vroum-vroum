// ==============================
// Collaborateur Rest Controller
// ==============================
package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.dto.CollaborateurLiteDto;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.exceptions.NotAuthenticatedException;
import demo.vroum_vroum.mappers.CollaborateurMapper;
import demo.vroum_vroum.services.CollaborateurService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/collaborateur")
public class CollaborateurRestControleur {

    /** Service de l'entité Collaborateur */
    private final CollaborateurService collaborateurService;

    /** Classe de sécurité permettant d'encoder le mot de passe */
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructeur de la classe CollaborateurRestControleur
     *
     * @param collaborateurService service collaborateur
     * @param passwordEncoder classe permettant l'encodage du mot de passe
     */
    public CollaborateurRestControleur(CollaborateurService collaborateurService, PasswordEncoder passwordEncoder) {
        this.collaborateurService = collaborateurService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Récupère les informations lite de l'utilisateur (ou collaborateur) connecté.
     *
     * @return un collaborateur lite
     */
    @GetMapping("/me")
    public ResponseEntity<CollaborateurLiteDto> getCurrentUserLite(@RequestParam(required = false) String pseudo) throws EntityNotFoundException, NotAuthenticatedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Collaborateur collaborateur = collaborateurService.findByPseudo(username);

        return ResponseEntity.ok(CollaborateurMapper.toLiteDto(collaborateur));
    }

    /**
     * Récupère la liste de tous les collaborateurs inscrits sur l'application.
     *
     * @return un set de collaborateurs lite
     */
    @GetMapping
    public Set<CollaborateurLiteDto> getAllCollaborateurs() {
        Set<Collaborateur> collaborateurs = collaborateurService.getAllCollaborateurs();

        if (collaborateurs.size() > 0) {
            return CollaborateurMapper.toLiteDtos(collaborateurs);
        }
        return Set.of();
    }

    /**
     * Récupère les informations complètes d'un collaborateur.
     * Réservé aux admins de l'application.
     *
     * @param id Id d'un collaborateur
     * @return l'entité Collaborateur demandée
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collaborateur> getCollaborateurById(@PathVariable int id) throws EntityNotFoundException {
        Collaborateur collaborateur = collaborateurService.getCollaborateurById(id);

        return ResponseEntity.ok(collaborateur);
    }

    /**
     * Ajoute un collaborateur (/utilisateur) à l'application.
     * Réservé aux admins de l'application.
     *
     * @param collaborateur informations du nouveau collaborateur
     * @return l'entité collaborateur créée
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CollaborateurLiteDto addCollaborateur(@RequestBody Collaborateur collaborateur) {
        String password = collaborateur.getPassword();
        collaborateur.setPassword(passwordEncoder.encode(password));
        return CollaborateurMapper.toLiteDto(collaborateurService.saveCollaborateur(collaborateur));
    }

    /**
     * Met à jour les informations d'un collaborateur.
     *
     * @param id Id du collaborateur à modifier
     * @param collaborateur le collaborateur avec informations modifiées
     * @return le collaborateur avec ses informations modifiées
     */
    @PutMapping("/{id}")
    public Collaborateur updateCollaborateur(@PathVariable int id, @RequestBody Collaborateur collaborateur) {
        collaborateur.setId(id);
        return collaborateurService.saveCollaborateur(collaborateur);
    }

    /**
     * Supprime un collaborateur de l'application. Il ne pourra plus se connecter à celle-ci.
     * Réservé aux admins de l'application.
     *
     * @param id Id du collaborateur à supprimer
     * @return statut HTTP 204 (no content) si suppression réussie, sinon statut 404 ou 500
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCollaborateur(@PathVariable int id) throws EntityNotFoundException, RuntimeException {
        collaborateurService.deleteCollaborateur(id);
        // Réponse 204 : suppression effectuée avec succès
        return ResponseEntity.noContent().build();
    }

    /**
     * Supprime le compte de l'utilisateur connecté. Action demandée par lui-même.
     *
     * @return statut HTTP 204 si suppression réussie, sinon statut 404 ou 500
     * @throws EntityNotFoundException
     * @throws NotAuthenticatedException
     * @throws RuntimeException
     */
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUtilisateurConnecte() throws EntityNotFoundException, NotAuthenticatedException, RuntimeException {
        // Récupère l'utilisateur connecté
        Collaborateur collaborateur = collaborateurService.getCurrentUser();

        collaborateurService.deleteCollaborateur(collaborateur.getId());
        // Réponse 204 : suppression effectuée avec succès
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
