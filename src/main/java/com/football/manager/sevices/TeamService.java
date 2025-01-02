package com.football.manager.sevices;

import com.football.manager.dto.TeamDTO;
import com.football.manager.exceptions.BudgetExceedException;
import com.football.manager.exceptions.TeamAlreadyExistException;
import com.football.manager.exceptions.TeamNotExistException;
import com.football.manager.exceptions.WrongCommissionException;
import com.football.manager.models.Player;
import com.football.manager.models.Team;
import com.football.manager.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final PlayerService playerService;


    public Team create(TeamDTO teamDTO) {
        Team team = teamRepository.getByName(teamDTO.getName());
        if (team == null || (team.getEndDate() != null && team.getEndDate().before(new Date()))) {
            team = new Team();
        } else {
            throw new TeamAlreadyExistException(teamDTO.getName());
        }
        team.setName(teamDTO.getName());
        if (teamDTO.getCommission() < 0 || teamDTO.getCommission() > 10) {
            throw new WrongCommissionException();
        }
        team.setCommission(teamDTO.getCommission());
        team.setBudget(teamDTO.getBudget());
        team = teamRepository.save(team);
        return team;
    }

    public Team getById(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotExistException(id));
        if (team.getEndDate() != null) throw new TeamNotExistException(id);
        return team;
    }

    public void delete(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotExistException(id));
        if (team.getEndDate() != null) throw new TeamNotExistException(id);

        for (Player player : team.getPlayers()) {
            playerService.clearTeam(player.getId());
        }
        team.setPlayers(null);
        team.setEndDate(new Date());
        teamRepository.save(team);
    }

    public Team update(Long id, Team updatedTeam) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotExistException(id));
        if (team.getEndDate() != null) throw new TeamNotExistException(id);
        if (updatedTeam.getName() != null) {
            if (teamRepository.getByName(updatedTeam.getName()) == null) {
                team.setName(updatedTeam.getName());
            } else {
                throw new TeamAlreadyExistException(updatedTeam.getName());
            }
        }
        if (updatedTeam.getBudget() != null) {
            team.setBudget(updatedTeam.getBudget());
        }
        if (updatedTeam.getCommission() != null) {
            team.setCommission(updatedTeam.getCommission());
        }
        return teamRepository.save(team);
    }

    public void addPlayerToTeam(Long teamId, Long playerId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotExistException(teamId));
        if (team.getEndDate() != null) throw new TeamNotExistException(teamId);
        Player player = playerService.getById(playerId);
        if (player.getTeam() != null) {
            transferPlayer(teamId, playerId);
        } else {
            player = playerService.changeTeam(playerId, team);
            team.getPlayers().add(player);
            teamRepository.save(team);
        }
    }

    private Period getDateDiff(Date start, Date end) {
        return Period.between(start.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate(), end.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
    }

    public void transferPlayer(Long teamId, Long playerId) {
        Player player = playerService.getById(playerId);
        if (player.getTeam() == null) {
            addPlayerToTeam(teamId, playerId);
        }
        Player finalPlayer = player;
        Team fromTransfer = teamRepository.findById(player.getTeam().getId()).orElseThrow(() -> new TeamNotExistException(finalPlayer.getTeam().getId()));
        if (fromTransfer.getEndDate() != null) throw new TeamNotExistException(finalPlayer.getTeam().getId());
        Team toTransfer = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotExistException(teamId));
        if (toTransfer.getEndDate() != null) throw new TeamNotExistException(teamId);
        Period careerPeriod = getDateDiff(player.getStartDate(), new Date());
        int experience = careerPeriod.getMonths() + (careerPeriod.getYears() * 12);
        Period agePeriod = getDateDiff(player.getBirthDate(), new Date());
        double varietyOfTransfer = (double) (experience * 100000) / agePeriod.getYears();

        double fullPrice = varietyOfTransfer + (double) (varietyOfTransfer * (fromTransfer.getCommission() / 100.0));

        BigDecimal fullPriceRounded = new BigDecimal(fullPrice).setScale(3, RoundingMode.HALF_UP);

        fullPrice = fullPriceRounded.doubleValue();

        if (toTransfer.getBudget() < fullPrice) throw new BudgetExceedException(toTransfer.getName());
        toTransfer.setBudget(toTransfer.getBudget() - fullPrice);
        fromTransfer.setBudget(fromTransfer.getBudget() + fullPrice);
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
    }
}
