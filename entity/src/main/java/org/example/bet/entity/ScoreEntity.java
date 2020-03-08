package org.example.bet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bet.enums.BetLeague;
import org.example.core.entity.User;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ScoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private BetLeague league;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "bet_scores")
    private List<BetScore> betScores;

}
