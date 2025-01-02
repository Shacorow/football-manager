package com.football.manager.sevices;

import com.football.manager.dto.PlayerDTO;
import com.football.manager.models.Player;
import com.football.manager.models.Team;
import com.football.manager.exceptions.PlayerDoesNotExistException;
import com.football.manager.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Player create(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setExperience(0);
        player.setName(playerDTO.getName());
        player.setAge(playerDTO.getAge());
        player.setSurname(playerDTO.getSurname());
        player = playerRepository.save(player);
        return player;
    }

    public Player getById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new PlayerDoesNotExistException(id));
    }

    public String delete(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerDoesNotExistException(id));
        playerRepository.delete(player);
        return "Player successfully deleted!";
    }

    public Player update(Long id, Player updatedPlayer) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerDoesNotExistException(id));

        if (updatedPlayer.getName() != null) {
            player.setName(updatedPlayer.getName());
        }
        if (updatedPlayer.getSurname() != null) {
            player.setSurname(updatedPlayer.getSurname());
        }
        if (updatedPlayer.getAge() != null) {
            player.setAge(updatedPlayer.getAge());
        }
        return playerRepository.save(player);
    }

    public Player addExperience(Long id, Integer experience) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerDoesNotExistException(id));
        player.setExperience(player.getExperience() + experience);
        return playerRepository.save(player);
    }

    public Player clearTeam(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerDoesNotExistException(id));
        player.setTeam(null);
        return playerRepository.save(player);
    }

    public Player changeTeam(Long id, Team team) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerDoesNotExistException(id));
        player.setTeam(team);
        return playerRepository.save(player);
    }
}
