package sofka.reto.tour_backend.usecase;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;
import sofka.reto.tour_backend.collection.Team;
import sofka.reto.tour_backend.exception.RecordNotFoundException;
import sofka.reto.tour_backend.model.TeamDTO;
import sofka.reto.tour_backend.repository.TeamRepository;
import sofka.reto.tour_backend.util.TeamMapper;

@Service
@Validated
public class UpdateTeamUseCase {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public UpdateTeamUseCase(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public Mono<String> apply(TeamDTO teamDTO) {
        return teamRepository.existsById(teamDTO.getId()).flatMap(resp -> {
            if (Boolean.FALSE.equals(resp)) {
                return Mono.error(new RecordNotFoundException("El equipo que intenta actualizar no existe"));
            }
            return teamRepository.save(teamMapper.mapToTeam(teamDTO))
                    .map(Team::getId);
        });
    }
}
