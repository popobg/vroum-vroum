package demo.vroum_vroum.services;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.exceptions.NotAuthenticatedException;
import demo.vroum_vroum.repositories.CollaborateurRepository;
import demo.vroum_vroum.utils.Validator;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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

    /** Classe de sécurité permettant de hasher le mot de passe */
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructeur
     * @param collaborateurRepository repo Collaborateur
     * @param passwordEncoder classe de hash du mot de passe
     */
    public CollaborateurService(CollaborateurRepository collaborateurRepository,
    PasswordEncoder passwordEncoder) {
        this.collaborateurRepository = collaborateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Récupère l'utilisateur à partir de son pseudo.
     * @param pseudo pseudo utilisateur
     * @return une entité Collaborateur
     */
    public Collaborateur findByPseudo(String pseudo) throws EntityNotFoundException {
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
     * Retourne un ensemble de collaborateurs.
     * @return un set de collaborateurs
     */
    public Set<Collaborateur> getAllCollaborateurs() {
        return collaborateurRepository.findAllCollaborateurs();
    }

    /**
     * Retourne le collaborateur à partir de son ID.
     * @param id identifiant du collaborateur en BDD
     * @return un collaborateur
     * @throws EntityNotFoundException lève une exception si l'ID du collaborateur n'existe pas en BDD
     */
    public Collaborateur getCollaborateurById(int id) throws EntityNotFoundException {
        Optional<Collaborateur> collaborateur = collaborateurRepository.findById(id);

        if (collaborateur.isEmpty()) {
            throw new EntityNotFoundException("Pas de collaborateur trouvé à l'ID " + id);
        }

        return collaborateur.get();
    }

    /**
     * Sauvegarde un nouveau collaborateur dans la BDD.
     * @param collaborateur le nouveau collaborateur
     * @return le collaborateur sauvegardé en BDD
     */
    public Collaborateur createCollaborateur(Collaborateur collaborateur) {
        if (collaborateur.getId() != 0) {
            throw new IllegalArgumentException("Un nouveau collaborateur ne peut pas déjà avoir d'ID");
        }

        String password = collaborateur.getPassword();

        if (!Validator.isPasswordValid(password)) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 8 caractères, dont au minimum une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial");
        }

        // Hashage du mot de passe
        collaborateur.setPassword(passwordEncoder.encode(password));

        return collaborateurRepository.save(collaborateur);
    }

    /**
     * Modifie les informations d'un collaborateur existant en BDD.
     * @param collaborateur collaborateur existant dont les données ont été modifiées
     * @return le collaborateur modifié
     * @throws EntityNotFoundException lève une exception si l'ID du collaborateur n'existe pas en BDD
     */
    public Collaborateur updateCollaborateur(Collaborateur collaborateur) throws EntityNotFoundException {
        if (!collaborateurRepository.existsById(collaborateur.getId())) {
            throw new EntityNotFoundException("Pas de collaborateur trouvé pour l'ID " + collaborateur.getId());
        }

        return collaborateurRepository.save(collaborateur);
    }

    /**
     * Supprime un collaborateur de la BDD à partir de son ID.
     * @param id identifiant du collaborateur
     * @throws EntityNotFoundException lève une exception si l'ID du collaborateur n'existe pas en BDD
     * @throws RuntimeException lève une exception en cas d'erreur au moment de la suppression du collaborateur
     */
    public void deleteCollaborateur(int id) throws EntityNotFoundException, RuntimeException {
        if (collaborateurRepository.existsById(id)) {
            collaborateurRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Pas de collaborateur trouvé pour l'ID " + id);
        }
    }
}