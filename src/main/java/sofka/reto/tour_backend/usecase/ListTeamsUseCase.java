package sofka.reto.tour_backend.usecase;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import sofka.reto.tour_backend.model.TeamDTO;
import sofka.reto.tour_backend.repository.TeamRepository;
import sofka.reto.tour_backend.util.TeamMapper;

@Service
@Validated
public class ListTeamsUseCase {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public ListTeamsUseCase(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public Flux<TeamDTO> get() {
        return teamRepository.findAll()
                .map(teamMapper::mapToDTO);
    }
}
