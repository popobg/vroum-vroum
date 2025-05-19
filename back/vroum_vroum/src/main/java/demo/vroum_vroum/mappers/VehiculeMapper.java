package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.VehiculeLiteDto;
import demo.vroum_vroum.entities.Vehicule;
import org.springframework.stereotype.Component;

/**
 * Mapper objet Vehicule - VehiculeDto
 */
@Component
public class VehiculeMapper {

    public static VehiculeLiteDto toDto(Vehicule vehicule) {
        return new VehiculeLiteDto(vehicule.getId(), vehicule.getMarque(), vehicule.getModele());
    }
}
