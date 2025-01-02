package com.football.manager.convertors;

import com.football.manager.dto.PlayerDTO;
import com.football.manager.dto.TeamDTO;
import com.football.manager.models.Player;
import com.football.manager.models.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TeamConvertor {

    private final PlayerConvertor playerConvertor;

    public void fromDTO(TeamDTO source, Team destination) {
        destination.setId(source.getId());
        destination.setName(source.getName());
        destination.setAccount(source.getAccount());
        destination.setCommission(source.getCommission());
        List<Player> players = new ArrayList<>();
        for (PlayerDTO playerDTO : source.getPlayers()) {
            Player player = new Player();
            playerConvertor.fromDTO(playerDTO, player);
            players.add(player);
        }
        destination.setPlayers(players);
    }

    public void toDTO(Team source, TeamDTO destination) {
        destination.setId(source.getId());
        destination.setName(source.getName());
        destination.setAccount(source.getAccount());
        destination.setCommission(source.getCommission());
        List<PlayerDTO> players = new ArrayList<>();
        for (Player player : source.getPlayers()) {
            PlayerDTO playerDTO = new PlayerDTO();
            playerConvertor.toDTO(player, playerDTO);
            players.add(playerDTO);
        }
        destination.setPlayers(players);
    }
}
