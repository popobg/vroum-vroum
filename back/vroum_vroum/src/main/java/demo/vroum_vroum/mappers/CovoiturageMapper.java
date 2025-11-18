package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.CovoiturageDto;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Covoiturage;
import demo.vroum_vroum.entities.Vehicule;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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
        return new CovoiturageDto(covoiturage.getId(), covoiturage.getDate(), AdresseMapper.toDto(covoiturage.getAdresseDepart()), AdresseMapper.toDto(covoiturage.getAdresseArrivee()), covoiturage.getDistance(), covoiturage.getDuree(), CollaborateurMapper.toLiteDto(covoiturage.getOrganisateur()), VehiculeMapper.toDto(covoiturage.getVehicule()), CollaborateurMapper.toLiteDtos(covoiturage.getCollaborateurs()), covoiturage.getNbPlaces());
    }

    /**
     * Méthode permettant de mapper une liste d'objet Covoiturage vers une liste de CovoiturageDto
     * @param covoiturages liste de Covoiturage
     * @return une liste de CovoiturageDto
     */
    public static Set<CovoiturageDto> toDtos(Set<Covoiturage> covoiturages) {
        Set<CovoiturageDto> dtos = new HashSet<>();

        if (covoiturages == null) return dtos;

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
        if (dto == null) return null;

        Covoiturage covoit = new Covoiturage();
        covoit.setDate(dto.getDate());
        covoit.setDistance(dto.getDistance());
        covoit.setDuree(dto.getDuree());
        covoit.setNbPlaces(dto.getNbPlaces());

        if (dto.getAdresseDepart() != null) {
            covoit.setAdresseDepart(AdresseMapper.toEntity(dto.getAdresseDepart()));
        }
        if (dto.getAdresseArrivee() != null) {
            covoit.setAdresseArrivee(AdresseMapper.toEntity(dto.getAdresseArrivee()));
        }

        if (dto.getOrganisateur() != null) {
            Collaborateur orga = new Collaborateur();
            orga.setId(dto.getOrganisateur().getId());
            covoit.setOrganisateur(orga);
        }

        if (dto.getVehicule() != null) {
            Vehicule veh = new Vehicule();
            veh.setId(dto.getVehicule().getId());
            covoit.setVehicule(veh);
        }

        return covoit;
    }

}
