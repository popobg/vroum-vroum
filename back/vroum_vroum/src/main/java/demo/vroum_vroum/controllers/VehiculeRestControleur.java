package demo.vroum_vroum.controllers;

import demo.vroum_vroum.entities.Vehicule;
import demo.vroum_vroum.exceptions.Controle;
import demo.vroum_vroum.services.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des véhicules.
 * Fournit les opérations CRUD :
 * - GET /vehicule
 * - GET /vehicule/{id}
 * - POST /vehicule
 * - PUT /vehicule
 * - DELETE /vehicule/{id}
 */
@RestController
@RequestMapping("/vehicule")
public class VehiculeRestControleur {

    @Autowired
    private VehiculeService vehiculeService;

    /**
     * Récupère tous les véhicules.
     * @return liste des véhicules
     */
    @GetMapping
    public List<Vehicule> getAllVehicules() {
        return vehiculeService.findAll();
    }

    /**
     * Récupère un véhicule par son identifiant.
     * @param id identifiant du véhicule
     * @return véhicule correspondant
     * @throws Controle si l'id n'existe pas
     */
    @GetMapping("/{id}")
    public Vehicule getVehiculeById(@PathVariable int id) throws Controle {
        return vehiculeService.getVehiculeById(id);
    }

    /**
     * Crée un nouveau véhicule.
     * @param vehicule objet véhicule à créer
     * @return message de confirmation
     * @throws Controle si erreur de validation
     */
    @PostMapping
    public ResponseEntity<String> createVehicule(@RequestBody Vehicule vehicule) throws Controle {
        vehiculeService.create(vehicule);
        return ResponseEntity.ok("Véhicule créé avec succès");
    }

    /**
     * Met à jour un véhicule existant.
     * @param vehicule objet véhicule à mettre à jour
     * @return message de confirmation
     * @throws Controle si erreur de validation
     */
    @PutMapping
    public ResponseEntity<String> updateVehicule(@RequestBody Vehicule vehicule) throws Controle {
        vehiculeService.update(vehicule);
        return ResponseEntity.ok("Véhicule avec l'id " + vehicule.getId() + " mis à jour");
    }

    /**
     * Supprime un véhicule par son identifiant.
     * @param id identifiant du véhicule
     * @return message de confirmation
     * @throws Controle si l'id n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicule(@PathVariable int id) throws Controle {
        vehiculeService.delete(id);
        return ResponseEntity.ok("Véhicule avec l'id " + id + " supprimé");
    }
}