// ==============================
// Collaborateur Rest Controller
// ==============================
package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.dto.CollaborateurDto;
import demo.vroum_vroum.dto.CollaborateurLiteDto;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.exceptions.NotAuthenticatedException;
import demo.vroum_vroum.mappers.CollaborateurMapper;
import demo.vroum_vroum.services.CollaborateurService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST controller de l'entité Collaborateur.
 */
@RestController
@RequestMapping("/collaborateur")
public class CollaborateurRestControleur {

    /** Service de l'entité Collaborateur */
    private final CollaborateurService collaborateurService;

    /**
     * Constructeur de la classe CollaborateurRestControleur
     *
     * @param collaborateurService service collaborateur
     */
    public CollaborateurRestControleur(CollaborateurService collaborateurService) {
        this.collaborateurService = collaborateurService;
    }

    /**
     * Récupère les informations lite de l'utilisateur (ou collaborateur) connecté.
     *
     * @return un collaborateur lite
     */
    @GetMapping("/me")
    public ResponseEntity<CollaborateurLiteDto> getCurrentUserLite(@RequestParam(required = false) String pseudo) {
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
     *
     * @param id Id d'un collaborateur
     * @return l'entité Collaborateur demandée
     */
    @GetMapping("/{id}")
    public ResponseEntity<CollaborateurDto> getCollaborateurById(@PathVariable Integer id) {
        Collaborateur collaborateur = collaborateurService.getCollaborateurById(id);
        return ResponseEntity.ok(CollaborateurMapper.toDto(collaborateur));
    }

    /**
     * Ajoute un collaborateur (/utilisateur) à l'application.
     * Réservé aux admins de l'application.
     *
     * @param collaborateurDto informations du nouveau collaborateur
     * @param validationResult validation des champs du collaborateurDto
     * @return le CollaborateurDto sauvegardé en BDD
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CollaborateurDto addCollaborateur(@Valid @RequestBody CollaborateurDto collaborateurDto, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new IllegalArgumentException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }

        Collaborateur collaborateur = CollaborateurMapper.toEntity(collaborateurDto);
        return CollaborateurMapper.toDto(collaborateurService.createCollaborateur(collaborateur));
    }

    /**
     * Met-à-jour les informations d'un collaborateur.
     *
     * @param collaborateurDto informations modifiées du collaborateur
     * @param validationResult validation des champs du collaborateurDto
     * @return le CollaborateurDto modifié
     */
    @PutMapping
    public CollaborateurDto updateCollaborateur(@Valid @RequestBody CollaborateurDto collaborateurDto, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new IllegalArgumentException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }

        Collaborateur collaborateur = CollaborateurMapper.toEntity(collaborateurDto);
        return CollaborateurMapper.toDto(collaborateurService.updateCollaborateur(collaborateur));
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
    public ResponseEntity<Void> deleteCollaborateur(@PathVariable Integer id) {
        collaborateurService.deleteCollaborateur(id);
        // Réponse 204 : suppression effectuée avec succès
        return ResponseEntity.noContent().build();
    }

    /**
     * Supprime le compte de l'utilisateur connecté. Action demandée par lui-même.
     *
     * @return statut HTTP 204 si suppression réussie, sinon statut 404 ou 500
     */
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUtilisateurConnecte() {
        // Récupère l'utilisateur connecté
        Collaborateur collaborateur = collaborateurService.getCurrentUser();

        collaborateurService.deleteCollaborateur(collaborateur.getId());
        // Réponse 204 : suppression effectuée avec succès
        return ResponseEntity.noContent().build();
    }
}
