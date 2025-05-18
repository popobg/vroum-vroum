package demo.vroum_vroum.repositories;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {
    Iterable<Vehicule> findVehiculeByCollaborateur(Collaborateur collaborateur);
}
