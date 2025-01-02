package com.football.manager.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "TEAM")
public class Team {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_id_generator")
    @SequenceGenerator(name = "team_id_generator", sequenceName = "TEAM_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COMMISSION")
    private Integer commission;

    @Column(name = "BUDGET", precision = 10, scale = 3)
    private Double budget;

    @Column(name = "END_DATE")
    private Date endDate;

    @OneToMany(mappedBy = "team")
    private List<Player> players = new ArrayList<>();

}
