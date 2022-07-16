package sofka.reto.tour_backend.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sofka.reto.tour_backend.model.TeamDTO;
import sofka.reto.tour_backend.usecase.*;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TeamRouter {

    @Bean
    public RouterFunction<ServerResponse> listTeams(ListTeamsUseCase listRidersUseCase) {
        return route(GET("/team/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listRidersUseCase.get(), TeamDTO.class))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> findTeamByCode(FindTeamByCodeUseCase findTeamByCodeUseCase) {
        return route(GET("/team/findByCode/{code}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(findTeamByCodeUseCase.get(request.pathVariable("code")), TeamDTO.class))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> createTeam(CreateTeamUseCase createTeamUseCase) {
        Function<TeamDTO, Mono<ServerResponse>> executor = teamDto -> createTeamUseCase.apply(teamDto)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result))
                .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class));

        return route(
                POST("/team/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(TeamDTO.class).flatMap(executor)
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> deleteTeam(DeleteTeamUseCase deleteTeamUseCase) {
        return route(
                DELETE("/team/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteTeamUseCase.apply(request.pathVariable("id")), Void.class))
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> updateTeam(UpdateTeamUseCase updateTeamUseCase) {
        Function<TeamDTO, Mono<ServerResponse>> executor = teamDTO ->  updateTeamUseCase.apply(teamDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result))
                .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class));

        return route(
                PUT("/team/update").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(TeamDTO.class).flatMap(executor)
                        .onErrorResume(throwable -> ServerResponse.badRequest().body(throwable.getMessage(), String.class))
        );
    }
}
