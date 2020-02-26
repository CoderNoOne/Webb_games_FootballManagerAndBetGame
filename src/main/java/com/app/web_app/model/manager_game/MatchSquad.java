package com.app.web_app.model.manager_game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "match_squads", uniqueConstraints = @UniqueConstraint(columnNames = {"match_id", "team_id"}))
public class MatchSquad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private Integer formationType;

    private Integer substitutionsAvailable;

    @ManyToMany
    @JoinTable(name = "match_squads_players",
            joinColumns = @JoinColumn(name = "match_squad_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"))
    @MapKeyColumn(name = "position")
    private Map<String, Player> players;

}
