package com.football.manager.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "PLAYER")
public class Player {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_id_generator")
    @SequenceGenerator(name = "player_id_generator", sequenceName = "PLAYER_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "EXPERIENCE")
    private Integer experience;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", nullable = true)
    private Team team;
}
