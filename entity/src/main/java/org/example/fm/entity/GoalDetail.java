package org.example.fm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "goal_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GoalDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer minute;

    @ManyToOne
    @JoinColumn(name = "goal_scorer_id")
    private Player scorer;

    @ManyToOne
    @JoinColumn(name = "goal_assistant_id")
    private Player assistant;

    @ManyToOne
    @JoinColumn(name = "match_statistic_id")
    private MatchStatistic matchStatistic;
}
