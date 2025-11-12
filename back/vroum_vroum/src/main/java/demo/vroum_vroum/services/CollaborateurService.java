package demo.vroum_vroum.services;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.exceptions.NotAuthenticatedException;
import demo.vroum_vroum.repositories.CollaborateurRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service lié à l'entité Collaborateur
 */
@Service
public class CollaborateurService implements UserDetailsService {
    /** Repository de l'entité Collaborateur */
    private final CollaborateurRepository collaborateurRepository;

    /**
     * Constructeur
     * @param collaborateurRepository repo Collaborateur
     */
    public CollaborateurService(CollaborateurRepository collaborateurRepository) {
        this.collaborateurRepository = collaborateurRepository;
    }

    /**
     * Récupère l'utilisateur à partir de son pseudo.
     * @param pseudo pseudo utilisateur
     * @return l'optional d'une entité Collaborateur
     */
    public Collaborateur findByPseudo(String pseudo) {
        Optional<Collaborateur> collaborateur = collaborateurRepository.findByPseudo(pseudo);

        if (collaborateur.isEmpty()) {
            throw new EntityNotFoundException("Pas d'utilisateur trouvé avec le pseudo " + pseudo);
        }

        return collaborateur.get();
    }

    /**
     * Récupère l'utilisateur actuellement connecté (si un utilisateur est connecté).
     * @return une entité Collaborateur
     */
    public Collaborateur getCurrentUser() throws EntityNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            return this.findByPseudo(username);
        }

        throw new NotAuthenticatedException("Pas d'utilisateur connecté");
    }

    /**
     * Méthode utilisée par /login géré par Spring Security pour l'authentification.
     * Retourne une entité UserDetails.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Collaborateur> optionalCollaborateur = collaborateurRepository.findByPseudo(username);

        // Si aucun collaborateur trouvé à partir du pseudo
        if (optionalCollaborateur.isEmpty()) {
            throw new UsernameNotFoundException("Collaborateur inconnu");
        }

        Collaborateur collaborateur = optionalCollaborateur.get();

        // Déterminer le rôle en fonction du champ "admin"
        String role = collaborateur.getAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        return new User(collaborateur.getPseudo(), collaborateur.getPassword(), authorities);
    }

    /**
     * Retourne un set de collaborateur
     * @return
     */
    public Set<Collaborateur> getAllCollaborateurs() {
        return collaborateurRepository.findAllCollaborateurs();
    }

    public Collaborateur getCollaborateurById(int id) throws EntityNotFoundException {
        Optional<Collaborateur> collaborateur = collaborateurRepository.findById(id);

        if (collaborateur.isEmpty()) {
            throw new EntityNotFoundException("Pas de collaborateur trouvé à l'ID " + id);
        }

        return collaborateur.get();
    }

    public Collaborateur createCollaborateur(Collaborateur collaborateur) {
        return collaborateurRepository.save(collaborateur);
    }

    public Collaborateur updateCollaborateur(Collaborateur collaborateur) {
        return collaborateurRepository.save(collaborateur);
    }

    public void deleteCollaborateur(int id) throws EntityNotFoundException, RuntimeException {
        if (collaborateurRepository.existsById(id)) {
            collaborateurRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Pas de collaborateur trouvé pour l'ID " + id);
        }
    }

    public Collaborateur saveCollaborateur(Collaborateur collaborateur) {
        return collaborateurRepository.save(collaborateur);
    }
}