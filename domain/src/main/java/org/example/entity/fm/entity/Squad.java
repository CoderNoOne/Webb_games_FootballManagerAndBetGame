package org.example.entity.fm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "squads"/*, uniqueConstraints = {@UniqueConstraint(columnNames = {"team_id","name"})}*/)
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private String name;
    private Integer formationType;

    @OneToMany(mappedBy = "squad", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<PlayerSquadPosition> playerSquadPositions;

}
