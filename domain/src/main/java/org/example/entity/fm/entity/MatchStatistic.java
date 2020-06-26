package org.example.entity.fm.entity;

import lombok.*;
import org.example.entity.fm.enums.FmMatchStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "match_statistics")
public class MatchStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id")
    private Match match;

    @Enumerated(EnumType.STRING)
    private FmMatchStatus matchStatus;

    private Integer minute;

    private String score;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "matchStatistic")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<GoalDetail> goalsDetails;
}
