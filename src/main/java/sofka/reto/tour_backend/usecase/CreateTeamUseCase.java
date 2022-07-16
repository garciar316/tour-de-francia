package sofka.reto.tour_backend.usecase;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;
import sofka.reto.tour_backend.collection.Team;
import sofka.reto.tour_backend.model.TeamDTO;
import sofka.reto.tour_backend.repository.TeamRepository;
import sofka.reto.tour_backend.util.TeamMapper;

import javax.validation.Valid;

@Service
@Validated
public class CreateTeamUseCase {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public CreateTeamUseCase(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public Mono<String> apply(@Valid TeamDTO teamDTO) {
        return teamRepository
                .save(teamMapper.mapToTeam(teamDTO))
                .map(Team::getId);
    }
}
