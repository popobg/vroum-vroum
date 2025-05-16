package demo.vroum_vroum.service;

import demo.vroum_vroum.entity.Collaborateur;
import demo.vroum_vroum.entity.Vehicule;
import demo.vroum_vroum.exeption.Controle;
import demo.vroum_vroum.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculeService {

    @Autowired
    VehiculeRepository vehiculeRepository;

    public Vehicule getVehiculeById(int id) throws Controle {
        if (vehiculeRepository.existsById(id)) {
            return vehiculeRepository.findById(id).get();
        } else {
            throw new Controle("L'id n'existe pas");
        }
    }

    public Iterable<Vehicule> findVehiculeByCollaborateur(Collaborateur collaborateur) throws Controle {
        return vehiculeRepository.findVehiculeByCollaborateur(collaborateur);
    }

    public void create(Vehicule vehicule) throws Controle {
        vehiculeRepository.save(vehicule);
    }

    public void update(Vehicule vehicule) throws Controle {
        vehiculeRepository.save(vehicule);
    }

    public void delete(int id) throws Controle {
        if (vehiculeRepository.existsById(id)) {
            vehiculeRepository.deleteById(id);
        } else {
            throw new Controle("L'id n'existe pas");
        }
    }
}
