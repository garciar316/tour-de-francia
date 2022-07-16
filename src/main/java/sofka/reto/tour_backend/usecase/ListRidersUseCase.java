package sofka.reto.tour_backend.usecase;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import sofka.reto.tour_backend.model.RiderDTO;
import sofka.reto.tour_backend.repository.RiderRepository;
import sofka.reto.tour_backend.util.RiderMapper;

@Service
@Validated
public class ListRidersUseCase {

    private final RiderRepository riderRepository;
    private final RiderMapper riderMapper;

    public ListRidersUseCase(RiderRepository riderRepository, RiderMapper riderMapper) {
        this.riderRepository = riderRepository;
        this.riderMapper = riderMapper;
    }

    public Flux<RiderDTO> get() {
        return riderRepository.findAll().map(riderMapper::mapToDTO);
    }
}
