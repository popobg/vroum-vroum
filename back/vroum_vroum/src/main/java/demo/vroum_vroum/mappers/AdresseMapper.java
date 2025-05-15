package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.AdresseDto;
import demo.vroum_vroum.entity.Adresse;
import org.springframework.stereotype.Component;

@Component
public class AdresseMapper {
    public static AdresseDto toDto(Adresse adresse) {
        AdresseDto adresseDto = new AdresseDto();
        return adresseDto;
    }

    public static Adresse toEntity(AdresseDto adresseDto) {
        Adresse adresse = new Adresse();
        return adresse;
    }
}
