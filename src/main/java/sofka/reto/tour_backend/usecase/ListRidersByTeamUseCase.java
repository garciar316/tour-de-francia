package sofka.reto.tour_backend.usecase;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import sofka.reto.tour_backend.model.RiderDTO;
import sofka.reto.tour_backend.repository.RiderRepository;
import sofka.reto.tour_backend.util.RiderMapper;

@Service
@Validated
public class ListRidersByTeamUseCase {

    private final RiderRepository riderRepository;
    private final RiderMapper riderMapper;

    public ListRidersByTeamUseCase(RiderRepository riderRepository, RiderMapper riderMapper) {
        this.riderRepository = riderRepository;
        this.riderMapper = riderMapper;
    }

    public Flux<RiderDTO> get(String teamCode) {
        return riderRepository.findAllByTeamCode(teamCode).map(riderMapper::mapToDTO);
    }
}
