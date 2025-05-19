package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.VehiculeServiceDto;
import demo.vroum_vroum.entities.VehiculeService;
import org.springframework.stereotype.Component;

@Component
public class VehiculeServiceMapper {

    public VehiculeServiceDto toDto(VehiculeService vehiculeService) {
        VehiculeServiceDto vehiculeServiceDto = new VehiculeServiceDto();
        vehiculeServiceDto.setImmatriculation(vehiculeService.getImmatriculation());
        vehiculeServiceDto.setMarque(vehiculeService.getMarque());
        vehiculeServiceDto.setModele(vehiculeService.getModele());
        vehiculeServiceDto.setCategorie(vehiculeService.getCategorie());
        vehiculeServiceDto.setPhoto(vehiculeService.getPhoto());
        vehiculeServiceDto.setMotorisation(vehiculeService.getMotorisation());
        vehiculeServiceDto.setCO2parKm(vehiculeService.getCO2parKm());
        vehiculeServiceDto.setNbPlaces(vehiculeService.getNbPlaces());
        vehiculeServiceDto.setStatut(vehiculeService.getStatut());
        vehiculeServiceDto.setReservations(vehiculeService.getReservations());
        return vehiculeServiceDto;
    }

    public VehiculeService toEntity(VehiculeServiceDto vehiculeServiceDto) {
        VehiculeService vehiculeService = new VehiculeService();
        vehiculeService.setImmatriculation(vehiculeServiceDto.getImmatriculation());
        vehiculeService.setMarque(vehiculeServiceDto.getMarque());
        vehiculeService.setModele(vehiculeServiceDto.getModele());
        vehiculeService.setCategorie(vehiculeServiceDto.getCategorie());
        vehiculeService.setPhoto(vehiculeServiceDto.getPhoto());
        vehiculeService.setMotorisation(vehiculeServiceDto.getMotorisation());
        vehiculeService.setCO2parKm(vehiculeServiceDto.getCO2parKm());
        vehiculeService.setNbPlaces(vehiculeServiceDto.getNbPlaces());
        vehiculeService.setStatut(vehiculeServiceDto.getStatut());
        vehiculeService.setReservations(vehiculeServiceDto.getReservations());
        return vehiculeService;
    }
}
