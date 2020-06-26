package org.example.entity.fm.entity;

import lombok.*;
import org.example.entity.core.entity.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "games")
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
