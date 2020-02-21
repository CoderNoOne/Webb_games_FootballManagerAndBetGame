package com.app.web_app.model.manager_game;

import com.app.web_app.model.manager_game.enums.CardColor;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CardColor cardColor;

    private LocalDate date;

    @ManyToMany(mappedBy = "cards")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<PlayerStats> playerStats;
}
