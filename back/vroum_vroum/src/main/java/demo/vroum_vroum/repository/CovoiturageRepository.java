package demo.vroum_vroum.repository;

import demo.vroum_vroum.entity.Collaborateur;
import demo.vroum_vroum.entity.Covoiturage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CovoiturageRepository extends JpaRepository<Covoiturage, Integer> {
    public List<Covoiturage> findByCollaborateursContaining(Collaborateur collaborateur);
}
