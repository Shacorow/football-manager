package com.football.manager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PlayerDTO {

    private Long id;

    private String name;

    private String surname;

    private Date birthDate;

    private Date startDate;

    private Date endDate;
}
