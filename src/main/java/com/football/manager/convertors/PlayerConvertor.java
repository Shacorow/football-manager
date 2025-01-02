package com.football.manager.convertors;

import com.football.manager.dto.PlayerDTO;
import com.football.manager.models.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerConvertor {

    public void fromDTO(PlayerDTO source, Player destination) {
        destination.setId(source.getId());
        destination.setName(source.getName());
        destination.setSurname(source.getSurname());
        destination.setAge(source.getAge());
        destination.setExperience(source.getExperience());
    }

    public void toDTO(Player source, PlayerDTO destination) {
        destination.setId(source.getId());
        destination.setName(source.getName());
        destination.setSurname(source.getSurname());
        destination.setAge(source.getAge());
        destination.setExperience(source.getExperience());
    }
}
