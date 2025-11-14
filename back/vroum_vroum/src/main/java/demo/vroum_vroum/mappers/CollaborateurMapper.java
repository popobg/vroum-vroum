package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.CollaborateurDto;
import demo.vroum_vroum.dto.CollaborateurLiteDto;
import demo.vroum_vroum.entities.Collaborateur;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Mapper entité Collaborateur - Dtos de collaborateur
 */
@Component
public class CollaborateurMapper {
    /**
     * Méthode permettant de créer un CollaborateurLiteDto à partir d'un objet Collaborateur
     * @param collaborateur collaborateur (entité)
     * @return un CollaborateurLiteDto
     */
    public static CollaborateurLiteDto toLiteDto(Collaborateur collaborateur) {
        return new CollaborateurLiteDto(collaborateur.getId(), collaborateur.getNom(), collaborateur.getPrenom(), collaborateur.getTelephone());
    }

    /**
     * Méthode permettant de convertir une liste d'objet Collaborateur en liste d'objet CollaborateurLiteDto
     * @param passagers une liste de Collaborateur
     * @return une liste de CollaborateurLiteDto
     */
    public static Set<CollaborateurLiteDto> toLiteDtos(Set<Collaborateur> collaborateurs) {
        Set<CollaborateurLiteDto> collaborateurLiteDtos = new HashSet<>();

        for (Collaborateur collaborateur : collaborateurs) {
            collaborateurLiteDtos.add(toLiteDto(collaborateur));
        }

        return collaborateurLiteDtos;
    }

    /**
     * Méthode permettant de créer un CollaborateurDto à partir d'un objet Collaborateur
     * @param collaborateur collaborateur (entité)
     * @return un CollaborateurDto
     */
    public static CollaborateurDto toDto(Collaborateur collaborateur) {
        return new CollaborateurDto(collaborateur.getId(), collaborateur.getNom(), collaborateur.getPrenom(), collaborateur.getAdresse(), collaborateur.getEmail(), collaborateur.getTelephone(), collaborateur.getPseudo(), null, collaborateur.getAdmin(), VehiculeMapper.toDtos(collaborateur.getVehicules()));
    }

    /**
     * Méthode permettant de convertir une liste d'objet Collaborateur en liste d'objet CollaborateurDto
     * @param passagers une liste de Collaborateur
     * @return une liste de CollaborateurDto
     */
    public static Set<CollaborateurDto> toDtos(Set<Collaborateur> collaborateurs) {
        Set<CollaborateurDto> collaborateurDtos = new HashSet<>();

        for (Collaborateur collaborateur : collaborateurs) {
            collaborateurDtos.add(toDto(collaborateur));
        }

        return collaborateurDtos;
    }

    /**
     * Méthode permettant de convertir un CollaborateurDto en Collaborateur
     * @param collaborateurDto un CollaborateurDto
     * @return un collaborateur (entité)
     */
    public static Collaborateur toEntity(CollaborateurDto collaborateurDto) {
        return new Collaborateur(collaborateurDto.getId(), collaborateurDto.getNom(), collaborateurDto.getPrenom(), collaborateurDto.getAdresse(), collaborateurDto.getEmail(), collaborateurDto.getTelephone(), collaborateurDto.getPseudo(), collaborateurDto.getPassword(), collaborateurDto.getAdmin());
    }

    /**
     * Méthode permettant de convertir un CollaborateurLiteDto en Collaborateur
     * @param collaborateurDto un CollaborateurLiteDto
     * @return un collaborateur (entité)
     */
    public static Collaborateur liteToEntity(CollaborateurLiteDto collaborateurDto) {
        Collaborateur collaborateur = new Collaborateur();
        collaborateur.setId(collaborateurDto.getId());
        collaborateur.setNom(collaborateurDto.getNom());
        collaborateur.setPrenom(collaborateurDto.getPrenom());
        collaborateur.setTelephone(collaborateurDto.getTelephone());
        return collaborateur;
    }
}
