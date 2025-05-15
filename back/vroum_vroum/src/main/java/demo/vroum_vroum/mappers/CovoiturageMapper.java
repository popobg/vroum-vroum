package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.CovoiturageDto;
import demo.vroum_vroum.entity.Covoiturage;
import org.springframework.stereotype.Component;

@Component
public class CovoiturageMapper {

    public static CovoiturageDto toDto(Covoiturage covoiturage) {
        CovoiturageDto dto = new CovoiturageDto();
        return dto;
    }

    public static Covoiturage toEntity(CovoiturageDto dto) {
        Covoiturage covoiturage = new Covoiturage();
        return covoiturage;
    }
}
