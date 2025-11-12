package demo.vroum_vroum.services;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Vehicule;
import demo.vroum_vroum.exceptions.Controle;
import demo.vroum_vroum.repositories.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour la gestion des véhicules.
 * Contient la logique de validation et les appels au repository.
 */
@Service
public class VehiculeService {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    /**
     * Récupère tous les véhicules.
     * @return liste de tous les véhicules
     */
    public List<Vehicule>   findAll() {
        return vehiculeRepository.findAll();
    }

    /**
     * Recherche un véhicule par identifiant.
     * @param id identifiant du véhicule
     * @return véhicule trouvé
     * @throws Controle si l'id n'existe pas
     */
    public Vehicule getVehiculeById(int id) throws Controle {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new Controle("L'id " + id + " n'existe pas"));
    }

    /**
     * Recherche les véhicules associés à un collaborateur donné.
     * @param collaborateur collaborateur cible
     * @return liste de véhicules associés
     */
    public Iterable<Vehicule> findVehiculeByCollaborateur(Collaborateur collaborateur) {
        return vehiculeRepository.findVehiculeByCollaborateur(collaborateur);
    }

    /**
     * Crée un nouveau véhicule.
     * @param vehicule objet à enregistrer
     */
    public void create(Vehicule vehicule) {
        vehiculeRepository.save(vehicule);
    }

    /**
     * Met à jour un véhicule existant.
     * @param vehicule objet à mettre à jour
     */
    public void update(Vehicule vehicule) {
        vehiculeRepository.save(vehicule);
    }

    /**
     * Supprime un véhicule par son identifiant.
     * @param id identifiant du véhicule
     * @throws Controle si l'id n'existe pas
     */
    public void delete(int id) throws Controle {
        if (!vehiculeRepository.existsById(id)) {
            throw new Controle("L'id " + id + " n'existe pas");
        }
        vehiculeRepository.deleteById(id);
    }
}