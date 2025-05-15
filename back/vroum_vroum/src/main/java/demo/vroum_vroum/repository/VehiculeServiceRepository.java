package demo.vroum_vroum.repository;

import demo.vroum_vroum.entity.Collaborateur;
import demo.vroum_vroum.entity.VehiculeService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeServiceRepository extends JpaRepository<VehiculeService,Integer> {
    Iterable<VehiculeService> findVehiculeServiceByCollaborateur(Collaborateur collaborateur);

}
