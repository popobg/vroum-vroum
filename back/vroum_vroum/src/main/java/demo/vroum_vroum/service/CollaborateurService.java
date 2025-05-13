package demo.vroum_vroum.service;

import demo.vroum_vroum.entity.Collaborateur;
import demo.vroum_vroum.repository.CollaborateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
        return collaborateurRepository.findByPseudo(pseudo);
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