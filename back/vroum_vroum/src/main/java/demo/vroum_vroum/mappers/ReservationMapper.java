package demo.vroum_vroum.mappers;

import demo.vroum_vroum.dto.ReservationDto;
import demo.vroum_vroum.entities.Reservation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *  Mapper objet reservation - objet reservationDto
 */
@Component
public class ReservationMapper {
    /**
     * Méthode permettant de mapper un objet reservation vers une ReservationDto
     * @param reservation objet entité reservation
     * @return une reservation Dto
     */
    public static ReservationDto toDto(Reservation reservation) {
        return new ReservationDto(reservation.getId(), reservation.getDateDepart(), reservation.getCollaborateur(), reservation.getDateRetour(), reservation.getVehiculeService());
    }

    /**
     * Méthode permettant de mapper une liste d'objet Reservation vers une liste de ReservationDto
     * @param reservations liste des Reservation
     * @return une liste de reservationDto
     */
    public static List<ReservationDto> toDtos(List<Reservation> reservations) {
        List<ReservationDto> dtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            dtos.add(toDto(reservation));
        }
        return dtos;
    }

    /**
     * Méthode permettant de mapper une ReservationDto vers un objet entité Reservation
     * @param dto ReservationDto
     * @return un objet reservation
     */
    public static Reservation toEntity(ReservationDto dto) {return new Reservation();}
}
