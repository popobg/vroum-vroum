package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.CovoiturageDto;
import demo.vroum_vroum.entities.Covoiturage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper objet Covoiturage - objet CovoiturageDto
 */
@Component
public class CovoiturageMapper {
    /**
     * Méthode permettant de mapper un objet Covoiturage vers un CovoiturageDto
     * @param covoiturage objet entité Covoiturage
     * @return un Covoiturage dto
     */
    public static CovoiturageDto toDto(Covoiturage covoiturage) {
        return new CovoiturageDto(covoiturage.getId(), covoiturage.getDate(), AdresseMapper.toDto(covoiturage.getAdresseDepart()), AdresseMapper.toDto(covoiturage.getAdresseArrivee()), covoiturage.getDistance(), covoiturage.getDuree(), CollaborateurMapper.toLiteDto(covoiturage.getOrganisateur()), VehiculeMapper.toDto(covoiturage.getVehicule()), CollaborateurMapper.toLiteDtos(covoiturage.getCollaborateurs()));
    }

    /**
     * Méthode permettant de mapper une liste d'objet Covoiturage vers une liste de CovoiturageDto
     * @param covoiturages liste de Covoiturage
     * @return une liste de CovoiturageDto
     */
    public static List<CovoiturageDto> toDtos(List<Covoiturage> covoiturages) {
        List<CovoiturageDto> dtos = new ArrayList<>();

        for (Covoiturage covoiturage : covoiturages) {
            dtos.add(toDto(covoiturage));
        }

        return dtos;
    }

    /**
     * Méthode permettant de mapper un CovoiturageDto vers un objet entité Covoiturage
     * @param dto CovoiturageDto
     * @return un objet Covoiturage
     */
    public static Covoiturage toEntity(CovoiturageDto dto) {
        return new Covoiturage();
    }
}
