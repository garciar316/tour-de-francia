package sofka.reto.tour_backend.util;

import org.springframework.stereotype.Component;
import sofka.reto.tour_backend.collection.Rider;
import sofka.reto.tour_backend.model.RiderDTO;

@Component
public class RiderMapper {

    public RiderDTO mapToDTO(Rider rider) {
        return new RiderDTO(
                rider.getId(),
                rider.getCode(),
                rider.getFullName(),
                rider.getNationality(),
                rider.getTeamCode());
    }

    public Rider mapToRider(RiderDTO riderDTO) {
        return new Rider(
                riderDTO.getId(),
                riderDTO.getCode(),
                riderDTO.getFullName(),
                riderDTO.getNationality(),
                riderDTO.getTeamCode());
    }
}
