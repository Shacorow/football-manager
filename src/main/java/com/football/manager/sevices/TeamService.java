package com.football.manager.sevices;

import com.football.manager.dto.TeamDTO;
import com.football.manager.models.Player;
import com.football.manager.models.Team;
import com.football.manager.exceptions.TeamAlreadyExistException;
import com.football.manager.exceptions.TeamDoesNotHaveEnoughMoneyToTransferException;
import com.football.manager.exceptions.TeamNotExistException;
import com.football.manager.exceptions.WrongCommissionException;
import com.football.manager.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final PlayerService playerService;


    public Team create(TeamDTO teamDTO) {
        Team team = teamRepository.getByName(teamDTO.getName());
        if (team == null) {
            team = new Team();
        } else {
            throw new TeamAlreadyExistException(teamDTO.getName());
        }
        team.setName(teamDTO.getName());
        if (teamDTO.getCommission() < 0 || teamDTO.getCommission() > 10) {
            throw new WrongCommissionException();
        }
        team.setCommission(teamDTO.getCommission());
        team.setAccount(teamDTO.getAccount());
        team = teamRepository.save(team);
        return team;
    }

    public Team getById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new TeamNotExistException(id));
    }

    public String delete(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotExistException(id));
        for(Player player : team.getPlayers()){
            playerService.clearTeam(player.getId());
        }
        team.setPlayers(null);
        teamRepository.save(team);
        teamRepository.delete(team);
        return "Team successfully deleted!";
    }

    public Team update(Long id, Team updatedTeam) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotExistException(id));

        if (updatedTeam.getName() != null) {
            if (teamRepository.getByName(updatedTeam.getName()) == null) {
                team.setName(updatedTeam.getName());
            } else {
                throw new TeamAlreadyExistException(updatedTeam.getName());
            }
        }
        if (updatedTeam.getAccount() != null) {
            team.setAccount(updatedTeam.getAccount());
        }
        if (updatedTeam.getCommission() != null) {
            team.setCommission(updatedTeam.getCommission());
        }
        return teamRepository.save(team);
    }

    public String addPlayerToTeam(Long teamId, Long playerId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotExistException(teamId));
        Player player = playerService.getById(playerId);
        if (player.getTeam() != null) {
            return transferPlayer(player.getTeam().getId(), teamId, playerId);
        } else {
            player = playerService.changeTeam(playerId, team);
            team.getPlayers().add(player);
            teamRepository.save(team);
        }
        return "Player add to team";
    }


    public String transferPlayer(Long firstTeamId, Long secondTeamId, Long playerId) {
        Team fromTransfer = teamRepository.findById(firstTeamId).orElseThrow(() -> new TeamNotExistException(firstTeamId));
        Team toTransfer = teamRepository.findById(secondTeamId).orElseThrow(() -> new TeamNotExistException(secondTeamId));
        Player player = playerService.getById(playerId);
        double varietyOfTransfer = (double) (player.getExperience() * 100000) / player.getAge();

        double fullPrice = varietyOfTransfer + (double) (varietyOfTransfer * (fromTransfer.getCommission() / 100.0));

        BigDecimal fullPriceRounded = new BigDecimal(fullPrice).setScale(3, RoundingMode.HALF_UP);

        fullPrice = fullPriceRounded.doubleValue();

        if (toTransfer.getAccount() < fullPrice) {
            throw new TeamDoesNotHaveEnoughMoneyToTransferException(toTransfer.getName());
        }
        toTransfer.setAccount(toTransfer.getAccount() - fullPrice);
        fromTransfer.setAccount(fromTransfer.getAccount() + fullPrice);
        player = playerService.changeTeam(playerId, toTransfer);
        Iterator<Player> iterator = fromTransfer.getPlayers().iterator();
        while (iterator.hasNext()) {
            Player playerDelete = iterator.next();
            if (Objects.equals(playerDelete.getId(), playerId)) {
                iterator.remove();
            }
        }
        toTransfer.getPlayers().add(player);

        teamRepository.save(toTransfer);
        teamRepository.save(fromTransfer);

        return "Transfer player: " + player.getName() + " " + player.getSurname() + " was successfully transferred from team: "
                + fromTransfer.getName() + " to team: " + toTransfer.getName();
    }
}
