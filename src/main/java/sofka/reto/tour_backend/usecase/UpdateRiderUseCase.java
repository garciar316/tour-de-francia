package sofka.reto.tour_backend.usecase;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;
import sofka.reto.tour_backend.collection.Rider;
import sofka.reto.tour_backend.exception.FullRidersOnTeamException;
import sofka.reto.tour_backend.exception.RecordNotFoundException;
import sofka.reto.tour_backend.model.RiderDTO;
import sofka.reto.tour_backend.repository.RiderRepository;
import sofka.reto.tour_backend.util.RiderMapper;

import javax.validation.Valid;
import java.util.Objects;

@Service
@Validated
public class UpdateRiderUseCase {

    private final RiderRepository riderRepository;
    private final RiderMapper riderMapper;

    public UpdateRiderUseCase(RiderRepository riderRepository, RiderMapper riderMapper) {
        this.riderRepository = riderRepository;
        this.riderMapper = riderMapper;
    }

    public Mono<String> apply(@Valid RiderDTO riderDTO) {
        Objects.requireNonNull(riderDTO.getId(), "El id del ciclista es requerido");
        return riderRepository
                .findAllByTeamCode(riderDTO.getTeamCode())
                .count()
                .flatMap(n -> {
                    if (n >= 8) {
                        return Mono.error(new FullRidersOnTeamException("Se ha alcanzado el máximo de jugadores en el equipo"));
                    }
                    return riderRepository.existsById(riderDTO.getId()).flatMap(resp -> {
                        if (Boolean.FALSE.equals(resp)) {
                            return Mono.error(new RecordNotFoundException("El corredor que intenta actualizar no existe"));
                        }
                        return riderRepository
                                .save(riderMapper.mapToRider(riderDTO))
                                .map(Rider::getId);
                    });
                });
    }
}
