package org.example.entity.core.entity;


import lombok.*;
import org.example.entity.bet.entity.BetPoints;
import org.example.entity.bet.entity.ScoreEntity;
import org.example.entity.core.enums.Authority;
import org.example.entity.core.enums.Gender;
import org.example.entity.fm.entity.Game;
import org.example.entity.fm.entity.Team;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    private String username;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String password;

    private String photoUrl;

    @Column(columnDefinition = "tinyInt")
    private Boolean enabled;

    @ElementCollection
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "username"))
    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Authority> authorities;


    @ManyToMany
    @JoinTable(
            name = "users_games",
            joinColumns = @JoinColumn(name = "user_username", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Game> games;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Team> teams;


    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ScoreEntity> scoreEntities;

    @OneToOne(mappedBy = "user")
    private BetPoints betPoints;

}
