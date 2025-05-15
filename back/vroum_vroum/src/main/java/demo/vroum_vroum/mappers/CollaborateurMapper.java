package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.CollaborateurLiteDto;
import demo.vroum_vroum.entity.Collaborateur;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Mapper de l'entité Collaborateur vers CollaborateurLiteDto
 */
@Component
public class CollaborateurMapper {
    /**
     * Méthode permettant de créer un CollaborateurLiteDto à partir d'un objet Collaborateur
     * @param collaborateur collaborateur (entité)
     * @return un CollaborateurLiteDto
     */
    public static CollaborateurLiteDto toLiteDto(Collaborateur collaborateur) {
        return new CollaborateurLiteDto(collaborateur.getNom(), collaborateur.getPrenom(), collaborateur.getTelephone());
    }

    /**
     * Méthode permettant de convertir une liste d'objet Collaborateur en liste d'objet CollaborateurLiteDto
     * @param passagers une liste de Collaborateur
     * @return une liste de CollaborateurLiteDto
     */
    public static List<CollaborateurLiteDto> toLiteDtos(List<Collaborateur> passagers) {
        List<CollaborateurLiteDto> collaborateurLiteDtos = new ArrayList<>();

        for (Collaborateur passager : passagers) {
            collaborateurLiteDtos.add(toLiteDto(passager));
        }

        return collaborateurLiteDtos;
    }
}
