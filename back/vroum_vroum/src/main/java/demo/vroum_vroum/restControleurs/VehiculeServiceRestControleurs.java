package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.entity.VehiculeService;
import demo.vroum_vroum.service.VehiculeServiceServ;
import demo.vroum_vroum.exeption.Controle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vehiculeService")
public class VehiculeServiceRestControleurs {

    @Autowired
    VehiculeServiceServ vehiculeServiceServ;

    @GetMapping
    public List<VehiculeService> getAllVehiculeService() {
        Iterable<VehiculeService> vehiculeServices = vehiculeServiceServ.findAll();
        List<VehiculeService> vehiculeServicesList = new ArrayList<>();
        for (VehiculeService vehiculeService : vehiculeServices) {
            vehiculeServicesList.add(vehiculeService);
        }
        return vehiculeServicesList;
    }

    @GetMapping("{id}")
    public VehiculeService getVehiculeServiceById(@PathVariable int id) throws Controle {
        return vehiculeServiceServ.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> postVehiculeService(@RequestBody VehiculeService vehiculeService) throws Controle {
        vehiculeServiceServ.create(vehiculeService);
        return ResponseEntity.ok("Véhicule de service crée avec succès");
    }

    @PutMapping
    public ResponseEntity<String> putVehiculeService(@RequestBody VehiculeService vehiculeService) throws Controle {
        vehiculeServiceServ.update(vehiculeService);
        return ResponseEntity.ok("Le véhicule de service avec l'id :  " + vehiculeService.getId() + " à etait modifiée");
    }

    @DeleteMapping(path ="{id}")
    public ResponseEntity<String> deleteVehiculeService(@PathVariable int id) throws Controle {
        vehiculeServiceServ.delete(id);
        return ResponseEntity.ok().body("Le véhicule de service avec l'id : " + id + " est supprimée");
    }
}



