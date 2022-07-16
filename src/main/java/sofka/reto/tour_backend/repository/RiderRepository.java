package sofka.reto.tour_backend.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sofka.reto.tour_backend.collection.Rider;
import sofka.reto.tour_backend.collection.Team;

@Repository
public interface RiderRepository extends ReactiveMongoRepository<Rider, String> {
    Flux<Rider> findAllByTeamCode(String teamCode);
    Mono<Rider> findByCode(String code);
}
