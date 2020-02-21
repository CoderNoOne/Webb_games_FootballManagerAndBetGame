package com.app.web_app.model.bet_game;

import com.app.web_app.model.BetLeague;
import com.app.web_app.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
