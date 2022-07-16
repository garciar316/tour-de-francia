package sofka.reto.tour_backend.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sofka.reto.tour_backend.model.RiderDTO;
import sofka.reto.tour_backend.usecase.*;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RiderRouter {

    @Bean
    public RouterFunction<ServerResponse> listRiders(ListRidersUseCase listRidersUseCase) {
        return route(GET("/rider/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listRidersUseCase.get(), RiderDTO.class))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> listRidersByTeamCode(ListRidersByTeamUseCase listRidersByTeamUseCase) {
        return route(GET("/rider/listAllByTeam/{teamCode}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listRidersByTeamUseCase.get(request.pathVariable("teamCode")), RiderDTO.class))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );

    }

    @Bean
    public RouterFunction<ServerResponse> findRiderByCode(FindRiderByCodeUseCase findRiderByCodeUseCase) {
        return route(GET("/rider/findByCode/{code}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(findRiderByCodeUseCase.get(request.pathVariable("code")), RiderDTO.class))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> createRider(CreateRiderUseCase createRiderUseCase) {
        Function<RiderDTO, Mono<ServerResponse>> executor = riderDTO -> createRiderUseCase.apply(riderDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result))
                .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class));

        return route(
                POST("/rider/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(RiderDTO.class).flatMap(executor)
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> deleteRider(DeleteRiderUseCase deleteRiderUseCase) {
        return route(
                DELETE("/rider/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteRiderUseCase.apply(request.pathVariable("id")), Void.class))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> updateRider(UpdateRiderUseCase updateRiderUseCase) {
        Function<RiderDTO, Mono<ServerResponse>> executor = riderDTO ->  updateRiderUseCase.apply(riderDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result))
                .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class));

        return route(
                PUT("/rider/update").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(RiderDTO.class).flatMap(executor)
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }
}
