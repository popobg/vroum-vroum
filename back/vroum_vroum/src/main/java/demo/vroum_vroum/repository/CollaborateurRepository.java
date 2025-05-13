package demo.vroum_vroum.repository;

import demo.vroum_vroum.entity.Collaborateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaborateurRepository extends JpaRepository<Collaborateur, Integer> {
}
