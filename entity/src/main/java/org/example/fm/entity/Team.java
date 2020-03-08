package org.example.fm.entity;

import lombok.*;
import org.example.core.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String backgroundUrl;
    private String logoUrl;
    private String shirtColors;

    @OneToMany(mappedBy = "team", /*fetch = FetchType.EAGER,*/ cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Player> players;

    @OneToMany(mappedBy = "awayTeam", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Match> awayMatches = new ArrayList<>();

    @OneToMany(mappedBy = "homeTeam", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Match> homeMatches = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "user_username")
    private User user;

    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Squad> squads;

}
