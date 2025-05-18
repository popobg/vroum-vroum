package demo.vroum_vroum.services;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.VehiculeService;
import demo.vroum_vroum.exceptions.Controle;
import demo.vroum_vroum.repositories.VehiculeServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculeServiceServ {

    @Autowired
    VehiculeServiceRepository vehiculeServiceRepository;

    public List<VehiculeService> findAll() {return vehiculeServiceRepository.findAll();}

    public VehiculeService findById(int id) throws Controle {
        if (vehiculeServiceRepository.existsById(id)) {
            return vehiculeServiceRepository.findById(id).get();
        } else {
            throw new Controle("L'id n'existe pas");
        }
    }

    public Iterable<VehiculeService> findVehiculeServiceByCollaborateur(Collaborateur collaborateur) throws Controle {
        return vehiculeServiceRepository.findVehiculeServiceByCollaborateurs(collaborateur);
    }

    public void create(VehiculeService vehiculeService) throws Controle {
        vehiculeServiceRepository.save(vehiculeService);
    }

    public void update(VehiculeService vehiculeService) throws Controle {
        vehiculeServiceRepository.save(vehiculeService);
    }

    public void delete(int id) throws Controle {
        if (vehiculeServiceRepository.existsById(id)) {
            vehiculeServiceRepository.deleteById(id);
        } else {
            throw new Controle("L'id n'existe pas.");
        }
    }
}


