package sofka.reto.tour_backend.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import sofka.reto.tour_backend.collection.Team;

@Repository
public interface TeamRepository extends ReactiveMongoRepository<Team, String> {
    Mono<Team> findByCode(String code);
}
