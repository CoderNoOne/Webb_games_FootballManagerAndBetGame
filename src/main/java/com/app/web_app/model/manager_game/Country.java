package com.app.web_app.model.manager_game;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "countries")
@Data
public class Country {

    @Id
    private String name;

    @OneToMany(mappedBy = "country")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<League> leagues;

    @OneToMany(mappedBy = "country")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Player> players;

    @OneToMany(mappedBy = "country")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Team> teams;
}
