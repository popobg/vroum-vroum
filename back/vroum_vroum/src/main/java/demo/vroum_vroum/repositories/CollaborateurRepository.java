package demo.vroum_vroum.repositories;

import demo.vroum_vroum.entities.Collaborateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

/**
 * Custom repository de l'entité Collaborateur
 */
public interface CollaborateurRepository extends JpaRepository<Collaborateur, Integer> {
    /**
     * Query permettant de récupérer tous les collaborateurs
     * @return un set de collaborateurs
     */
    @Query("SELECT c FROM Collaborateur c")
    Set<Collaborateur> findAllCollaborateurs();

    /**
     * Query permettant de trouver (ou non) un collaborateur à partir de son pseudo
     * @param pseudo pseudo utilisateur
     * @return un collaborateur ou un optional vide
     */
    Optional<Collaborateur> findByPseudo(String pseudo);
}
