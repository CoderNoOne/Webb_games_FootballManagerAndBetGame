package com.app.web_app.model.manager_game;

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
public class PlayerPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;

    private String position;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
}
