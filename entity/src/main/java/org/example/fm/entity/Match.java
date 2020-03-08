package org.example.fm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.fm.enums.FmMatchStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_id")
    private Team awayTeam;

    private LocalDateTime dateTime;

    private String score;

    @Enumerated(EnumType.ORDINAL)
    private FmMatchStatus status;

    private Integer matchDay;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

}
