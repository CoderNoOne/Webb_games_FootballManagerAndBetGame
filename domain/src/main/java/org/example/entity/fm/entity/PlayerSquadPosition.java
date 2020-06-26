package org.example.entity.fm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.fm.enums.Position;

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
