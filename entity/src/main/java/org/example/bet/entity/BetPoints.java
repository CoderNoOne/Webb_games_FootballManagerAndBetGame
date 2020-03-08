package org.example.bet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bet.enums.BetLeague;
import org.example.core.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "bet_points", uniqueConstraints = {@UniqueConstraint(columnNames = {"league", "username"})})
public class BetPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    @Enumerated(EnumType.STRING)
    private BetLeague league;

    private Integer pointsNumber;

    private LocalDateTime lastUpdateTime;
}
