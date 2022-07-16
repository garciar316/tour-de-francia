package sofka.reto.tour_backend.usecase;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;
import sofka.reto.tour_backend.model.TeamDTO;
import sofka.reto.tour_backend.repository.TeamRepository;
import sofka.reto.tour_backend.util.TeamMapper;

@Service
@Validated
public class FindTeamByCodeUseCase {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public FindTeamByCodeUseCase(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public Mono<TeamDTO> get(String code) {
        return teamRepository.findByCode(code).map(teamMapper::mapToDTO);
    }
}
