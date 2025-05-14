package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.CovoiturageDTO;
import demo.vroum_vroum.entity.Covoiturage;
import org.springframework.stereotype.Component;

@Component
public class CovoiturageMapper {

    public static CovoiturageDTO toDto(Covoiturage covoiturage) {
        CovoiturageDTO dto = new CovoiturageDTO();
        return dto;
    }
}
