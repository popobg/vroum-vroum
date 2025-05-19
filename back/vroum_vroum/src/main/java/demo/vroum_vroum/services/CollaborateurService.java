package demo.vroum_vroum.services;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.repositories.CollaborateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class CollaborateurService implements UserDetailsService {
    /** Repository de l'entité Collaborateur */
    private final CollaborateurRepository collaborateurRepository;

    /**
     * Constructeur
     * @param collaborateurRepository repo Collaborateur
     */
    @Autowired
    public CollaborateurService(CollaborateurRepository collaborateurRepository) {
        this.collaborateurRepository = collaborateurRepository;
    }


    public Collaborateur findByPseudo(String pseudo) {
        return collaborateurRepository.findByPseudo(pseudo);
    }

    public Collaborateur getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            return this.findByPseudo(username);
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collaborateur collaborateur = collaborateurRepository.findByPseudo(username);
        if (collaborateur == null) {
            throw new RuntimeException("Collaborateur not found");
        }

        // Déterminer le rôle en fonction du champ "admin"
        String role = collaborateur.getAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        return new User(collaborateur.getPseudo(), collaborateur.getPassword(), authorities);
    }


    public List<Collaborateur> getAllCollaborateurs() {
        return collaborateurRepository.findAll();
    }

    public Optional<Collaborateur> getCollaborateurById(int id) {
        return collaborateurRepository.findById(id);
    }

    public Collaborateur createCollaborateur(Collaborateur collaborateur) {
        return collaborateurRepository.save(collaborateur);
    }

    public Collaborateur updateCollaborateur(Collaborateur collaborateur) {
        return collaborateurRepository.save(collaborateur);
    }

    public void deleteCollaborateur(int id) {
        collaborateurRepository.deleteById(id);
    }

    public Collaborateur saveCollaborateur(Collaborateur collaborateur) {
        return collaborateurRepository.save(collaborateur);
    }

    public Optional<Collaborateur> login(String pseudo, String password) {
        return collaborateurRepository.findByPseudoAndPassword(pseudo, password);
    }
}