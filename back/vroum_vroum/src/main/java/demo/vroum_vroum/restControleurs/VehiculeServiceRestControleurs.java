package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.dto.VehiculeServiceDto;
import demo.vroum_vroum.entities.VehiculeService;
import demo.vroum_vroum.mappers.VehiculeServiceMapper;
import demo.vroum_vroum.services.VehiculeServiceServ;
import demo.vroum_vroum.exceptions.Controle;
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

    @Autowired
    VehiculeServiceMapper vehiculeServiceMapper;

    @GetMapping
    public List<VehiculeServiceDto> getAllVehiculeService() {
        Iterable<VehiculeService> vehiculeServices = vehiculeServiceServ.findAll();
        List<VehiculeServiceDto> vehiculeServiceDto = new ArrayList<>();
        for (VehiculeService vehiculeService : vehiculeServices) {
            vehiculeServiceDto.add(vehiculeServiceMapper.toDto(vehiculeService));
        }
        return vehiculeServiceDto;
    }

    @GetMapping("{id}")
    public VehiculeServiceDto getVehiculeServiceById(@PathVariable int id) throws Controle {
        return vehiculeServiceMapper.toDto(vehiculeServiceServ.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> postVehiculeService(@RequestBody VehiculeServiceDto vehiculeServiceDto) throws Controle {
        vehiculeServiceServ.create(vehiculeServiceMapper.toEntity(vehiculeServiceDto));
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



