package com.football.manager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamDTO {

    private Long id;

    private String name;

    private int commission;

    private double account;

    private List<PlayerDTO> players = new ArrayList<>();
}
