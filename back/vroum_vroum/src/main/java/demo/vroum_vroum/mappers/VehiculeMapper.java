package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.VehiculeLiteDto;
import demo.vroum_vroum.entities.Vehicule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Mapper objet Vehicule - VehiculeDto
 */
@Component
public class VehiculeMapper {

    /**
     * Méthode permettant de mapper un véhicule en véhicule lite dto
     * @param vehicule entité Vehicule
     * @return un VehiculeLiteDto
     */
    public static VehiculeLiteDto toDto(Vehicule vehicule) {
        return new VehiculeLiteDto(vehicule.getId(), vehicule.getMarque(), vehicule.getModele());
    }

    /**
     * Méthode permettant de mapper une liste d'objet Véhicule en liste d'objet VehiculeLiteDto
     * @param vehicules une liste de véhicules
     * @return une liste de VehiculeLiteDto
     */
    public static List<VehiculeLiteDto> toDtos(List<Vehicule> vehicules) {
        List<VehiculeLiteDto> vehiculesDto = new ArrayList<>();

        for (Vehicule vehicule : vehicules) {
            vehiculesDto.add(toDto(vehicule));
        }

        return vehiculesDto;
    }

    public static Vehicule toEntity(VehiculeLiteDto vehiculeDto) {
        Vehicule vehicule = new Vehicule();
        vehicule.setId(vehiculeDto.getId());
        vehicule.setMarque(vehiculeDto.getMarque());
        vehicule.setModele(vehiculeDto.getModele());
        return vehicule;
    }
}
