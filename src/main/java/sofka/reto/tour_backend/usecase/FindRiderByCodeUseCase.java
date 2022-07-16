package sofka.reto.tour_backend.usecase;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;
import sofka.reto.tour_backend.model.RiderDTO;
import sofka.reto.tour_backend.repository.RiderRepository;
import sofka.reto.tour_backend.util.RiderMapper;

@Service
@Validated
public class FindRiderByCodeUseCase {

    private final RiderRepository riderRepository;
    private final RiderMapper riderMapper;

    public FindRiderByCodeUseCase(RiderRepository riderRepository, RiderMapper riderMapper) {
        this.riderRepository = riderRepository;
        this.riderMapper = riderMapper;
    }

    public Mono<RiderDTO> get(String code) {
        return riderRepository.findByCode(code).map(riderMapper::mapToDTO);
    }
}
