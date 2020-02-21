package com.app.web_app.model.manager_game;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "matches")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_id")
    private Team awayTeam;

    private LocalDateTime dateTime;

    private String score;

    @Enumerated(EnumType.ORDINAL)
    private FmMatchStatus status;

    private Integer matchDay;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @OneToMany(mappedBy = "match", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<PlayerStatsPerMatch> playerStatsPerMatches;

    @OneToMany(mappedBy = "match", cascade = {CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<TeamSquad> startingTeamSquads;
}
