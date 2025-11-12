package demo.vroum_vroum.repositories;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {
    Iterable<Vehicule> findVehiculeByCollaborateur(Collaborateur collaborateur);

    @Query("SELECT v FROM Vehicule v")
    Set<Vehicule> findAllVehicules();

}
