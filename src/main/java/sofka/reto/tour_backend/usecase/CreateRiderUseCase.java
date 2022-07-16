package sofka.reto.tour_backend.usecase;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;
import sofka.reto.tour_backend.collection.Rider;
import sofka.reto.tour_backend.exception.FullRidersOnTeamException;
import sofka.reto.tour_backend.model.RiderDTO;
import sofka.reto.tour_backend.repository.RiderRepository;
import sofka.reto.tour_backend.repository.TeamRepository;
import sofka.reto.tour_backend.util.RiderMapper;

import javax.validation.Valid;

@Service
@Validated
public class CreateRiderUseCase {

    private final RiderRepository riderRepository;
    private final TeamRepository teamRepository;
    private final RiderMapper riderMapper;

    public CreateRiderUseCase(RiderRepository riderRepository, TeamRepository teamRepository, RiderMapper riderMapper) {
        this.riderRepository = riderRepository;
        this.teamRepository = teamRepository;
        this.riderMapper = riderMapper;
    }

    public Mono<String> apply(@Valid RiderDTO riderDTO) {
        return riderRepository
                .findAllByTeamCode(riderDTO.getTeamCode())
                .count()
                .flatMap(n -> {
                    if (n >= 8) {
                        return Mono.error(new FullRidersOnTeamException("Se ha alcanzado el máximo de jugadores en el equipo"));
                    }
                    return riderRepository
                            .save(riderMapper.mapToRider(riderDTO))
                            .map(Rider::getId);
                });
    }
}
