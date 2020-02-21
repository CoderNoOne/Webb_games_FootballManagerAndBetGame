package com.app.web_app.model.manager_game;

import com.app.web_app.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean active;

    @ManyToMany(mappedBy = "games")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<User> users;

    @OneToMany(mappedBy = "game", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<League> leagues;

    private LocalDate startDate;
}
