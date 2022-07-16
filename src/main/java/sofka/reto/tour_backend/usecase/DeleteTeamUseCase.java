package sofka.reto.tour_backend.usecase;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;
import sofka.reto.tour_backend.exception.RecordNotFoundException;
import sofka.reto.tour_backend.repository.TeamRepository;

import java.util.Objects;

@Service
@Validated
public class DeleteTeamUseCase {

    private final TeamRepository teamRepository;

    public DeleteTeamUseCase(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "El id no debe ser nulo");
        return teamRepository.existsById(id)
                .flatMap(resp -> {
                    if (Boolean.FALSE.equals(resp)) {
                        return Mono.error(new RecordNotFoundException("El equipo que intenta eliminar no existe"));
                    }
                    return teamRepository.deleteById(id);
                });
    }
}
