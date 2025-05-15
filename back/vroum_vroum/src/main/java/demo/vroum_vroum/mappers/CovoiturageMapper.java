package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.CovoiturageDto;
import demo.vroum_vroum.entity.Covoiturage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CovoiturageMapper {

    public static CovoiturageDto toDto(Covoiturage covoiturage) {
        CovoiturageDto dto = new CovoiturageDto();
        return dto;
    }

    public static List<CovoiturageDto> toDtos(List<Covoiturage> covoiturages) {
        List<CovoiturageDto> dtos = new ArrayList<>();

        for (Covoiturage covoiturage : covoiturages) {
            dtos.add(toDto(covoiturage));
        }

        return dtos;
    }

    public static Covoiturage toEntity(CovoiturageDto dto) {
        Covoiturage covoiturage = new Covoiturage();
        return covoiturage;
    }
}
