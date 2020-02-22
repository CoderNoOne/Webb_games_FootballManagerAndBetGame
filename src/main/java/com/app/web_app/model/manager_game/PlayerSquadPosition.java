package com.app.web_app.model.manager_game;

import com.app.web_app.model.manager_game.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "player_positions")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerSquadPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;

    @Enumerated(EnumType.STRING)
    private Position position;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
}
