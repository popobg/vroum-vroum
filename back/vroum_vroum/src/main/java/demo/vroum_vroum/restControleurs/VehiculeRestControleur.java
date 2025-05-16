package demo.vroum_vroum.restControleurs;


import demo.vroum_vroum.service.VehiculeServiceServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicule")
public class VehiculeRestControleur {

    @Autowired
    VehiculeServiceServ vehiculeService;
}
