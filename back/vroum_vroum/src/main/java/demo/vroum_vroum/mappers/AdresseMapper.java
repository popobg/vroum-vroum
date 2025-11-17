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
        if (adresse == null) return null;
        return new AdresseDto(adresse.getId(), adresse.getNumero(), adresse.getRue(), adresse.getCodePostal(), adresse.getVille());
    }

    public static Adresse toEntity(AdresseDto dto) {
        if (dto == null) return null;

        Adresse adresse = new Adresse();
        adresse.setNumero(dto.getNumero());
        adresse.setRue(dto.getRue());
        adresse.setCodePostal(dto.getCodePostal());
        adresse.setVille(dto.getNomVille());
        return adresse;
    }
}
