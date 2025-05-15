package demo.vroum_vroum.service;

import demo.vroum_vroum.entity.Collaborateur;
import demo.vroum_vroum.repository.CollaborateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollaborateurService implements UserDetailsService {
    @Autowired
    private CollaborateurRepository collaborateurRepository;

    public Collaborateur findByPseudo(String pseudo) {
        return collaborateurRepository.findByPseudo(pseudo);
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