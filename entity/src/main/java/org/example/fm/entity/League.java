package org.example.fm.entity;

import lombok.*;
import org.example.fm.enums.LeagueType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "leagues")
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate startDate;
    private String name;

    @OneToMany(mappedBy = "league", cascade = {CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Team> teams;

    @OneToMany(mappedBy = "league", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Match> matches;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Enumerated(EnumType.STRING)
    private LeagueType leagueType;

    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
