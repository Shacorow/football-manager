package com.football.manager.controllers;

import com.football.manager.convertors.PlayerConvertor;
import com.football.manager.dto.PlayerDTO;
import com.football.manager.models.Player;
import com.football.manager.sevices.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    private final PlayerConvertor playerConvertor;

    @PostMapping("/")
    public PlayerDTO create(@RequestBody PlayerDTO playerDTO) {
        Player player = playerService.create(playerDTO);
        playerConvertor.toDTO(player, playerDTO);
        return playerDTO;
    }

    @PatchMapping("/{id}")
    public PlayerDTO update(@PathVariable(value = "id") Long id, @RequestBody PlayerDTO playerDTO) {
        Player player = new Player();
        playerConvertor.fromDTO(playerDTO, player);
        player = playerService.update(id, player);
        playerConvertor.toDTO(player, playerDTO);
        return playerDTO;
    }

    @GetMapping("/{id}")
    public PlayerDTO getById(@PathVariable(value = "id") Long id) {
        PlayerDTO playerDTO = new PlayerDTO();
        Player player = playerService.getById(id);
        playerConvertor.toDTO(player, playerDTO);
        return playerDTO;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        return playerService.delete(id);
    }

    @PatchMapping("/{id}/experience/{experience}")
    public PlayerDTO addExperience(@PathVariable(value = "id") Long id, @PathVariable(value = "experience") Integer experience) {
        PlayerDTO playerDTO = new PlayerDTO();
        Player player = playerService.addExperience(id, experience);
        playerConvertor.toDTO(player, playerDTO);
        return playerDTO;
    }
}
