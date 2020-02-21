package com.app.web_app.model.manager_game;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "player_stats")
@Data
public class PlayerStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "playerStats")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Player player;

    private Integer goals;
    private Integer assists;

    @ManyToMany
    @JoinTable(
            name = "players_cards",
            joinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "card_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Card> cards;

}
