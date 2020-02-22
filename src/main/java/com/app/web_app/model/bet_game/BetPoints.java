package com.app.web_app.model.bet_game;

import com.app.web_app.model.BetLeague;
import com.app.web_app.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

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
