package com.football.manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {

    private Long id;

    private String name;

    private String surname;

    private int age;

    private int experience;
}
