package demo.vroum_vroum.restControleurs;

import demo.vroum_vroum.service.VehiculeServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehiculeService")
public class VehiculeServiceRestControleurs {

    @Autowired
    VehiculeServiceService vehiculeServiceService;
}
