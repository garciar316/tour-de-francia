package sofka.reto.tour_backend.usecase;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;
import sofka.reto.tour_backend.exception.RecordNotFoundException;
import sofka.reto.tour_backend.repository.RiderRepository;

import java.util.Objects;

@Service
@Validated
public class DeleteRiderUseCase {

    private final RiderRepository riderRepository;

    public DeleteRiderUseCase(RiderRepository riderRepository) {
        this.riderRepository = riderRepository;
    }

    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "El id no debe ser nulo");
        return riderRepository.existsById(id).flatMap(resp -> {
            if (Boolean.FALSE.equals(resp)) {
                return Mono.error(new RecordNotFoundException("El equipo que intenta eliminar no existe"));
            }
            return riderRepository.deleteById(id);
        });
    }
}
