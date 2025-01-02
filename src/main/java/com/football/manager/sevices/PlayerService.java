package com.football.manager.sevices;

import com.football.manager.dto.PlayerDTO;
import com.football.manager.exceptions.PlayerDoesNotExistException;
import com.football.manager.models.Player;
import com.football.manager.models.Team;
import com.football.manager.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Player create(PlayerDTO playerDTO) {
        Player player = new Player();
        if (playerDTO.getStartDate() != null) {
            player.setStartDate(playerDTO.getStartDate());
        } else {
            player.setStartDate(new Date());
        }
        player.setName(playerDTO.getName());
        player.setBirthDate(playerDTO.getBirthDate());
        player.setSurname(playerDTO.getSurname());
        player = playerRepository.save(player);
        return player;
    }

    public Player getById(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerDoesNotExistException(id));
        if (player.getEndDate() != null) throw new PlayerDoesNotExistException(id);
        return player;
    }

    public void delete(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerDoesNotExistException(id));
        if (player.getEndDate() != null) throw new PlayerDoesNotExistException(id);
        player.setEndDate(new Date());
        playerRepository.save(player);
    }

    public Player update(Long id, Player updatedPlayer) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerDoesNotExistException(id));
        if (player.getEndDate() != null) throw new PlayerDoesNotExistException(id);

        if (updatedPlayer.getName() != null) {
            player.setName(updatedPlayer.getName());
        }
        if (updatedPlayer.getSurname() != null) {
            player.setSurname(updatedPlayer.getSurname());
        }
        return playerRepository.save(player);
    }

    public void clearTeam(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerDoesNotExistException(id));
        player.setTeam(null);
        playerRepository.save(player);
    }

    public Player changeTeam(Long id, Team team) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerDoesNotExistException(id));
        player.setTeam(team);
        return playerRepository.save(player);
    }
}
