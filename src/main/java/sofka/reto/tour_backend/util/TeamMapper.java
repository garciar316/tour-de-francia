package sofka.reto.tour_backend.util;

import org.springframework.stereotype.Component;
import sofka.reto.tour_backend.collection.Team;
import sofka.reto.tour_backend.model.TeamDTO;

@Component
public class TeamMapper {

    public TeamDTO mapToDTO(Team team) {
        return new TeamDTO(
                team.getId(),
                team.getCode(),
                team.getName(),
                team.getCountry());
    }

    public Team mapToTeam(TeamDTO teamDTO) {
        return new Team(
                teamDTO.getId(),
                teamDTO.getCode(),
                teamDTO.getName(),
                teamDTO.getCountry()
        );
    }
}
