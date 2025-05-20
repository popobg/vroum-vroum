package demo.vroum_vroum.repositories;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.VehiculeService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeServiceRepository extends JpaRepository<VehiculeService,Integer> {
    Iterable<VehiculeService> findVehiculeServiceByCollaborateurs(Collaborateur collaborateur);

}
