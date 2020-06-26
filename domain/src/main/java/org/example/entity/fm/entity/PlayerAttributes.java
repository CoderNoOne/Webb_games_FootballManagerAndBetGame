package org.example.entity.fm.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerAttributes {

    @Id
    @Column(name = "player_id")
    private Integer id;

    @MapsId
    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Player player;


    private Integer attacking;
    private Integer defending;
    private Integer heading;

    private Integer dribbling;
    private Integer passing;
    private Integer technique;
    private Integer teamWork;

    private Integer speed;
    private Integer aggression;

    private Integer reflexes;
    private Integer oneOnOnes;
    private Integer penaltySaving;
    private Integer penaltyScoring;
}
