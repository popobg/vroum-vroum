package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.VehiculeDto;
import demo.vroum_vroum.entities.Vehicule;
import org.springframework.stereotype.Component;

/**
 * Mapper objet Vehicule - VehiculeDto
 */
@Component
public class VehiculeMapper {
    /**
     * Méthode permettant de mapper un objet Vehicule vers un objet VehiculeDto épuré
     * @param vehicule objet Vehicule (entité)
     * @return un véhicule dto
     */
    public static VehiculeDto toDto(Vehicule vehicule) {
        return new VehiculeDto(vehicule.getId(), vehicule.getNbPlaces(), vehicule.getModele(), vehicule.getMarque());
    }

    /**
     * Méthode permettant de mapper un objet VehiculeDto vers un objet Vehicule
     * @param dto VehiculeDto
     * @return un objet Vehicule (entité)
     */
    public static Vehicule toEntity(VehiculeDto dto) {
        return new Vehicule(dto.getNbPlaces(), dto.getModele(), dto.getMarque(), dto.getImmatriculation(), dto.getConducteur());
    }
}
