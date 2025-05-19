package demo.vroum_vroum.repositories;

import demo.vroum_vroum.entities.Collaborateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollaborateurRepository extends JpaRepository<Collaborateur, Integer> {
    Collaborateur findByPseudo(String pseudo);

    Optional<Collaborateur> findByPseudoAndPassword(String pseudo, String password);
}
