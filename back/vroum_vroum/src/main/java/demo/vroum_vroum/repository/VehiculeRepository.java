package demo.vroum_vroum.repository;

import demo.vroum_vroum.entity.Collaborateur;
import demo.vroum_vroum.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {
    Iterable<Vehicule> findVehiculeByCollaborateur(Collaborateur collaborateur);
}
