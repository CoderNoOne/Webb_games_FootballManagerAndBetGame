package org.example.entity.bet.entity;

import lombok.*;
import org.example.entity.bet.enums.BetLeague;
import org.example.entity.core.entity.User;

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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @Enumerated(EnumType.STRING)
    private BetLeague league;

    private Integer pointsNumber;

    private LocalDateTime lastUpdateTime;
}
