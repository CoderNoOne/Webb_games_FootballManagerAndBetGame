package com.app.web_app.model.manager_game;

import com.app.web_app.model.manager_game.enums.Formation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "match_squads", uniqueConstraints = @UniqueConstraint(columnNames = {"matchId", "teamId"}))
public class MatchSquad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer matchId;
    private Integer teamId;

    @Enumerated(EnumType.STRING)
    private Formation formation;

    private String formationName;
    private Integer substitutionsAvailable;

    @ManyToMany
    @JoinTable(name = "match_squads_players",
            joinColumns = @JoinColumn(name = "match_squad_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"))
    @MapKeyColumn(name = "position")
    private Map<String, Player> players;

//    private LocalDateTime lastSaveTime;
}
