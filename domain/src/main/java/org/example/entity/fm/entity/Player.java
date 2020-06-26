package org.example.entity.fm.entity;

import lombok.*;
import org.example.entity.fm.enums.Position;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "players")
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

}
