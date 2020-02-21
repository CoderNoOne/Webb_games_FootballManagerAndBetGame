package com.app.web_app.model.manager_game;

import com.app.web_app.model.manager_game.enums.Position;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
//@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;

    private String imageUrl;
    private Integer number;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "positions", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Set<Position> positions;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "player_stats_id")
    private PlayerStats playerStats;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "health_card_id")
    private HealthCard healthCard;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Team team;

    @OneToOne(mappedBy = "player", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private PlayerAttributes playerAttributes;

    @OneToMany(mappedBy = "player", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Transfer> transfers;

    @OneToMany(mappedBy = "player", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<PlayerStatsPerMatch> playerStatsPerMatches;

}
