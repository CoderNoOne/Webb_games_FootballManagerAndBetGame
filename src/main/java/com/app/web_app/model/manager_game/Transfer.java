package com.app.web_app.model.manager_game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transfers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player  player;

    @ManyToOne
    @JoinColumn(name = "team_from_id")
    private Team teamFrom;

    @ManyToOne
    @JoinColumn(name = "team_to_id")
    private Team teamTo;

    private BigDecimal price;

    private LocalDate date;

}
