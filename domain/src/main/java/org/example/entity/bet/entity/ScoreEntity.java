package org.example.entity.bet.entity;

import lombok.*;
import org.example.entity.bet.enums.BetLeague;
import org.example.entity.core.entity.User;

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
    @EqualsAndHashCode.Exclude
    private List<BetScore> betScores;

}
