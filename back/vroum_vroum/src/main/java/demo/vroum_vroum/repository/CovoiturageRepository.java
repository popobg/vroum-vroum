package demo.vroum_vroum.repository;

import demo.vroum_vroum.entity.Covoiturage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CovoiturageRepository extends JpaRepository<Covoiturage, Integer> {
}
