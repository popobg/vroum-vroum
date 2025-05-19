package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.AdresseDto;
import demo.vroum_vroum.entities.Adresse;
import org.springframework.stereotype.Component;

@Component
public class AdresseMapper {
    /**
     * Méthode permettant de mapper un objet Adresse vers un objet AdresseDto
     * @param adresse adresse
     * @return une adresse dto
     */
    public static AdresseDto toDto(Adresse adresse) {
        return new AdresseDto(adresse.getId(), adresse.getNumero(), adresse.getRue(), adresse.getCodePostal(), adresse.getVille());
    }

    /**
     * Méthode permettant de mapper un objet AdresseDto vers un objet Adresse
     * @param dto adresse dto
     * @return une adresse
     */
    public static Adresse toEntity(AdresseDto dto) {
        return new Adresse(dto.getNumero(), dto.getRue(), dto.getCodePostal(), dto.getNomVille());
    }
}
