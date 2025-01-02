package com.football.manager.controllers;

import com.football.manager.convertors.TeamConvertor;
import com.football.manager.dto.TeamDTO;
import com.football.manager.models.Team;
import com.football.manager.sevices.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    private final TeamConvertor teamConvertor;

    @PostMapping("/")
    public TeamDTO create(@RequestBody TeamDTO teamDTO) {
        Team team = teamService.create(teamDTO);
        teamConvertor.toDTO(team, teamDTO);
        return teamDTO;
    }

    @PatchMapping("/{id}")
    public TeamDTO update(@PathVariable(value = "id") Long id, @RequestBody TeamDTO teamDTO) {
        Team team = new Team();
        teamConvertor.fromDTO(teamDTO, team);
        team = teamService.update(id, team);
        teamConvertor.toDTO(team, teamDTO);
        return teamDTO;
    }

    @GetMapping("/{id}")
    public TeamDTO getById(@PathVariable(value = "id") Long id) {
        TeamDTO teamDTO = new TeamDTO();
        Team team = teamService.getById(id);
        teamConvertor.toDTO(team, teamDTO);
        return teamDTO;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        return teamService.delete(id);
    }

    @PatchMapping("/{firstTeamId}/transfer/{secondTeamId}/player/{playerId}")
    public String transferPlayer(@PathVariable(value = "firstTeamId") Long firstTeamId, @PathVariable(value = "secondTeamId") Long secondTeamId, @PathVariable(value = "playerId") Long playerId) {
        return teamService.transferPlayer(firstTeamId, secondTeamId, playerId);
    }

    @PatchMapping("/{teamId}/player/{playerId}")
    public String addPlayerToTeam(@PathVariable(value = "teamId") Long firstTeamId, @PathVariable(value = "playerId") Long playerId) {
        return teamService.addPlayerToTeam(firstTeamId, playerId);
    }
}